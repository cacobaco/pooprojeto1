import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Level2 extends Level {

    private GreenfootImage image1; // all door closed image
    private GreenfootImage image2; // button door open and leave dor closed image
    private GreenfootImage image3; // button door closed and leave door open image
    private GreenfootImage image4; // all door open image
    private PressurePlate plate;
    private Door buttonDoor; // null if open
    private Lever lever; // null if was pressed
    private Door leaveDoor; // null if open
    private Creature creature;

    // constructor for debug
    public Level2() {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, StaminaBar.class, Creature.class, Player.class, GameObject.class);
        setBackground();
        addTimer(true);
        addImageObjects();
        addPlate();
        addButtonDoor();
        addLever();
        addLeaveDoor();
        spawnPlayer1(false);
        spawnPlayer2(false);
        addStaminaBar1();
        addStaminaBar2();
        spawnCreature();
        debug();
    }
    
    public Level2(Timer timer, Player player1, Player player2) {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, StaminaBar.class, Creature.class, Player.class, GameObject.class);
        setBackground();
        timer.setCount(false);
        addTimer(timer);
        addImageObjects();
        addPlate();
        addButtonDoor();
        addLever();
        addLeaveDoor();
        addJoinAnimation();
        spawnPlayer1(false, player1);
        spawnPlayer2(false, player2);
        addStaminaBar1();
        addStaminaBar2();
        spawnCreature();
        creature.freeze();
        freezePlayers();
    }
    
    public void act() {
        checkStart();
        checkPressPlate();
        checkPullLever();
        checkLeave();
        checkGameOver();
    }

    // checks when join animation ends so player can start moving
    public void checkStart() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            start();
        }
    }

    // checks if player is pressing the pressure plate
    public void checkPressPlate() {
        if (plate == null) return;
        if (getPlayer1().isTouching(plate) || getPlayer2().isTouching(plate)) {
            pressPlate();
        } else {
            unpressPlate();
        }
    }
    
    // checks if player is pulling the lever
    public void checkPullLever() {
        if (lever == null) return;
        if (getPlayer1().isInteracting(lever) || getPlayer2().isInteracting(lever)) {
            pullLever();
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
        if (getPlayer1().isDead() && getPlayer2().isDead() || getTimer().getValue() <= 0) {
            gameOver();
        }
    }

    // starts the level
    public void start() {
        removeJoinAnimation();
        unfreezePlayers();
        creature.unfreeze();
        getTimer().setCount(true);
    }

    // presses the plate
    public void pressPlate() {
        if (buttonDoor == null) return;
        openButtonDoor();
    }

    // unpresses the plate
    public void unpressPlate() {
        if (buttonDoor != null) return;
        closeButtonDoor();
    }

    // pulls the lever
    public void pullLever() {
        removeLever();
        addUselessLever();
        openLeaveDoor();
    }

    // opens button door
    public void openButtonDoor() {
        buttonDoor.playSound();
        removeButtonDoor();
    }

    // closes button door
    public void closeButtonDoor() {
        addButtonDoor();
        buttonDoor.playSound();
    }

    // opens leave door
    public void openLeaveDoor() {
        leaveDoor.playSound();
        removeLeaveDoor();
    }

    // closes leave door
    public void closeLeaveDoor() {
        addLeaveDoor();
        leaveDoor.playSound();
    }

    // makes player leave the level to the elevator
    public void leave() {
        if (getElevator() == null) {
            setElevator(new Elevator(this, new Level3(getTimer(), getPlayer1(), getPlayer2()), getTimer(), getPlayer1(), getPlayer2()));
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
        image1 = new GreenfootImage("level/2/0.png");
        image1.scale(getWidth(), getHeight());
        image2 = new GreenfootImage("level/2/1.png");
        image2.scale(getWidth(), getHeight());
        image3 = new GreenfootImage("level/2/2.png");
        image3.scale(getWidth(), getHeight());
        image4 = new GreenfootImage("level/2/3.png");
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
        addObject(new CollidableObject(350, 50), 173, 545); // wall room up
        addObject(new CollidableObject(50, 80), 323, 605); // wall room right up
        addObject(new CollidableObject(50, 80), 323, 763); // wall room right down
    }

    // adds the plate, if not added
    public void addPlate() {
        if (plate != null) return;
        plate = new PressurePlate(50, 50);
        addObject(plate, 582, 701);
    }

    // removes the plate, if added
    public void removePlate() {
        if (plate == null) return;
        removeObject(plate);
        plate = null;
    }

    // adds the button door, if not added
    public void addButtonDoor() {
        if (buttonDoor != null) return;
        buttonDoor = new Door(50, 80);
        addObject(buttonDoor, 323, 684);
        setBackground((leaveDoor == null) ? image3 : image1);
    }

    // removes the button door, if added
    public void removeButtonDoor() {
        if (buttonDoor == null) return;
        removeObject(buttonDoor);
        buttonDoor = null;
        setBackground((leaveDoor == null) ? image4 : image2);
    }
    
    // adds the lever, if not added
    public void addLever() {
        if (lever != null) return;
        lever = new Lever(50, 50);
        addObject(lever, 165, 571);
    }

    // removes the lever, if added
    public void removeLever() {
        if (lever == null) return;
        removeObject(lever);
        lever = null;
    }

    // adds non interactable lever
    public void addUselessLever() {
        addObject(new CollidableObject(50, 50), 165, 571);
    }

    // adds the leave door, if not added
    public void addLeaveDoor() {
        if (leaveDoor != null) return;
        leaveDoor = new Door(40, 125);
        addObject(leaveDoor, 785, 400);
        setBackground((buttonDoor == null) ? image2 : image1);
    }
    
    // removes the leave door, if added
    public void removeLeaveDoor() {
        if (leaveDoor == null) return;
        removeObject(leaveDoor);
        leaveDoor = null;
        setBackground((buttonDoor == null) ? image4 : image3);
    }

    // spawns the creature, if spawned
    public void spawnCreature() {
        if (creature != null) return;
        creature = new Creature();
        addObject(creature, 160, 165);
    }

    // despawns the creature, it not spawned
    public void despawnCreature() {
        if (creature == null) return;
        removeObject(creature);
        creature = null;
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

    public void setPlate(PressurePlate plate) {
        this.plate = plate;
    }
    
    public PressurePlate getPlate() {
        return this.plate;
    }

    public void setButtonDoor(Door buttonDoor) {
        this.buttonDoor = buttonDoor;
    }
    
    public Door getButtonDoor() {
        return this.buttonDoor;
    }

    public void setLever(Lever lever) {
        this.lever = lever;
    }
    
    public Lever getLever() {
        return this.lever;
    }

    public void setLeaveDoor(Door leaveDoor) {
        this.leaveDoor = leaveDoor;
    }
    
    public Door getLeaveDoor() {
        return this.leaveDoor;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    public Creature getCreature() {
        return this.creature;
    }

}

