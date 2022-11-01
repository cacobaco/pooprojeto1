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
    }
    
    public void act() {
        start();
        openLeaveDoor();
        finish();
    }

    // sets the background
    public void setBackground() {
        image1 = new GreenfootImage("level/0-0.png");
        image1.scale(getWidth(), getHeight());
        image2 = new GreenfootImage("level/0-1.png");
        image2.scale(getWidth(), getHeight());
        setBackground(image1);
    }
    
    // adds invisible collidable objects for image objects (plants, decoration, etc)
    public void addImageObjects() {
        // walls
        addObject(new CollidableObject(40, 400), 790, 120);
        addObject(new CollidableObject(40, 400), 790, 650);
        
        // bushes
        addObject(new CollidableObject(155, 135), 695, 35);
        addObject(new CollidableObject(155, 135), 695, 730);
        
        // plants
        addObject(new CollidableObject(55, 90), 745, 245);
        addObject(new CollidableObject(55, 90), 745, 495);
    }

    // adds collidable vehicles to the world
    public void addVehicles() {
        addObject(new Bike(), 125, 75);
        addObject(new Bike(), 125, 180);
        addObject(new Bike(), 125, 285);

        addObject(new Car(), 150, 400);
        addObject(new Car(), 150, 550);
        addObject(new Car(), 150, 700);
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

    // checks when join animation ends so player can start moving
    public void start() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            removeJoinAnimation();
            unblockMovement();
        }
    }

    // checks if players are interacting with the leave door
    public void openLeaveDoor() {
        if ((getPlayer1() != null && getPlayer1().isInteracting(Door.class)) || (getPlayer2() != null && getPlayer2().isInteracting(Door.class))) {
            removeLeaveDoor();
        }
    }
    
    // check when player finishes the level
    public void finish() {
        if (leaveDoor != null) return;
        
        if (getPlayer1() != null && getPlayer1().getWorld() != null && getPlayer1().getX() >= getWidth() - 1) {
            removeObject(getPlayer1());
        }

        if (getPlayer2() != null && getPlayer2().getWorld() != null && getPlayer2().getX() >= getWidth() - 1) {
            removeObject(getPlayer2());
        }
        
        if (getPlayer1() != null && getPlayer1().getWorld() == null && getPlayer2() != null && getPlayer2().getWorld() == null) {
            if (getLeaveAnimation() == null) {
                addLeaveAnimation();
            } else {
                if (getLeaveAnimation().hasEnded()) {
                    Greenfoot.setWorld(new Level1(getPlayer1(), getPlayer2()));
                }
            }
        }
    }
    
    // skips this level
    public void skip() {
        Greenfoot.setWorld(new Level1(getPlayer1(), getPlayer2()));
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
