import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Player extends Actor {
    
    // constants / default values
    private final int DEF_LIFE = 3;
    private final int DEF_DELTA = 4;
    private final int DEF_IMAGE_STANCE = 2;
    private final int DEF_IMAGE_VARIANT = 1;
    private final int DEF_IMAGE_DELAY = 10;
    
    /*
     * index - control
     * 0 - front
     * 1 - left
     * 2 - back
     * 3 - right
     * 4 - interact
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
    private int life;
    private int delta;
    private int imageStance;
    private int imageVariant;
    private int imageDelay;
    
    public Player(String[] controls, GreenfootImage[] images) {
        this.controls = controls;
        this.images = images;
        
        this.life = this.DEF_LIFE;
        this.delta = this.DEF_DELTA;
        this.imageStance = this.DEF_IMAGE_STANCE;
        this.imageVariant = this.DEF_IMAGE_VARIANT;
        this.imageDelay = this.DEF_IMAGE_DELAY;
        
        setImage(images[this.imageStance * 4 + this.imageVariant]);
    }

    public void act() {
        if (!isBlocked())
            move();
    }

    // checks if player can move
    public boolean isBlocked() {
        try {
            GameWorld world = getWorldOfType(GameWorld.class);
            return world != null && world.getBlockMovement();
        } catch (ClassCastException ex) {
            return true;
        }
    }
    
    /*
     * checks if player is using the keys to move or run
     * checks collides
     * updates player image
     */
    public void move() {
        boolean moved = false;
        int sX = 0;
        int sY = 0;
        
        // check pressed keys to modify sX and sY
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
        
        // actual movement and collide check
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

        moved = sX != 0 || sY != 0;

        // determine imageStance
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
        if (imageDelay <= 0) {
            imageVariant = (!moved) ? DEF_IMAGE_VARIANT : ((imageVariant == 3) ? 0 : ++imageVariant);
            imageDelay = DEF_IMAGE_DELAY;
        } else {
            imageDelay--;
        }
        
        setImage(images[imageStance * 4 + imageVariant]);
    }
    
    /*
     * isTouching inherited from actor is protected
     * needed for level purposes
     */
    public boolean isTouching(Class clazz) {
        return getWorld() != null && super.isTouching(clazz);
    }

    /*
     * returns true when player is trying to interact
     * needed for level purposes
     */
    public boolean isInteracting() {
        return getWorld() != null && !isBlocked() && Greenfoot.isKeyDown(controls[4]);
    }

    /*
     * returns true when player is trying to interact with an actor of the given type
     * needed for level purposes
     */
    public boolean isInteracting(Class type) {
        return getWorld() != null && isInteracting() && getObjectsInRange(60, type).size() > 0;
    }

    // getters and setters
    public void setLife(int life) {
        this.life = life;
    }
    
    public int getLife() {
        return this.life;
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

}
