import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Level1 extends Level {

    private GreenfootImage image1; // closed door image
    private GreenfootImage image2; // open door image
    private Vent vent;
    private Door lockerDoor; // null if open

    // constructor for debug
    public Level1() {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, StaminaBar.class, Player.class, GameObject.class);
        setBackground();
        addTimer(true);
        addImageObjects();
        addCrowbar();
        addVent();
        addKey();
        addLockerDoor();
        spawnPlayer1(false);
        spawnPlayer2(false);
        addStaminaBar1();
        addStaminaBar2();
        debug();
    }

    public Level1(Player player1, Player player2) {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, StaminaBar.class, Player.class, GameObject.class);
        setBackground();
        addTimer(false);
        addImageObjects();
        addCrowbar();
        addVent();
        addKey();
        addLockerDoor();
        addJoinAnimation();
        spawnPlayer1(false, player1);
        spawnPlayer2(false, player2);
        addStaminaBar1();
        addStaminaBar2();
        freezePlayers();
    }
    
    public void act() {
        checkStart();
        checkOpenVent();
        checkEnterVent();
        checkOpenLockerDoor();
        checkLeave();
        checkGameOver();
    }

    // checks when join animation ends so player can start moving
    public void checkStart() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            start();
        }
    }

    // checks if player is openning the vent
    public void checkOpenVent() {
        if (vent == null) return;
        if (vent.isOpen()) return;

        if (getPlayer1().getHoldingItem() instanceof Crowbar && getPlayer1().isInteracting(vent)) {
            getPlayer1().destroyItem();
            vent.open();
        }

        if (getPlayer2().getHoldingItem() instanceof Crowbar && getPlayer2().isInteracting(vent)) {
            getPlayer2().destroyItem();
            vent.open();
        }
    }

    // checks if player is entering the vent
    public void checkEnterVent() {
        if (!vent.isOpen()) return;

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
        if (getPlayer1().getHoldingItem() instanceof Key && getPlayer1().isInteracting(Door.class) || getPlayer2().getHoldingItem() instanceof Key && getPlayer2().isInteracting(Door.class)) {
            openLockerDoor();
        }
    }

    // checks if player is leaving the level (entering the elevator)
    public void checkLeave() {
        if (getPlayer1().getWorld() != null) {
            if (getPlayer1().getX() >= getWidth() - 1) {
                removeObject(getPlayer1());
                removeObject(getPlayer1().getHoldingItem());
            }
        } else {
            if (!getPlayer1().isFreeze() && Greenfoot.isKeyDown(getPlayer1().getControls()[1])) {
                addObject(getPlayer1(), getWidth() - 2, getHeight()/2);
            }
        }

        if (getPlayer2().getWorld() != null) {
            if (getPlayer2().getX() >= getWidth() - 1) {
                removeObject(getPlayer2());
                removeObject(getPlayer2().getHoldingItem());
            }
        } else {
            if (!getPlayer2().isFreeze() && Greenfoot.isKeyDown(getPlayer2().getControls()[1])) {
                addObject(getPlayer2(), getWidth() - 2, getHeight()/2);
            }
        }

        if (getPlayer1().getWorld() == null && getPlayer2().getWorld() == null) {
            if (getLeaveAnimation() == null) {
                getTimer().setCount(false);
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

    // checks if timer <= 0
    public void checkGameOver() {
        if (getTimer().getValue() <= 0) {
            gameOver();
        }
    }

    // starts the level
    public void start() {
        removeJoinAnimation();
        unfreezePlayers();
        getTimer().setCount(true);
    }

    // makes player enter the vent
    public void enterVent(Player player) {
        player.setLocation(685, 170);
    }

    // opens locker door
    public void openLockerDoor() {
        lockerDoor.playSound();
        removeLockerDoor();
    }

    // closes locker door
    public void closeLockerDoor() {
        addLockerDoor();
        lockerDoor.playSound();
    }

    // makes player leave the level to the elevator
    public void leave() {
        if (getElevator() == null) {
            Elevator.setKeyInserted(false);
            setElevator(new Elevator(this, new Level2(getTimer(), getPlayer1(), getPlayer2()), getTimer(), getPlayer1(), getPlayer2()));
            Greenfoot.setWorld(getElevator());
        } else {
            getTimer().setCount(false);
            getElevator().addTimer(getTimer());
            Greenfoot.setWorld(getElevator());
            getElevator().spawnPlayer1(1, getElevator().getHeight()/2, getPlayer1());
            getElevator().spawnPlayer2(1, getElevator().getHeight()/2, getPlayer2());
            getElevator().addJoinAnimation();
        }
    }

    // game over
    public void gameOver() {
        Greenfoot.setWorld(new GameOver());
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

    // adds the crowbar
    public void addCrowbar() {
        addObject(new Crowbar(), 740, 645);
    }

    // adds the vent, if not added
    public void addVent() {
        if (vent != null) return;
        vent = new Vent(40, 75);
        addObject(vent, 225, 655);
    }

    // removes the vent, if added
    public void removeVent() {
        if (vent == null) return;
        removeObject(vent);
        vent = null;
    }

    // adds the key
    public void addKey() {
        addObject(new Key(), 735, 110);
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

    // spawns player 1
    public void spawnPlayer1(boolean fromElevator) {
        if (fromElevator) {
            if (getPlayer1() != null) {
                spawnPlayer1(getWidth()-2, 365, getPlayer1());
            } else {
                spawnPlayer1(getWidth()-2, 365);
            }
        } else {
            if (getPlayer1() != null) {
                spawnPlayer1(70, 365, getPlayer1());
            } else {
                spawnPlayer1(70, 365);
            }
        }
    }

    // spawns a given player 1
    public void spawnPlayer1(boolean fromElevator, Player player1) {
        if (fromElevator) {
            spawnPlayer1(getWidth()-2, 365, player1);
        } else {
            spawnPlayer1(70, 365, player1);
        }
    }

    // spawns player 2
    public void spawnPlayer2(boolean fromElevator) {
        if (fromElevator) {
            if (getPlayer2() != null) {
                spawnPlayer2(getWidth()-2, 415, getPlayer2());
            } else {
                spawnPlayer2(getWidth()-2, 415);
            }
        } else {
            if (getPlayer2() != null) {
                spawnPlayer2(70, 415, getPlayer2());
            } else {
                spawnPlayer2(70, 415);
            }
        }
    }

    // spawns a given player 2
    public void spawnPlayer2(boolean fromElevator, Player player2) {
        if (fromElevator) {
            spawnPlayer2(getWidth()-2, 415, player2);
        } else {
            spawnPlayer2(70, 415, player2);
        }
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

    public void setVent(Vent vent) {
        this.vent = vent;
    }

    public Vent getVent() {
        return this.vent;
    }

    public void setLockerDoor(Door lockerDoor) {
        this.lockerDoor = lockerDoor;
    }
    
    public Door getLockerDoor() {
        return this.lockerDoor;
    }
    
}