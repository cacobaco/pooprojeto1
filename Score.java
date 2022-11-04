import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Score extends Actor {

    private int value;
    private final int defDelay;
    private int delay;
    private boolean count;

    public Score(int value, int defDelay) {
        this.value = value;
        this.defDelay = defDelay;
        this.delay = defDelay;
        this.count = true;
        setImage();
    }

    public Score(int value, int defDelay, boolean count) {
        this.value = value;
        this.defDelay = defDelay;
        this.delay = defDelay;
        this.count = count;
        setImage();
    }
    
    public void act() {
        update();
    }
    
    // updates the score
    public void update() {
        if (!count) return;

        if (--delay <= 0) {
            this.value--;
            setImage();
            this.delay = defDelay;
        }
    }
    
    // sets score image
    public void setImage() {
        setImage(new GreenfootImage("Score: " + value, 25, Color.WHITE, null));
    }
    
    // getters and setters
    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public int getDefDelay() {
        return this.defDelay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    public boolean isCount() {
        return this.count;
    }
    
}
