import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Level3 extends Level {

    private GreenfootImage image1; // all doors closed
    private GreenfootImage image2; // bottom door open and top and leave doors closed
    private GreenfootImage image3; // bottom and top doors open and leave door closed
    private GreenfootImage image4; // all doors open
    private Lever entryLever; // null if was pressed
    private Door bottomDoor; // null if open
    private Lever bottomLever; // null if was pressed
    private Door topDoor; // null if open
    private Lever topLever; // null if was pressed
    private Door leaveDoor; // null if open
    
    // constructor for debug
    public Level3() {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, StaminaBar.class, Player.class, GameObject.class);
        setBackground();
        addTimer(true);
        addImageObjects();
        addEntryLever();
        addBottomDoor();
        addBottomLever();
        addTopDoor();
        addTopLever();
        addLeaveDoor();
        spawnPlayer1(false);
        spawnPlayer2(false);
        addStaminaBar1();
        addStaminaBar2();
        debug();
    }
    
    public Level3(Timer timer, Player player1, Player player2) {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, StaminaBar.class, Player.class, GameObject.class);
        setBackground();
        timer.setCount(false);
        addTimer(timer);
        addImageObjects();
        addEntryLever();
        addBottomDoor();
        addBottomLever();
        addTopDoor();
        addTopLever();
        addLeaveDoor();
        addJoinAnimation();
        spawnPlayer1(false, player1);
        spawnPlayer2(false, player2);
        addStaminaBar1();
        addStaminaBar2();
        freezePlayers();
    }
    
    public void act() {
        checkStart();
        checkPullEntryLever();
        checkPullTopLever();
        checkPullBottomLever();
        checkLeave();
        checkGameOver();
    }

    // checks when join animation ends so player can start moving
    public void checkStart() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            start();
        }
    }

    // checks if player is pulling the entry lever
    public void checkPullEntryLever() {
        if (entryLever == null) return;
        if (getPlayer1().isInteracting(entryLever) || getPlayer2().isInteracting(entryLever)) {
            pullEntryLever();
        }
    }

    // checks if player is pulling the top lever
    public void checkPullTopLever() {
        if (topLever == null) return;
        if (getPlayer1().isInteracting(topLever) || getPlayer2().isInteracting(topLever)) {
            pullTopLever();
        }
    }
    
    // checks if player is pulling the bottom lever
    public void checkPullBottomLever() {
        if (bottomLever == null) return;
        if (getPlayer1().isInteracting(bottomLever) || getPlayer2().isInteracting(bottomLever)) {
            pullBottomLever();
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
    
    // starts the game
    public void start() {
        removeJoinAnimation();
        unfreezePlayers();
        getTimer().setCount(true);
    }

    // pulls the entry lever
    public void pullEntryLever() {
        removeEntryLever();
        addUselessEntryLever();
        openBottomDoor();
    }

    // pulls the bottom lever
    public void pullBottomLever() {
        removeBottomLever();
        addUselessBottomLever();
        openTopDoor();
    }

    // pulls the top lever
    public void pullTopLever() {
        removeTopLever();
        addUselessTopLever();
        openLeaveDoor();
    }

    // opens the bottom door
    public void openBottomDoor() {
        bottomDoor.playSound();
        removeBottomDoor();
    }

    // closes the bottom door
    public void closeBottomDoor() {
        addBottomDoor();
        bottomDoor.playSound();
    }

    // opens the top door
    public void openTopDoor() {
        topDoor.playSound();
        removeTopDoor();
    }

    // closes the top door
    public void closeTopDoor() {
        addTopDoor();
        topDoor.playSound();
    }

    // opens the leave door
    public void openLeaveDoor() {
        leaveDoor.playSound();
        removeLeaveDoor();
    }

    // closes the leave door
    public void closeLeaveDoor() {
        addLeaveDoor();
        leaveDoor.playSound();
    }
    
    // makes player leave the level to the elevator
    public void leave() {
        if (getElevator() == null) {
            setElevator(new Elevator(this, new Level4(getTimer(), getPlayer1(), getPlayer2()), getTimer(), getPlayer1(), getPlayer2())); // TODO(): change next level
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
        image1 = new GreenfootImage("level/3/0.png");
        image1.scale(getWidth(), getHeight());
        image2 = new GreenfootImage("level/3/1.png");
        image2.scale(getWidth(), getHeight());
        image3 = new GreenfootImage("level/3/2.png");
        image3.scale(getWidth(), getHeight());
        image4 = new GreenfootImage("level/3/3.png");
        image4.scale(getWidth(), getHeight());
        setBackground(image1);
    }
    
    // adds invisible collidable objects for image objects (plants, decoration, etc)
    public void addImageObjects() {
        // walls
        addObject(new CollidableObject(40, 800), 15, 400); // wall left
        addObject(new CollidableObject(800, 50), 400, 25); // wall up
        addObject(new CollidableObject(800, 20), 400, 790); // wall down
        addObject(new CollidableObject(40, 330), 785, 635); // wall right up
        addObject(new CollidableObject(40, 330), 785, 165); // wall right down

        addObject(new CollidableObject(50, 60), 531, 43); // wall top room left up
        addObject(new CollidableObject(50, 80), 531, 191); // wall top room left down
        addObject(new CollidableObject(350, 50), 681, 232); // wall top room down

        addObject(new CollidableObject(50, 60), 531, 616); // wall bottom room left up
        addObject(new CollidableObject(50, 80), 531, 764); // wall bottom room left down
        addObject(new CollidableObject(350, 50), 681, 597); // wall bottom room up
    }

    // adds the entry lever, if not added
    public void addEntryLever() {
        if (entryLever != null) return;
        entryLever = new Lever(50, 50);
        addObject(entryLever, 687, 257);
    }

    // removes the entry lever, if added
    public void removeEntryLever() {
        if (entryLever == null) return;
        removeObject(entryLever);
        entryLever = null;
    }

    // adds non interactable entry lever
    public void addUselessEntryLever() {
        addObject(new CollidableObject(50, 50), 687, 257);
    }

    // adds the bottom door, if not added
    public void addBottomDoor() {
        if (bottomDoor != null) return;
        bottomDoor = new Door(50, 80);
        addObject(bottomDoor, 531, 685);
    }
    
    // removes the bottom door, if added
    public void removeBottomDoor() {
        if (bottomDoor == null) return;
        removeObject(bottomDoor);
        bottomDoor = null;
        setBackground(image2);
    }

    // adds the bottom lever, if not added
    public void addBottomLever() {
        if (bottomLever != null) return;
        bottomLever = new Lever(50, 50);
        addObject(bottomLever, 636, 623);
    }

    // removes the bottom lever, if added
    public void removeBottomLever() {
        if (bottomLever == null) return;
        removeObject(bottomLever);
        bottomLever = null;
    }

    // adds non interactable bottom lever
    public void addUselessBottomLever() {
        addObject(new CollidableObject(50, 50), 636, 623);
    }

    // adds the top door, if not added
    public void addTopDoor() {
        if (topDoor != null) return;
        topDoor = new Door(50, 80);
        addObject(topDoor, 531, 112);
    }
    
    // removes the top door, if added
    public void removeTopDoor() {
        if (topDoor == null) return;
        removeObject(topDoor);
        topDoor = null;
        setBackground(image3);
    }

    // adds the top lever, if not added
    public void addTopLever() {
        if (topLever != null) return;
        topLever = new Lever(50, 50);
        addObject(topLever, 687, 50);
    }

    // adds the top lever, if added
    public void removeTopLever() {
        if (topLever == null) return;
        removeObject(topLever);
        topLever = null;
    }

    // adds non interactable bottom lever
    public void addUselessTopLever() {
        addObject(new CollidableObject(50, 50), 687, 50);
    }

    // adds the leave door, if not added
    public void addLeaveDoor() {
        if (leaveDoor != null) return;
        leaveDoor = new Door(40, 125);
        addObject(leaveDoor, 785, 400);
    }
    
    // removes leave door, if added
    public void removeLeaveDoor() {
        if (leaveDoor == null) return;
        removeObject(leaveDoor);
        leaveDoor = null;
        setBackground(image4);
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
                spawnPlayer1(getWidth()-80, 365, getPlayer1());
            } else {
                spawnPlayer1(getWidth()-80, 365);
            }
        }
    }

    // spawns a given player 1
    public void spawnPlayer1(boolean fromElevator, Player player1) {
        if (fromElevator) {
            spawnPlayer1(getWidth()-2, 365, player1);
        } else {
            spawnPlayer1(getWidth()-80, 365, player1);
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
                spawnPlayer2(getWidth()-80, 415, getPlayer2());
            } else {
                spawnPlayer2(getWidth()-80, 415);
            }
        }
    }

    // spawns a given player 2
    public void spawnPlayer2(boolean fromElevator, Player player2) {
        if (fromElevator) {
            spawnPlayer2(getWidth()-2, 415, player2);
        } else {
            spawnPlayer2(getWidth()-80, 415, player2);
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
    
    public void setImage3(GreenfootImage image3) {
        this.image3 = image3;
    }

    public GreenfootImage getImage3() {
        return this.image3;
    }

    public void setImage4(GreenfootImage image4) {
        this.image4 = image4;
    }

    public GreenfootImage getImage4() {
        return this.image4;
    }

    public void setEntryLever(Lever entryLever) {
        this.entryLever = entryLever;
    }

    public Lever getEntryLever() {
        return this.entryLever;
    }

    public void setBottomDoor(Door bottomDoor) {
        this.bottomDoor = bottomDoor;
    }
    
    public Door getBottomDoor() {
        return this.bottomDoor;
    }

    public void setBottomLever(Lever bottomLever) {
        this.bottomLever = bottomLever;
    }

    public Lever getBottomLever() {
        return this.bottomLever;
    }

    public void setTopDoor(Door topDoor) {
        this.topDoor = topDoor;
    }
    
    public Door getTopDoor() {
        return this.topDoor;
    }

    public void setTopLever(Lever topLever) {
        this.topLever = topLever;
    }

    public Lever getTopLever() {
        return this.topLever;
    }

    public void setLeaveDoor(Door leaveDoor) {
        this.topDoor = topDoor;
    }
    
    public Door getLeaveDoor() {
        return this.leaveDoor;
    }

}

