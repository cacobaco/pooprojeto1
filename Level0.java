import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Level0 extends Level {
    
    private GreenfootImage image1; // closed door image
    private GreenfootImage image2; // open door image
    private Door leaveDoor; // null if open
    
    public Level0() {
        super(800, 800, 400, 350, 400, 400);
        setBackground();
        addImageObjects();
        addVehicles();
        addLeaveDoor();
        addJoinAnimation();
        freezePlayers();
    }

    public void act() {
        checkStart();
        checkOpenLeaveDoor();
        checkLeave();
    }

    // checks when join animation ends so player can start moving
    public void checkStart() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            start();
        }
    }

    // checks if players are interacting with the leave door
    public void checkOpenLeaveDoor() {
        if (leaveDoor == null) return;
        if ((getPlayer1().isInteracting(leaveDoor)) || (getPlayer2() != null && getPlayer2().isInteracting(leaveDoor))) {
            openLeaveDoor();
        }
    }

    // checks if player is leaving the level (entering the level 1)
    public void checkLeave() {
        if (getPlayer1().getWorld() != null) {
            if (getPlayer1().getX() >= getWidth() - 1) {
                removeObject(getPlayer1().getHoldingItem());
                removeObject(getPlayer1());
            }
        } else {
            if (!getPlayer1().isFreeze() && Greenfoot.isKeyDown(getPlayer1().getControls()[1])) {
                addObject(getPlayer1(), getWidth() - 2, getHeight()/2);
            }
        }

        if (getPlayer2().getWorld() != null) {
            if (getPlayer2().getX() >= getWidth() - 1) {
                removeObject(getPlayer2().getHoldingItem());
                removeObject(getPlayer2());
            }
        } else {
            if (!getPlayer2().isFreeze() && Greenfoot.isKeyDown(getPlayer2().getControls()[1])) {
                addObject(getPlayer2(), getWidth() - 2, getHeight()/2);
            }
        }

        if (getPlayer1().getWorld() == null && getPlayer2().getWorld() == null) {
            if (getLeaveAnimation() == null) {
                freezePlayers();
                addLeaveAnimation();
            } else {
                if (getLeaveAnimation().hasEnded()) {
                    removeLeaveAnimation();
                    leave();
                }
            }
        }
    }

    // starts the level
    public void start() {
        removeJoinAnimation();
        unfreezePlayers();
    }

    // opens leave door
    public void openLeaveDoor() {
        if (leaveDoor == null) return;
        removeLeaveDoor();
    }
    
    // finishes this level
    public void leave() {
        Greenfoot.setWorld(new Level1(getPlayer1(), getPlayer2()));
    }

    // sets the background
    public void setBackground() {
        image1 = new GreenfootImage("level/0/0.png");
        image1.scale(getWidth(), getHeight());
        image2 = new GreenfootImage("level/0/1.png");
        image2.scale(getWidth(), getHeight());
        setBackground(image1);
    }
    
    // adds invisible collidable objects for image objects (plants, decoration, etc)
    public void addImageObjects() {
        // walls
        addObject(new CollidableObject(40, 400), 790, 120); // wall right up
        addObject(new CollidableObject(40, 400), 790, 650); // wall right down
        
        // bushes
        addObject(new CollidableObject(155, 135), 695, 35); // bush right up
        addObject(new CollidableObject(155, 135), 695, 730); // bush right down
        
        // plants
        addObject(new CollidableObject(55, 90), 745, 245); // plant right up
        addObject(new CollidableObject(55, 90), 745, 495); // plant right down
    }

    // adds collidable vehicles to the world
    public void addVehicles() {
        addObject(new Bike(), 125, 75); // 1st bike
        addObject(new Bike(), 125, 180); // 2nd bike
        addObject(new Bike(), 125, 285); // 3rd bike

        addObject(new Car(), 150, 400); // 1st car
        addObject(new Car(), 150, 550); // 2nd car
        addObject(new Car(), 150, 700); // 3rd car
    }

    // adds the leave door, if not added
    public void addLeaveDoor() {
        if (leaveDoor != null) return;
        leaveDoor = new Door(40, 125);
        addObject(leaveDoor, 790, 385);
        setBackground(image1);
    }

    // removes the leave door, if added
    public void removeLeaveDoor() {
        if (leaveDoor == null) return;
        removeObject(leaveDoor);
        leaveDoor = null;
        setBackground(image2);
    }

    // getters and setters
    public void setImage1(GreenfootImage image1) {
        this.image1 = image1;
    }

    public GreenfootImage getImage1() {
        return this.image1;
    }

    public void setImage2(GreenfootImage image2) {
        this.image2 = image2;
    }

    public GreenfootImage getImage2() {
        return this.image2;
    }
    
    public void setLeaveDoor(Door leaveDoor) {
        this.leaveDoor = leaveDoor;
    }
    
    public Door getLeaveDoor() {
        return this.leaveDoor;
    }
    
}
