 import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Level1 extends Level {
    
    private GreenfootImage image1; // closed door image
    private GreenfootImage image2; // open door image
    private Door lockerDoor; // null if open
    private boolean ventOpen; // true if used the crowbar to open the vent
    
    // constructor for debug
    public Level1() {
        super(800, 800, 70, 375, 70, 425);
        setBackground();
        addImageObjects();
        addLockerDoor();
        addVent();
        addCrowbar();
        addKey();
        debug();
    }
    
    public Level1(Player player1, Player player2) {
        super(800, 800, 70, 375, 70, 425, player1, player2);
        setBackground();
        addImageObjects();
        addLockerDoor();
        addVent();
        addCrowbar();
        addKey();
        addJoinAnimation();
        freezePlayers();
    }
    
    public void act() {
        checkStart();
        checkOpenVent();
        checkEnterVent();
        checkOpenLockerDoor();
        checkLeave();
    }

    // checks when join animation ends so player can start moving
    public void checkStart() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            start();
        }
    }

    // checks if player is openning the vent
    public void checkOpenVent() {
        if (ventOpen) return;

        if (getPlayer1().getHoldingItem() instanceof Crowbar && getPlayer1().isInteracting(Vent.class)) {
            getPlayer1().destroyItem();
            openVent();
        }

        if (getPlayer2().getHoldingItem() instanceof Crowbar && getPlayer2().isInteracting(Vent.class)) {
            getPlayer2().destroyItem();
            openVent();
        }
    }

    // checks if player is entering the vent
    public void checkEnterVent() {
        if (!ventOpen) return;

        if (getPlayer1().isInteracting(Vent.class)) {
            enterVent(getPlayer1());
        }

        if (getPlayer2().isInteracting(Vent.class)) {
            enterVent(getPlayer2());
        }
    }

    // checks if player is openning the locker door
    public void checkOpenLockerDoor() {
        if (lockerDoor == null) return;

        if (getPlayer1().getHoldingItem() instanceof Key && getPlayer1().isInteracting(Door.class)) {
            openLockerDoor();
        }

        if (getPlayer2().getHoldingItem() instanceof Key && getPlayer2().isInteracting(Door.class)) {
            openLockerDoor();
        }
    }

    // checks if player is leaving the level (entering the elevator)
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

    // opens the vent
    public void openVent() {
        ventOpen = true;
    }

    // closes the vent
    public void closeVent() {
        ventOpen = false;
    }

    // makes player enter the vent
    public void enterVent(Player player) {
        player.setLocation(685, 170);
    }

    // opens locker door
    public void openLockerDoor() {
        removeLockerDoor();
    }

    // makes player leave the level to the elevator
    public void leave() {
        if (getElevator() == null) {
            setElevator(new Elevator(800, 800, this, new Level1(getPlayer1(), getPlayer2()), getPlayer1(), getPlayer2())); // TODO(): change next level
            Greenfoot.setWorld(getElevator());
        } else {
            Greenfoot.setWorld(getElevator());
            getElevator().spawnPlayer1(1, getElevator().getHeight()/2, getPlayer1());
            getElevator().spawnPlayer2(1, getElevator().getHeight()/2, getPlayer2());
            getElevator().addJoinAnimation();
        }
    }

    // sets the background
    public void setBackground() {
        image1 = new GreenfootImage("level/1/0.png");
        image1.scale(getWidth(), getHeight());
        image2 = new GreenfootImage("level/1/1.png");
        image2.scale(getWidth(), getHeight());
        setBackground(image1);
    }
    
    // adds invisible collidable objects for image objects (plants, decoration, etc)
    public void addImageObjects() {
        // walls
        addObject(new CollidableObject(40, 800), 10, 400); // wall and door left
        addObject(new CollidableObject(800, 50), 400, 25); // wall up
        addObject(new CollidableObject(800, 20), 400, 790); // wall down
        addObject(new CollidableObject(40, 330), 785, 635); // wall right up
        addObject(new CollidableObject(40, 330), 785, 165); // wall right down
        addObject(new CollidableObject(50, 100), 580, 70); // wall locker left up
        addObject(new CollidableObject(50, 135), 580, 265); // wall locker left down
        addObject(new CollidableObject(50, 100), 580, 520); // wall leave left down
        addObject(new CollidableObject(200, 50), 685, 285); // wall locker down
        addObject(new CollidableObject(200, 50), 685, 545); // wall leave down

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

        // bottom desk
        addObject(new CollidableObject(50, 210), 270, 675); // desk right
        addObject(new CollidableObject(100, 50), 240, 595); // chairs up
        addObject(new CollidableObject(100, 50), 240, 755); // chairs down
        
        // library & plant
        addObject(new CollidableObject(400, 100), 200, 65);
        
        // locker
        addObject(new CollidableObject(50, 100), 735, 60);
        
        // dog house
        addObject(new CollidableObject(60, 60), 740, 590);
    
        // couch
        addObject(new CollidableObject(210, 50), 243, 233);

        // camera
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

    // adds the vent
    public void addVent() {
        addObject(new Vent(40, 75), 225, 655);
    }

    // adds the crowbar
    public void addCrowbar() {
        addObject(new Crowbar(), 740, 645);
    }

    // adds the key
    public void addKey() {
        addObject(new Key(), 735, 110);
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

    public void setVentOpen(boolean ventOpen) {
        this.ventOpen = ventOpen;
    }

    public boolean isVentOpen() {
        return this.ventOpen;
    }
    
}