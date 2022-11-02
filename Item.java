import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Item extends GameObject {
    
    private boolean pickable;
    
    // object with a given image path
    public Item(String image) {
        super(image);
        this.pickable = true;
    }

    // object with a given image object
    public Item(GreenfootImage image) {
        super(image);
        this.pickable = true;
    }
    
    // getters and setters
    public void setPickable(boolean pickable) {
        this.pickable = pickable;
    }
    
    public boolean isPickable() {
        return this.pickable;
    }
    
}
