import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Player extends Actor {
    
    // constants / default values
    private final int DEF_DELTA = 3;
    private final int DEF_IMAGE_STANCE = 2;
    private final int DEF_IMAGE_VARIANT = 1;
    private final int DEF_IMAGE_DELAY = 10;
    private final int DEF_STAMINA = 100;
    private final int DEF_PICKUP_ITEM_DELAY = 55;
    
    /*
     * index - control
     * 0 - front
     * 1 - left
     * 2 - back
     * 3 - right
     * 4 - interact / pick item
     * 5 - drop item
     * 6 - run
     */
    private final String[] controls;
    
    /*
     * [0, 3] - front
     * [4, 7] - left
     * [8, 11] - back
     * [12, 15] - right
     */
    private final GreenfootImage[] images;

    // player status
    private int delta;
    private int imageStance;
    private int imageVariant;
    private int imageDelay;
    private boolean freeze;
    private int stamina;
    private Item holdingItem;
    private int pickupItemDelay;
    private boolean dead; // can be revived tho
    
    public Player(String[] controls, GreenfootImage[] images) {
        this.controls = controls;
        this.images = images;
        
        this.delta = this.DEF_DELTA;
        this.imageStance = this.DEF_IMAGE_STANCE;
        this.imageVariant = this.DEF_IMAGE_VARIANT;
        this.imageDelay = this.DEF_IMAGE_DELAY;
        this.stamina = this.DEF_STAMINA;
        this.pickupItemDelay = this.DEF_PICKUP_ITEM_DELAY;
        
        setImage(images[this.imageStance * 4 + this.imageVariant]);
    }

    public void act() {
        holdingItemFollow();
        if (!freeze && !dead) {
            checkMovement();
            checkItemPickup();
            checkDropItem();
            checkRevive();
        }
    }

    // checks player movement
    public void checkMovement() {
        boolean runned = Greenfoot.isKeyDown(controls[6]);
        int sX = 0;
        int sY = 0;

        if (runned && stamina > 0) delta *= 2;
        
        // check pressed keys to determine sX, sY and imageStance
        if (Greenfoot.isKeyDown(controls[1])) {
            sX -= delta;
            imageStance = 1;
        }
        if (Greenfoot.isKeyDown(controls[3])) {
            sX += delta;
            imageStance = 3;
        }
        if (Greenfoot.isKeyDown(controls[0])) {
            sY -= delta;
            imageStance = 0;
        }
        if (Greenfoot.isKeyDown(controls[2])) {
            sY += delta;
            imageStance = 2;
        }
        
        // applies and adjusts the movement and checks any collides
        setLocation(getX() + sX, getY() + sY); // move 0
        
        if (isTouching(CollidableObject.class)) {
            setLocation(getX() - sX, getY() - sY); // - move 0

            setLocation(getX(), getY() + sY); // move 1 (attempt to move in y)
            
            if (isTouching(CollidableObject.class)) {
                setLocation(getX(), getY() - sY); // - move 1
                
                setLocation(getX() + sX, getY()); // move 2 (attempt to move in x)
                
                if (isTouching(CollidableObject.class)) {
                    setLocation(getX() - sX, getY()); // - move 2
                    sX = 0;
                    sY = 0;
                } else {
                    sY = 0;
                }
            } else {
                sX = 0;
            }
        }

        // adjusts imageStance after collide check
        if (sY < 0) {
            imageStance = 0;
        } else if (sY > 0) {
            imageStance = 2;
        } else {
            if (sX < 0) {
                imageStance = 1;
            } else if (sX > 0) {
                imageStance = 3;
            }
        }

        // change image
        boolean moved = sX != 0 || sY != 0;

        if (runned) delta = DEF_DELTA;
        if (runned && moved && stamina > 0) stamina--;
        if (!runned && stamina < DEF_STAMINA) stamina++;

        if (--imageDelay <= 0) {
            imageVariant = (!moved) ? DEF_IMAGE_VARIANT : ((imageVariant == 3) ? 0 : ++imageVariant);
            imageDelay = DEF_IMAGE_DELAY;
        }
        
        setImage(images[imageStance * 4 + imageVariant]);
    }

    // checks player item pickup
    public void checkItemPickup() {
        if (--pickupItemDelay > 0) return;

        if (Greenfoot.isKeyDown(controls[4])) {
            pickupItem();
            pickupItemDelay = DEF_PICKUP_ITEM_DELAY;
        }
    }

    // checks player drop item
    public void checkDropItem() {
        if (Greenfoot.isKeyDown(controls[5])) {
            dropItem();
        }
    }

    // checks if player is reviving
    public void checkRevive() {
        if (Greenfoot.isKeyDown(controls[4]) && isTouching(Player.class)) {
            for (Player player : getIntersectingObjects(Player.class)) {
                if (player != null && player.isDead()) {
                    player.revive();
                }
            }
        }
    }

    // makes the holding item follow the player (above his head)
    public void holdingItemFollow() {
        if (holdingItem == null) return;
        if (holdingItem.getWorld() == null) getWorld().addObject(holdingItem, getX(), getY() - 50);
        holdingItem.setLocation(getX(), getY() - 50);
    }

    // picks any item in the ground, if holding one drops and replaces
    public void pickupItem() {
        Item item = (Item) getOneIntersectingObject(Item.class);
        if (item != null) {
            pickupItem(item);
        }
    }

    // picks the given item, if holding one drops and replaces
    public void pickupItem(Item item) {
        if (item == null) {
            if (holdingItem != null) dropItem();
        } else {
            if (!item.isPickable()) return;
            if (holdingItem != null) {
                dropItem();
            } else {
                playPickupDropSound();
            }
            item.setPickable(false);
            holdingItem = item;
            holdingItem.setLocation(getX(), getY() - 50);
        }
    }

    // drops the holding item, if holding one
    public void dropItem() {
        if (holdingItem == null) return;
        playPickupDropSound();
        holdingItem.setLocation(getX(), getY());
        holdingItem.setPickable(true);
        holdingItem = null;
    }

    // destroys the item in player's hand
    public void destroyItem() {
        if (holdingItem == null) return;
        playBreakSound();
        getWorld().removeObject(holdingItem);
        holdingItem = null;
    }

    // plays the pickup or drop sound
    public void playPickupDropSound() {
        Greenfoot.playSound("pickupdrop.mp3");
    }

    // plays the break sound
    public void playBreakSound() {
        Greenfoot.playSound("break.mp3");
    }

    // plays the died sound
    public void playDiedSound() {
        Greenfoot.playSound("oof.mp3");
    }

    // freezes the player
    public void freeze() {
        this.freeze = true;
    }

    // unfreezes the player
    public void unfreeze() {
        this.freeze = false;
    }

    // kills the player
    public void kill() {
        imageStance = DEF_IMAGE_STANCE;
        imageVariant = DEF_IMAGE_VARIANT;
        setImage(images[imageStance * 4 + imageVariant]);
        setRotation(90);
        this.dead = true;
        playDiedSound();
    }

    // revives the player
    public void revive() {
        setRotation(0);
        this.dead = false;
    }

    // returns true if player is touching an object of the given type
    public boolean isTouching(Class clazz) {
        return clazz != null && getWorld() != null && super.isTouching(clazz);
    }

    // returns true if player is touching the given object
    public boolean isTouching(Actor object) {
        return object != null && object.getWorld() != null && getWorld() != null && intersects(object);
    }

    // returns true if player is trying to interact
    public boolean isInteracting() {
        return getWorld() != null && !freeze && Greenfoot.isKeyDown(controls[4]);
    }

    // returns true if player is trying to interact with an object of the given type (for collidable object with aren't intersectable)
    public boolean isInteracting(Class type) {
        return type != null && isInteracting() && getObjectsInRange(65, type).size() > 0;
    }

    // returns true if player is trying to interact with the given object (for collidable object with aren't intersectable)
    public boolean isInteracting(Actor object) {
        if (object == null || object.getWorld() == null || getWorld() == null || freeze || !Greenfoot.isKeyDown(controls[4])) return false;
        
        int imageWidth = object.getImage().getWidth();
        int imageHeight = object.getImage().getHeight();
        int range = 0;

        if (getY() >= object.getY() + imageHeight) {
            range = imageHeight/2;
        } else if (getY() <= object.getY() - imageHeight) {
            range = imageHeight/2;
        } else if (getX() >= object.getX() + imageWidth) {
            range = imageWidth/2;
        } else if (getX() <= object.getX() + imageWidth) {
            range = imageWidth/2;
        }
        
        return getObjectsInRange(38 + range, null).contains(object);
    }

    // getters and setters
    public String[] getControls() {
        return this.controls;
    }

    public GreenfootImage[] getImages() {
        return this.images;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public int getDelta() {
        return this.delta;
    }

    public void setImageStance(int imageStance) {
        this.imageStance = imageStance;
    }

    public int getImageStance() {
        return this.imageStance;
    }

    public void setImageVariant(int imageVariant) {
        this.imageVariant = imageVariant;
    }

    public int getImageVariant() {
        return this.imageVariant;
    }

    public void setImageDelay(int imageDelay) {
        this.imageDelay = imageDelay;
    }

    public int getImageDelay() {
        return this.imageDelay;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    public boolean isFreeze() {
        return this.freeze;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStamina() {
        return this.stamina;
    }

    public void setHoldingItem(Item holdingItem) {
        this.holdingItem = holdingItem;
    }

    public Item getHoldingItem() {
        return this.holdingItem;
    }

    public void setPickupItemDelay(int pickupItemDelay) {
        this.pickupItemDelay = pickupItemDelay;
    }

    public int getPickupItemDelay() {
        return this.pickupItemDelay;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return this.dead;
    }

}
