import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Elevator extends GameWorld {
    
    private static boolean keyFound; // if key was found

    private GreenfootImage image1; // open door
    private GreenfootImage image2; // closed door
    private Level currentLevel; // when they don't use the elevator
    private Level nextLevel; // when they use the elevator

    private Lever lever; // null if pressed
    private Door door; // null if open
    
    public Elevator() {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, LifeHeart.class, Player.class, GameObject.class);
        setBackground();
        this.currentLevel = currentLevel;
        this.nextLevel = nextLevel;
        addImageObjects();
        addLever();
        addJoinAnimation();
        blockMovement();
        spawnPlayer1(400, 400);
        spawnPlayer2(400, 400);
        addLifePlayer1();
        addLifePlayer2();
        debug();
    }
    
    public Elevator(int width, int height, Level currentLevel, Level nextLevel, boolean closeDoor) {
        super(width, height);
        setPaintOrder(SwitchWorldAnimation.class, LifeHeart.class, Player.class, GameObject.class);
        setBackground();
        this.currentLevel = currentLevel;
        this.nextLevel = nextLevel;
        addImageObjects();
        addLever();
        addDoor();
        addJoinAnimation();
        blockMovement();
        spawnPlayer1(width/2, height/2);
        spawnPlayer2(width/2, height/2);
        addLifePlayer1();
        addLifePlayer2();
        debug();
    }

    public Elevator(int width, int height, Level currentLevel, Level nextLevel, boolean closeDoor, Player player1, Player player2) {
        super(width, height);
        setPaintOrder(SwitchWorldAnimation.class, LifeHeart.class, Player.class, GameObject.class);
        setBackground();
        this.currentLevel = currentLevel;
        this.nextLevel = nextLevel;
        addImageObjects();
        addLever();
        addDoor();
        addJoinAnimation();
        blockMovement();
        spawnPlayer1(width/2, height/2, player1);
        spawnPlayer2(width/2, height/2, player2);
        addLifePlayer1();
        addLifePlayer2();
        debug();
    }

    public void act() {
        start();
    }

    // sets the background
    public void setBackground() {
        setBackground(new GreenfootImage(getWidth(), getHeight()));
        getBackground().setColor(Color.BLACK);
        getBackground().fill();
        image1 = new GreenfootImage("elevator/0.png");
        image2 = new GreenfootImage("elevator/1.png");
    }

    // resets background
    public void resetBackground() {
        getBackground().clear();
        getBackground().setColor(Color.BLACK);
        getBackground().fill();
    }

    // draws the given image onto the middle of the world
    public void drawImage(GreenfootImage image) {
        int x = getWidth()/2 - image.getWidth()/2;
        int y = getHeight()/2 - image.getHeight()/2;
        getBackground().drawImage(image, x, y);
    }

    // adds invisible collidable objects for image objects (plants, decoration, etc)
    public void addImageObjects() {
        // walls
        addObject(new CollidableObject(45, 100), 265, 290);
        addObject(new CollidableObject(45, 120), 265, 505);
        addObject(new CollidableObject(310, 60), 400, 275);
        addObject(new CollidableObject(45, 310), 535, 400);
        addObject(new CollidableObject(310, 25), 400, 545);

        // non interactable lever
        addObject(new CollidableObject(50, 50), 475, 315);
    }

    // adds the lever, if not added
    public void addLever() {
        if (lever != null) return;
        lever = new Lever(50, 50);
        addObject(lever, 475, 315);
    }

    // removes the lever, if added
    public void removeLever() {
        if (lever == null) return;
        removeObject(lever);
        lever = null;
    }

    // adds the door, if not added
    public void addDoor() {
        if (door != null) return;
        door = new Door(45, 105);
        addObject(door, 265, 392);
        drawImage(image1);
    }

    // removes the door, if added
    public void removeDoor() {
        if (door == null) return;
        removeObject(door);
        door = null;
        drawImage(image2);
    }

    // checks when the game can start and starts it
    public void start() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            removeJoinAnimation();
            unblockMovement();
        }
    }
    
    // getters and setters
    public static void setKeyFound(boolean keyFound) {
        Elevator.keyFound = keyFound;
    }

    public static boolean isKeyFound() {
        return Elevator.keyFound;
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

    public void setLever(Lever lever) {
        this.lever = lever;
    }

    public Lever getLever() {
        return this.lever;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public Door getDoor() {
        return this.door;
    }
    
}
