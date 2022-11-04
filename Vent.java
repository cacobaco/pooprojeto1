import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Vent extends TransposableObject {

    private boolean open;

    // vent invisible object
    public Vent(int width, int height) {
        super(width, height);
    }

    // vent object with given image path
    public Vent(String image) {
        super(image);
    }

    // vent object with given image object
    public Vent(GreenfootImage image) {
        super(image);
    }

    // opens the vent
    public void open() {
        this.open = true;
    }

    // closes the vent
    public void close() {
        this.open = false;
    }

    // getters and setters
    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return this.open;
    }
    
}
