 import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Level1 extends Level {
    
    private GreenfootImage image1; // closed door image
    private GreenfootImage image2; // open door image
    private Door lockerDoor; // null if open
    
    public Level1() {
        super(800, 800, 70, 375, 70, 425);
        setBackground();
        addImageObjects();
        addLockerDoor();
        debug();
    }
    
    public Level1(Player player1, Player player2) {
        super(800, 800, 70, 375, 70, 425, player1, player2);
        setBackground();
        addImageObjects();
        addLockerDoor();
        addJoinAnimation();
    }
    
    public void act() {
        start();
        openLockerDoor();
        elevator();
    }

    // sets the background
    public void setBackground() {
        image1 = new GreenfootImage("level/1-0.png");
        image1.scale(getWidth(), getHeight());
        image2 = new GreenfootImage("level/1-1.png");
        image2.scale(getWidth(), getHeight());
        setBackground(image1);
    }
    
    // adds invisible collidable objects for image objects (plants, decoration, etc)
    public void addImageObjects() {
        // walls
        addObject(new CollidableObject(40, 800), 10, 400);
        addObject(new CollidableObject(800, 50), 400, 25);
        addObject(new CollidableObject(800, 20), 400, 790);
        addObject(new CollidableObject(40, 330), 785, 635);
        addObject(new CollidableObject(40, 330), 785, 165);
        addObject(new CollidableObject(50, 100), 580, 70);
        addObject(new CollidableObject(50, 135), 580, 265);
        addObject(new CollidableObject(50, 100), 580, 520);
        addObject(new CollidableObject(200, 50), 685, 285);
        addObject(new CollidableObject(200, 50), 685, 545);

        /*addObject(new CollidableObject(40, 425), 790, 150);
        addObject(new CollidableObject(40, 425), 790, 650);
        addObject(new CollidableObject(800, 60), 400, 20);
        addObject(new CollidableObject(800, 30), 400, 795);
        addObject(new CollidableObject(250, 50), 680, 285);
        addObject(new CollidableObject(250, 50), 680, 545);

        addObject(new CollidableObject(50, 100), 580, 75);
        addObject(new CollidableObject(50, 160), 580, 280);*/

        // main desk
        addObject(new CollidableObject(55, 110), 320, 415);

        // desk
        addObject(new CollidableObject(105, 210), 242, 675);
        
        // library & plant
        addObject(new CollidableObject(400, 100), 200, 65);
        
        // locker
        addObject(new CollidableObject(50, 100), 735, 60);
        
        // dog house
        addObject(new CollidableObject(60, 120), 740, 610);
    
        // couch
        addObject(new CollidableObject(210, 50), 243, 233);

        // camara
        addObject(new CollidableObject(50, 50), 635, 340);
    }

    // adds the locker door, if not added
    public void addLockerDoor() {
        if (lockerDoor != null) return;
        lockerDoor = new Door(50, 80);
        addObject(lockerDoor, 580, 160);
        setBackground(image1);
    }

    // removes the locker door, if added
    public void removeLockerDoor() {
        if (lockerDoor == null) return;
        removeObject(lockerDoor);
        lockerDoor = null;
        setBackground(image2);
    }

    // checks when join animation ends so player can start moving
    public void start() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            removeJoinAnimation();
            unblockMovement();
        }
    }

    // checks if players are interacting with locker
    public void openLockerDoor() {
        if ((getPlayer1() != null && getPlayer1().isInteracting(Door.class)) || (getPlayer2() != null && getPlayer2().isInteracting(Door.class))) {
            removeLockerDoor();
        }
    }

    public void elevator() {
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
                    Greenfoot.setWorld(new Elevator(800, 800, this, null, getPlayer1(), getPlayer2()));
                }
            }
        }
    }

    // check if player finished this map
    public void finish() {
        if (lockerDoor != null) return;
        
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
                    Greenfoot.setWorld(new Level1(getPlayer1(), getPlayer2())); // TODO(): change level
                }
            }
        }
    }
    
    // skips this level
    public void skip() {
        Greenfoot.setWorld(new Level1(getPlayer1(), getPlayer2())); // TODO(): change level
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
    
    public void setLockerDoor(Door lockerDoor) {
        this.lockerDoor = lockerDoor;
    }
    
    public Door getLockerDoor() {
        return this.lockerDoor;
    }
    
}