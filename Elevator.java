import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Elevator extends GameWorld {
    
    private static boolean keyInserted; // if key was inserted

    private GreenfootImage image1; // open door
    private GreenfootImage image2; // closed door
    private Level currentLevel; // when they don't use the elevator
    private Level nextLevel; // when they use the elevator
    private Door door; // null if open
    private Lever lever; // null if pressed
    private GreenfootSound sound; // null if stopped
     
    // constructor for debug
    public Elevator() {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, Player.class, GameObject.class);
        setBackground();
        this.currentLevel = null;
        this.nextLevel = null;
        addImageObjects();
        addLever();
        spawnPlayer1(1, 400);
        spawnPlayer2(1, 400);
        debug();
    }
    
    public Elevator(int width, int height, Level currentLevel, Level nextLevel) {
        super(width, height);
        setPaintOrder(SwitchWorldAnimation.class, Player.class, GameObject.class);
        setBackground();
        this.currentLevel = currentLevel;
        this.nextLevel = nextLevel;
        addImageObjects();
        addLever();
        addJoinAnimation();
        freezePlayers();
        spawnPlayer1(1, height/2);
        spawnPlayer2(1, height/2);
    }

    public Elevator(int width, int height, Level currentLevel, Level nextLevel, Player player1, Player player2) {
        super(width, height);
        setPaintOrder(SwitchWorldAnimation.class, Player.class, GameObject.class);
        setBackground();
        this.currentLevel = currentLevel;
        this.nextLevel = nextLevel;
        addImageObjects();
        addLever();
        addJoinAnimation();
        freezePlayers();
        spawnPlayer1(1, height/2, player1);
        spawnPlayer2(1, height/2, player2);
    }

    public void act() {
        checkStart();
        checkPullLever();
        checkStopMoving();
        checkLeave();
    }

    // checks when the level can start and starts it
    public void checkStart() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            start();
        }
    }

    // checks if player is pulling the lever
    public void checkPullLever() {
        if (lever == null) return;

        if (getPlayer1().getWorld() == null || getPlayer2().getWorld() == null) return;

        if (!keyInserted) {
            if (getPlayer1().getHoldingItem() instanceof Key && getPlayer1().isInteracting(Lever.class)) {
                keyInserted = true;
                getPlayer1().destroyItem();
                pullLever();
            }

            if (getPlayer2().getHoldingItem() instanceof Key && getPlayer2().isInteracting(Lever.class)) {
                keyInserted = true;
                getPlayer2().destroyItem();
                pullLever();
            }
        } else {
            if (getPlayer1().isInteracting(Lever.class)) {
                pullLever();
            }

            if (getPlayer2().isInteracting(Lever.class)) {
                pullLever();
            }
        }
    }

    // checks when the elevator stops moving
    public void checkStopMoving() {
        if (sound == null) return;

        if (sound.isPlaying()) return;

        stopMoving();
    }

    // checks when players leave the elevator
    public void checkLeave() {
        if (getPlayer1().getWorld() != null) {
            if (getPlayer1().getX() <= 0) {
                removeObject(getPlayer1().getHoldingItem());
                removeObject(getPlayer1());
            }
        } else {
            if (!getPlayer1().isFreeze() && Greenfoot.isKeyDown(getPlayer1().getControls()[3])) {
                addObject(getPlayer1(), 1, getHeight()/2);
            }
        }

        if (getPlayer2().getWorld() != null) {
            if (getPlayer2().getX() <= 0) {
                removeObject(getPlayer2().getHoldingItem());
                removeObject(getPlayer2());
            }
        } else {
            if (!getPlayer2().isFreeze() && Greenfoot.isKeyDown(getPlayer2().getControls()[3])) {
                addObject(getPlayer2(), 1, getHeight()/2);
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

    // starts the elevator "level"
    public void start() {
        removeJoinAnimation();
        unfreezePlayers();
    }

    // pulls the lever
    public void pullLever() {
        if (lever == null) return;
        addDoor();
        addUselessLever();
        removeLever();
        startMoving();
    }

    // makes the elevator start moving
    public void startMoving() {
        playSound();
    }

    // makes the elevator stop moving
    public void stopMoving() {
        stopSound();
        removeDoor();
    }

    // makes player leave the elevator either to the current level (if elevator wasn't used) or to the next level (if elevator was used)
    public void leave() {
        if (lever != null) {
            currentLevel.setPlayer1(getPlayer1());
            currentLevel.setPlayer2(getPlayer2());
            currentLevel.setElevator(this);
            Greenfoot.setWorld(currentLevel);
            currentLevel.spawnPlayer1(currentLevel.getWidth() - 2, currentLevel.getHeight()/2, getPlayer1());
            currentLevel.spawnPlayer2(currentLevel.getWidth() - 2, currentLevel.getHeight()/2, getPlayer2());
            currentLevel.addJoinAnimation();
        } else {
            nextLevel.setPlayer1(getPlayer1());
            nextLevel.setPlayer2(getPlayer2());
            Greenfoot.setWorld(nextLevel);
        }
    }

    // sets the background
    public void setBackground() {
        setBackground(new GreenfootImage(getWidth(), getHeight()));
        getBackground().setColor(Color.BLACK);
        getBackground().fill();
        image1 = new GreenfootImage("elevator/0.png");
        image2 = new GreenfootImage("elevator/1.png");
        drawImage(image1);
    }

    // resets background
    public void resetBackground() {
        getBackground().clear();
        getBackground().setColor(Color.BLACK);
        getBackground().fill();
    }

    // draws the given image onto the left middle side of the world
    public void drawImage(GreenfootImage image) {
        int y = getHeight()/2 - image.getHeight()/2;
        getBackground().drawImage(image, 0, y);
    }

    // adds invisible collidable objects for image objects (plants, decoration, etc)
    public void addImageObjects() {
        // walls
        addObject(new CollidableObject(45, 100), 20, 290); // wall left down
        addObject(new CollidableObject(45, 120), 20, 505); // wall left up
        addObject(new CollidableObject(310, 60), 155, 275); // wall up
        addObject(new CollidableObject(45, 310), 290, 400); // wall right
        addObject(new CollidableObject(310, 25), 155, 545); // wall down
    }

    // adds the door, if not added
    public void addDoor() {
        if (door != null) return;
        door = new Door(45, 105);
        addObject(door, 20, 392);
        drawImage(image2);

        if (getPlayer1().getX() < 75) {
            getPlayer1().setLocation(75, getPlayer1().getY());
        }

        if (getPlayer2().getX() < 75) {
            getPlayer2().setLocation(75, getPlayer2().getY());
        }
    }

    // removes the door, if added
    public void removeDoor() {
        if (door == null) return;
        removeObject(door);
        door = null;
        drawImage(image1);
    }

    // adds the lever, if not added
    public void addLever() {
        if (lever != null) return;
        lever = new Lever(50, 50);
        addObject(lever, 231, 318);
    }

    // removes the lever, if added (the object, lever image remains)
    public void removeLever() {
        if (lever == null) return;
        removeObject(lever);
        lever = null;
    }

    // adds non interactable lever
    public void addUselessLever() {
        addObject(new CollidableObject(50, 50), 231, 318);
    }

    // plays the moving sound, if not playing
    public void playSound() {
        if (sound != null) return;
        sound = new GreenfootSound("elevator/elevator.mp3");
        sound.play();
    }

    // stops the moving sound, if playing
    public void stopSound() {
        if (sound == null) return;
        sound.stop();
        sound = null;
    }
    
    // getters and setters
    public static void setKeyInserted(boolean keyInserted) {
        Elevator.keyInserted = keyInserted;
    }

    public static boolean isKeyInserted() {
        return Elevator.keyInserted;
    }

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

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    public void setNextLevel(Level nextLevel) {
        this.nextLevel = nextLevel;
    }

    public Level getNextLevel() {
        return this.nextLevel;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public Door getDoor() {
        return this.door;
    }

    public void setLever(Lever lever) {
        this.lever = lever;
    }

    public Lever getLever() {
        return this.lever;
    }

    public void setSound(GreenfootSound sound) {
        this.sound = sound;
    }

    public GreenfootSound getSound() {
        return this.sound;
    }
    
}
