import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Player extends Actor {
    
    // constants / default values
    private final int DEF_DELTA = 4;
    private final int DEF_IMAGE_STANCE = 2;
    private final int DEF_IMAGE_VARIANT = 1;
    private final int DEF_IMAGE_DELAY = 10;
    private final int DEF_PICKUP_ITEM_DELAY = 55;
    
    /*
     * index - control
     * 0 - front
     * 1 - left
     * 2 - back
     * 3 - right
     * 4 - interact / pick item
     * 5 - drop item
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
    private Item holdingItem;
    private int pickupItemDelay;
    
    public Player(String[] controls, GreenfootImage[] images) {
        this.controls = controls;
        this.images = images;
        
        this.delta = this.DEF_DELTA;
        this.imageStance = this.DEF_IMAGE_STANCE;
        this.imageVariant = this.DEF_IMAGE_VARIANT;
        this.imageDelay = this.DEF_IMAGE_DELAY;
        this.pickupItemDelay = this.DEF_PICKUP_ITEM_DELAY;
        
        setImage(images[this.imageStance * 4 + this.imageVariant]);
    }

    public void act() {
        if (!freeze) {
            checkMovement();
            checkItemPickup();
            checkDropItem();
        }
        holdingItemFollow();
    }

    // checks player movement
    public void checkMovement() {
        int sX = 0;
        int sY = 0;
        
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

        if (imageDelay <= 0) {
            imageVariant = (!moved) ? DEF_IMAGE_VARIANT : ((imageVariant == 3) ? 0 : ++imageVariant);
            imageDelay = DEF_IMAGE_DELAY;
        } else {
            imageDelay--;
        }
        
        setImage(images[imageStance * 4 + imageVariant]);
    }

    // checks player item pickup
    public void checkItemPickup() {
        if (pickupItemDelay > 0) {
            pickupItemDelay--;
            return;
        }

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
            if (holdingItem != null) {
                dropItem();
            }
        } else {
            if (!item.isPickable()) return;
            if (holdingItem != null) dropItem();
            item.setPickable(false);
            holdingItem = item;
            holdingItem.setLocation(getX(), getY() - 50);
        }
    }

    // drops the holding item, if holding one
    public void dropItem() {
        if (holdingItem == null) return;
        holdingItem.setLocation(getX(), getY());
        holdingItem.setPickable(true);
        holdingItem = null;
    }

    // destroys the item in player's hand
    public void destroyItem() {
        if (holdingItem == null) return;
        getWorld().removeObject(holdingItem);
        holdingItem = null;
    }

    // freezes the player
    public void freeze() {
        this.freeze = true;
    }

    // unfreezes the player
    public void unfreeze() {
        this.freeze = false;
    }

    // returns true if player is touching an object of the given type
    public boolean isTouching(Class clazz) {
        return getWorld() != null && super.isTouching(clazz);
    }

    // returns true if player is touching the given object
    public boolean isTouching(Actor object) {
        return getWorld() != null && intersects(object);
    }

    // returns true if player is trying to interact
    public boolean isInteracting() {
        return getWorld() != null && !freeze && Greenfoot.isKeyDown(controls[4]);
    }

    // returns true if player is trying to interact with an object of the given type (for collidable object with aren't intersectable)
    public boolean isInteracting(Class type) {
        return isInteracting() && getObjectsInRange(60, type).size() > 0;
    }

    // returns true if player is trying to interact with the given object (for collidable object with aren't intersectable)
    public boolean isInteracting(Actor object) {
        return isInteracting() && getObjectsInRange(60, null).contains(object);
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

    public void setHoldingItem(Item holdingItem) {
        this.holdingItem = holdingItem;
    }

    public Item getHoldingItem() {
        return this.holdingItem;
    }

}
