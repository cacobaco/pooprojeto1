import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class Creature extends Actor {
    
    // constants / default values
    private final int DEF_DELTA = 1;
    private final int DEF_IMAGE_STANCE = 2;
    private final int DEF_IMAGE_VARIANT = 1;
    private final int DEF_IMAGE_DELAY = 10;
    
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
    
    public Creature() {
        this.images = new GreenfootImage[] {
            new GreenfootImage("creature/f1.png"),
            new GreenfootImage("creature/f2.png"),
            new GreenfootImage("creature/f3.png"),
            new GreenfootImage("creature/f2.png"),
            new GreenfootImage("creature/l1.png"),
            new GreenfootImage("creature/l2.png"),
            new GreenfootImage("creature/l3.png"),
            new GreenfootImage("creature/l2.png"),
            new GreenfootImage("creature/d1.png"),
            new GreenfootImage("creature/d2.png"),
            new GreenfootImage("creature/d3.png"),
            new GreenfootImage("creature/d2.png"),
            new GreenfootImage("creature/r1.png"),
            new GreenfootImage("creature/r2.png"),
            new GreenfootImage("creature/r3.png"),
            new GreenfootImage("creature/r2.png")
        };
        
        for (GreenfootImage image : images) {
            image.scale(image.getWidth()/3, image.getHeight()/3);
        }
        
        this.delta = this.DEF_DELTA;
        this.imageStance = this.DEF_IMAGE_STANCE;
        this.imageVariant = this.DEF_IMAGE_VARIANT;
        this.imageDelay = this.DEF_IMAGE_DELAY;
        
        setImage(images[this.imageStance * 4 + this.imageVariant]);
    }

    public void act() {
        if (!freeze) {
            moveTowards(getNearestPlayer());
            checkKill();
        }
    }

    // makes the creature move
    public void moveTowards(Player player) {
        if (player == null || player.getWorld() == null) return;

        int sX = 0;
        int sY = 0;

        if (getX() > player.getX()) {
            sX -= delta;
            imageStance = 1;
        }
        if (getX() < player.getX()) {
            sX += delta;
            imageStance = 3;
        }
        if (getY() > player.getY()) {
            sY -= delta;
            imageStance = 0;
        }
        if (getY() < player.getY()) {
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

        if (--imageDelay <= 0) {
            imageVariant = (!moved) ? DEF_IMAGE_VARIANT : ((imageVariant == 3) ? 0 : ++imageVariant);
            imageDelay = DEF_IMAGE_DELAY;
        }
        
        setImage(images[imageStance * 4 + imageVariant]);
    }

    // check if he can kill a player
    public void checkKill() {
        if (isTouching(Player.class)) {
            for (Player player : getIntersectingObjects(Player.class)) {
                if (player != null && !player.isDead()) {
                    player.kill();
                }
            }
        }
    }

    // returns the nearest alive player
    public Player getNearestPlayer() {
        int width = getWorld().getWidth();
        int height = getWorld().getHeight();

        int range = (width >= height) ? width : height;

        Player nearestPlayer = null;

        for (int i = 0; i <= range; i++) {
            List<Player> players = getObjectsInRange(i, Player.class);

            for (Player player : getObjectsInRange(i, Player.class)) {
                if (player.isDead()) continue;
                else nearestPlayer = player;
            }

            if (nearestPlayer != null) break;
        }

        return nearestPlayer;
    }

    // freezes the creature
    public void freeze() {
        this.freeze = true;
    }

    // unfreezes the creature
    public void unfreeze() {
        this.freeze = false;
    }

    // getters and setters
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

}
