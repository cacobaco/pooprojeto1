import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Level5 extends Level {

    private GreenfootImage image1; // all doors closed image
    private GreenfootImage image2; // button door open and leave door closed image
    private GreenfootImage image3; // all doors open image
    private Box box; // null if was open (got the crowbar)
    private Lever lever; // null if was pulled
    private Door buttonDoor; // null if open
    private Vent vent; // null if got the key
    private Door leaveDoor; // null if open
    private Helicopter helicopter;
    private GreenfootSound helicopterSound;
    private Creature creature;
    
    public Level5() {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, StaminaBar.class, Creature.class, Player.class, GameObject.class);
        setBackground();
        addTimer(true);
        addImageObjects();
        addBox();
        addLever();
        addButtonDoor();
        addVent();
        addLeaveDoor();
        addHelicopter();
        spawnPlayer1(false);
        spawnPlayer2(false);
        addStaminaBar1();
        addStaminaBar2();
        spawnCreature();
        playHelicopterSound();
        debug();
    }
    
    public Level5(Timer timer, Player player1, Player player2) {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, StaminaBar.class, Creature.class, Player.class, GameObject.class);
        setBackground();
        timer.setCount(false);
        addTimer(timer);
        addImageObjects();
        addBox();
        addLever();
        addButtonDoor();
        addVent();
        addLeaveDoor();
        addHelicopter();
        addJoinAnimation();
        spawnPlayer1(false, player1);
        spawnPlayer2(false, player2);
        addStaminaBar1();
        addStaminaBar2();
        spawnCreature();
        creature.freeze();
        playHelicopterSound();
        freezePlayers();
    }
    
    public void act() {
        checkStart();
        checkOpenBox();
        checkPullLever();
        checkOpenVent();
        checkOpenLeaveDoor();
        checkLeave();
        checkGameOver();
    }

    // checks when join animation ends so player can start moving
    public void checkStart() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            start();
        }
    }
    
    // checks if player is openning the box
    public void checkOpenBox(){
        if (box == null) return;

        if (getPlayer1().isInteracting(box)) {
            openBox(getPlayer1());
        }

        if (getPlayer2().isInteracting(box)) {
            openBox(getPlayer2());
        }
    }

    // checks if player is pulling the lever
    public void checkPullLever() {
        if (lever == null) return;
        if (getPlayer1().isInteracting(lever) || getPlayer2().isInteracting(lever)) {
            pullLever();
        }
    }

    // checks if player is openning the vent
    public void checkOpenVent() {
        if (vent == null || vent.isOpen()) return;

        if (getPlayer1().getHoldingItem() instanceof Crowbar && getPlayer1().isInteracting(vent)) {
            getPlayer1().destroyItem();
            vent.open();
            addKey();
        }

        if (getPlayer2().getHoldingItem() instanceof Crowbar && getPlayer2().isInteracting(vent)) {
            getPlayer2().destroyItem();
            vent.open();
            addKey();
        }
    }

    // checks if player is openning the leave door
    public void checkOpenLeaveDoor() {
        if (leaveDoor == null) return;
        
        if (getPlayer1().getHoldingItem() instanceof Key && getPlayer1().isInteracting(leaveDoor)) {
            getPlayer1().destroyItem();
            openLeaveDoor();
        }
        
        if (getPlayer2().getHoldingItem() instanceof Key && getPlayer2().isInteracting(leaveDoor)) {
            getPlayer2().destroyItem();
            openLeaveDoor();
        }
    }

    // checks if player is leaving the level (entering the helicopter)
    public void checkLeave() {
        if (getPlayer1().getWorld() != null && getPlayer1().isInteracting(helicopter)) {
            removeObject(getPlayer1());
            removeObject(getPlayer1().getHoldingItem());
        }

        if (getPlayer2().getWorld() != null && getPlayer2().isInteracting(helicopter)) {
            removeObject(getPlayer2());
            removeObject(getPlayer2().getHoldingItem());
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
        getTimer().setCount(true);
        removeJoinAnimation();
        unfreezePlayers();
        creature.unfreeze();
    }

    // opens the box
    public void openBox(Player player) {
        removeBox();
        addUselessBox();
        player.pickupItem(new Crowbar());
    }

    // pulls the lever
    public void pullLever() {
        removeLever();
        addUselessLever();
        openButtonDoor();
    }

    // opens the button door
    public void openButtonDoor() {
        buttonDoor.playSound();
        removeButtonDoor();
    }

    // closes the button door
    public void closeButtonDoor() {
        addButtonDoor();
        buttonDoor.playSound();
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
        stopHelicopterSound();
        Greenfoot.setWorld(new GameWin(getTimer().getValue()));
    }

    // game over
    public void gameOver() {
        stopHelicopterSound();
        Greenfoot.setWorld(new GameOver());
    }

    // sets the background
    public void setBackground() {
        image1 = new GreenfootImage("level/5/0.png");
        image1.scale(getWidth(), getHeight());
        image2 = new GreenfootImage("level/5/1.png");
        image2.scale(getWidth(), getHeight());
        image3 = new GreenfootImage("level/5/2.png");
        image3.scale(getWidth(), getHeight());
        setBackground(image1);
    }
    
    // adds invisible collidable objects for image objects (plants, decoration, etc)
    public void addImageObjects() {
        // walls
        addObject(new CollidableObject(40, 800), 18, 400); // wall left
        addObject(new CollidableObject(800, 50), 400, 775); // wall down
        addObject(new CollidableObject(40, 800), 788, 400); // wall and door right

        addObject(new CollidableObject(250, 60), 663, 28); // wall button room top
        addObject(new CollidableObject(50, 400), 534, 200); // wall button room left
        addObject(new CollidableObject(315, 90), 456, 376); // wall button room down left
        addObject(new CollidableObject(60, 90), 747, 376); // wall button room down right

        addObject(new CollidableObject(200, 90), 97, 376); // wall leave room down left

        // poste
        addObject(new CollidableObject(100, 150), 750, 692);
    }

    // adds the box, if not added
    public void addBox() {
        if (box != null) return;
        box = new Box(60, 60);
        addObject(box, 742, 86);
    }

    // removes the box, if added
    public void removeBox() {
        if (box == null) return;
        removeObject(box);
        box = null;
    }

    // adds non interactable box
    public void addUselessBox() {
        addObject(new CollidableObject(60, 60), 742, 86);
    }

    // adds the lever, if not added
    public void addLever() {
        if (lever != null) return;
        lever = new Lever(50, 50);
        addObject(lever, 535, 423);
    }

    // removes the lever, if added
    public void removeLever() {
        if (lever == null) return;
        removeObject(lever);
        lever = null;
    }

    // adds non interactable lever
    public void addUselessLever() {
        addObject(new CollidableObject(50, 50), 535, 423);
    }
    
    // adds the button door, if not added
    public void addButtonDoor() {
        if (buttonDoor != null) return;
        buttonDoor = new Door(100, 50);
        addObject(buttonDoor, 665, 396);
    }

    // removes the locker door, if added
    public void removeButtonDoor() {
        if (buttonDoor == null) return;
        removeObject(buttonDoor);
        buttonDoor = null;
        setBackground(image2);
    }

    // adds the vent, if not added
    public void addVent() {
        if (vent != null) return;
        vent = new Vent(50, 50);
        addObject(vent, 118, 706);
    }

    // removes the vent, if added
    public void removeVent() {
        if (vent == null) return;
        removeObject(vent);
        vent = null;
    }

    // adds the key
    public void addKey() {
        addObject(new Key(), 88, 704);
    }

    // adds the leave door, if not added
    public void addLeaveDoor() {
        if (leaveDoor != null) return;
        leaveDoor = new Door(100, 50);
        addObject(leaveDoor, 248, 396);
    }

    // removes the locker door, if added
    public void removeLeaveDoor() {
        if (leaveDoor == null) return;
        removeObject(leaveDoor);
        leaveDoor = null;
        setBackground(image3);
    }
    
    // adds the helicopter, if not added
    public void addHelicopter() {
        if (helicopter != null) return;
        helicopter = new Helicopter(260, 205);
        addObject(helicopter, 273, 210);
    }

    // removes the helicopter, if added
    public void removeHelicopter() {
        if (helicopter == null) return;
        removeObject(helicopter);
        helicopter = null;
    }

    // plays the helicopter sound, if not playing
    public void playHelicopterSound() {
        if (helicopterSound != null) return;
        helicopterSound = new GreenfootSound("helicopter.mp3");
        helicopterSound.playLoop();
        helicopterSound.setVolume(50);
    }

    // stops the helicopter sound, if playing
    public void stopHelicopterSound() {
        if (helicopterSound == null) return;
        helicopterSound.stop();
        helicopterSound = null;
    }

    // spawns the creature, if spawned
    public void spawnCreature() {
        if (creature != null) return;
        creature = new Creature();
        addObject(creature, 90, 515);
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
                spawnPlayer1(getWidth()-2, 490, getPlayer1());
            } else {
                spawnPlayer1(getWidth()-2, 490);
            }
        } else {
            if (getPlayer1() != null) {
                spawnPlayer1(665, 335, getPlayer1());
            } else {
                spawnPlayer1(665, 335);
            }
        }
    }

    // spawns a given player 1
    public void spawnPlayer1(boolean fromElevator, Player player1) {
        if (fromElevator) {
            spawnPlayer1(getWidth()-2, 490, player1);
        } else {
            spawnPlayer1(665, 335, player1);
        }
    }

    // spawns player 2
    public void spawnPlayer2(boolean fromElevator) {
        if (fromElevator) {
            if (getPlayer2() != null) {
                spawnPlayer2(getWidth()-2, 540, getPlayer2());
            } else {
                spawnPlayer2(getWidth()-2, 540);
            }
        } else {
            if (getPlayer2() != null) {
                spawnPlayer2(735, 515, getPlayer2());
            } else {
                spawnPlayer2(735, 515);
            }
        }
    }

    // spawns a given player 2
    public void spawnPlayer2(boolean fromElevator, Player player2) {
        if (fromElevator) {
            spawnPlayer2(getWidth()-2, 540, player2);
        } else {
            spawnPlayer2(735, 515, player2);
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

    public void setBox(Box box) {
        this.box = box;
    }

    public Box getBox() {
        return this.box;
    }

    public void setLever(Lever lever) {
        this.lever = lever;
    }

    public Lever getLever() {
        return this.lever;
    }

    public void setButtonDoor(Door buttonDoor) {
        this.buttonDoor = buttonDoor;
    }

    public Door getButtonDoor() {
        return this.buttonDoor;
    }

    public void setVent(Vent vent) {
        this.vent = vent;
    }

    public Vent getVent() {
        return this.vent;
    }

    public void setLeaveDoor(Door leaveDoor) {
        this.leaveDoor = leaveDoor;
    }

    public Door getLeaveDoor() {
        return this.leaveDoor;
    }

    public void setHelicopter(Helicopter helicopter) {
        this.helicopter = helicopter;
    }

    public Helicopter getHelicopter() {
        return this.helicopter;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    public Creature getCreature() {
        return this.creature;
    }

}