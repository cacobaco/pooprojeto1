import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Level4 extends Level {

    private GreenfootImage image1; 
    private GreenfootImage image2;
    private Lever topFakeLever1;
    private Lever topLever2;
    private Lever topFakeLever3;
    private Lever bottomLever1;
    private Lever bottomFakeLever2;
    private Lever bottomFakeLever3;
    private Door topDoor; // null if open
    private Door bottomDoor; // null if open
    private Door leaveDoor; // null if open
    
    // constructor for debug
    public Level4() {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, StaminaBar.class, Player.class, GameObject.class);
        setBackground();
        addTimer(true);
        addImageObjects();
        addTopFakeLever1();
        addTopLever2();
        addTopFakeLever3();
        addBottomLever1();
        addBottomFakeLever2();
        addBottomFakeLever3();
        addTopDoor();
        addBottomDoor();
        addLeaveDoor();
        spawnPlayer1(false);
        spawnPlayer2(false);
        addStaminaBar1();
        addStaminaBar2();
        debug();
    }
    
    public Level4(Timer timer, Player player1, Player player2) {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, StaminaBar.class, Player.class, GameObject.class);
        setBackground();
        timer.setCount(false);
        addTimer(timer);
        addImageObjects();
        addTopFakeLever1();
        addTopLever2();
        addTopFakeLever3();
        addBottomLever1();
        addBottomFakeLever2();
        addBottomFakeLever3();
        addTopDoor();
        addBottomDoor();
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
        checkPullFakeLever();
        checkPullTopLever2();
        checkPullBottomLever1();
        checkOpenDoors();
        checkLeave();
        checkGameOver();
    }

    // checks when join animation ends so player can start moving
    public void checkStart() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            start();
        }
    }

    // checks if player is pulling a fake lever
    public void checkPullFakeLever(){
        if (getPlayer1().isInteracting(topFakeLever1) || getPlayer1().isInteracting(topFakeLever3) || getPlayer1().isInteracting(bottomFakeLever2) || getPlayer1().isInteracting(bottomFakeLever3)) {
            pullFakeLever();
        }

        if (getPlayer2().isInteracting(topFakeLever1) || getPlayer2().isInteracting(topFakeLever3) || getPlayer2().isInteracting(bottomFakeLever2) || getPlayer2().isInteracting(bottomFakeLever3)) {
            pullFakeLever();
        }
    }

    // checks if player is pulling top lever 2
    public void checkPullTopLever2() {
        if (topLever2 == null) return;
        if (getPlayer1().isInteracting(topLever2) || getPlayer2().isInteracting(topLever2)) {
            pullTopLever2();
        }
    }
    
   // checks if player is pulling bottom lever 1
    public void checkPullBottomLever1() {
        if (bottomLever1 == null) return;
        if (getPlayer1().isInteracting(bottomLever1) || getPlayer2().isInteracting(bottomLever1)) {
            pullBottomLever1();
        }
    }
    
    // checks if both levers have been pulled to open the doors
    public void checkOpenDoors() {
        if (topLever2 != null || bottomLever1 != null) return;
        if (topDoor == null && bottomDoor == null && leaveDoor == null) return;
        openDoors();
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
        playSound();
        getTimer().setCount(true);
    }

    // pulls a fake lever
    public void pullFakeLever() {
        gameOver();
    }

    // pulls the top lever 2
    public void pullTopLever2() {
        removeTopLever2();
        addUselessTopLever2();
    }

    // pulls the bottom lever 1
    public void pullBottomLever1() {
        removeBottomLever1();
        addUselessBottomLever1();
    }

    // opens all the doors
    public void openDoors() {
        openTopDoor();
        openBottomDoor();
        openLeaveDoor();
        setBackground(image2);
    }

    // closes all the doors
    public void closeDoors() {
        closeTopDoor();
        closeBottomDoor();
        closeLeaveDoor();
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
            setElevator(new Elevator(this, new Level5(getTimer(), getPlayer1(), getPlayer2()), getTimer(), getPlayer1(), getPlayer2())); // TODO(): change next level
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

    // plays the malefical voice laugh sound
    public void playSound() {
        Greenfoot.playSound("laugh.mp3");
    }

    // sets the background
    public void setBackground() {
        image1 = new GreenfootImage("level/4/0.png");
        image1.scale(getWidth(), getHeight());
        image2 = new GreenfootImage("level/4/1.png");
        image2.scale(getWidth(), getHeight());
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

        addObject(new CollidableObject(400, 50), 190, 320); // wall top room down
        addObject(new CollidableObject(50, 80), 367, 72); // wall top room right up
        addObject(new CollidableObject(50, 80), 368, 256); // wall top room right down

        addObject(new CollidableObject(400, 50), 190, 493); // wall bottom room up
        addObject(new CollidableObject(50, 80), 368, 514); // wall bottom room right up
        addObject(new CollidableObject(50, 80), 366, 720); // wall top room right down
    }

    // adds the top fake lever 1, if not added
    public void addTopFakeLever1() {
        if (topFakeLever1 != null) return;
        topFakeLever1 = new Lever(50, 50);
        addObject(topFakeLever1, 61, 54);
    }
    
    // removes the top fake lever 1, if added
    public void removeTopFakeLever1() {
        if (topFakeLever1 == null) return;
        removeObject(topFakeLever1);
        topFakeLever1 = null;
    }

    // adds the top lever 2, if not added
    public void addTopLever2() {
        if (topLever2 != null) return;
        topLever2 = new Lever(50, 50);
        addObject(topLever2, 167, 54);
    }
    
    // removes the top lever 2, if added
    public void removeTopLever2() {
        if (topLever2 == null) return;
        removeObject(topLever2);
        topLever2 = null;
    }

    // adds non interactable top lever 2
    public void addUselessTopLever2() {
        addObject(new CollidableObject(50, 50), 167, 54);
    }
    
    // adds the top fake lever 3, if not added
    public void addTopFakeLever3() {
        if (topFakeLever3 != null) return;
        topFakeLever3 = new Lever(50, 50);
        addObject(topFakeLever3, 271, 63);
    }
    
    // removes the top fake lever 3, if added
    public void removeTopFakeLever3() {
        if (topFakeLever3 == null) return;
        removeObject(topFakeLever3);
        topFakeLever3 = null;
    }

    // adds the bottom lever 1, if not added
    public void addBottomLever1() {
        if (bottomLever1 != null) return;
        bottomLever1 = new Lever(50, 50);
        addObject(bottomLever1, 61, 523);
    }

    // removes the bottom lever 1, if added
    public void removeBottomLever1() {
        if (bottomLever1 == null) return;
        removeObject(bottomLever1);
        bottomLever1 = null;
    }

    // adds non interactable bottom lever 1
    public void addUselessBottomLever1() {
        addObject(new CollidableObject(50, 50), 66, 523);
    }
    
    // adds the bottom fake lever 2, if not added
    public void addBottomFakeLever2() {
        if (bottomFakeLever2 != null) return;
        bottomFakeLever2 = new Lever(50, 50);
        addObject(bottomFakeLever2, 167, 523);
    }

    // removes the bottom fake lever 2, if added
    public void removeBottomFakeLever2() {
        if (bottomFakeLever2 == null) return;
        removeObject(bottomFakeLever2);
        bottomFakeLever2 = null;
    }
    
    // adds the bottom fake lever 3, if not added
    public void addBottomFakeLever3() {
        if (bottomFakeLever3 != null) return;
        bottomFakeLever3 = new Lever(50, 50);
        addObject(bottomFakeLever3, 271, 523);
    }

    // removes the bottom fake lever 3, if added
    public void removeBottomFakeLever3() {
        if (bottomFakeLever3 == null) return;
        removeObject(bottomFakeLever3);
        bottomFakeLever3 = null;
    }

    // adds the top door, if not added
    public void addTopDoor() {
        if (topDoor != null) return;
        topDoor = new Door(50, 80);
        addObject(topDoor, 367, 157);   
    }

    // removes the top door, if added
    public void removeTopDoor() {
        if (topDoor == null) return;
        removeObject(topDoor);
        topDoor = null;
    }

    // adds the bottom door, if not added
    public void addBottomDoor() {
        if (bottomDoor != null) return;
        bottomDoor = new Door(50, 80);
        addObject(bottomDoor, 367, 609);
    }
    
    // removes the bottom door, if added
    public void removeBottomDoor() {
        if (bottomDoor == null) return;
        removeObject(bottomDoor);
        bottomDoor = null;
    }

    // adds the leave door, if not added
    public void addLeaveDoor() {
        if (leaveDoor != null) return;
        leaveDoor = new Door(40, 125);
        addObject(leaveDoor, 785, 400);
    }
    
    // removes the leave door, if added
    public void removeLeaveDoor() {
        if (leaveDoor == null) return;
        removeObject(leaveDoor);
        leaveDoor = null;
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
                spawnPlayer1(310, 175, getPlayer1());
            } else {
                spawnPlayer1(310, 175);
            }
        }
    }

    // spawns a given player 1
    public void spawnPlayer1(boolean fromElevator, Player player1) {
        if (fromElevator) {
            spawnPlayer1(getWidth()-2, 365, player1);
        } else {
            spawnPlayer1(310, 175, player1);
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
                spawnPlayer2(310, 640, getPlayer2());
            } else {
                spawnPlayer2(310, 640);
            }
        }
    }

    // spawns a given player 2
    public void spawnPlayer2(boolean fromElevator, Player player2) {
        if (fromElevator) {
            spawnPlayer2(getWidth()-2, 415, player2);
        } else {
            spawnPlayer2(310, 640, player2);
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

    public void setTopFakeLever1(Lever topFakeLever1) {
        this.topFakeLever1 = topFakeLever1;
    }

    public Lever getTopFakeLever1() {
        return this.topFakeLever1;
    }

    public void setTopLever2(Lever topLever2) {
        this.topLever2 = topLever2;
    }

    public Lever getTopLever2() {
        return this.topLever2;
    }

    public void setTopFakeLever3(Lever topFakeLever3) {
        this.topFakeLever3 = topFakeLever3;
    }

    public Lever getTopFakeLever3() {
        return this.topFakeLever3;
    }

    public void setBottomLever1(Lever bottomLever1) {
        this.bottomLever1 = bottomLever1;
    }

    public Lever getBottomLever1() {
        return this.bottomLever1;
    }

    public void setBottomFakeLever2(Lever bottomFakeLever2) {
        this.bottomFakeLever2 = bottomFakeLever2;
    }

    public Lever getBottomFakeLever2() {
        return this.bottomFakeLever2;
    }

    public void setBottomFakeLever3(Lever topFakeLever3) {
        this.bottomFakeLever3 = bottomFakeLever3;
    }

    public Lever getBottomFakeLever3() {
        return this.bottomFakeLever3;
    }

    public void setTopDoor(Door topDoor) {
        this.topDoor = topDoor;
    }

    public Door getTopDoor() {
        return this.topDoor;
    }

    public void setBottomDoor(Door bottomDoor) {
        this.bottomDoor = bottomDoor;
    }

    public Door getBottomDoor() {
        return this.bottomDoor;
    }

    public void setLeaveDoor(Door leaveDoor) {
        this.leaveDoor = leaveDoor;
    }

    public Door getLeaveDoor() {
        return this.leaveDoor;
    }
      
}

 

