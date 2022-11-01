import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class TransposableObject extends GameObject {
    
    // collidable invisible object (eg. for backgrounds)
    public TransposableObject(int width, int height) {
        super(width, height);
    }

    // object with a given image path
    public TransposableObject(String image) {
        super(image);
    }

    // object with a given image object
    public TransposableObject(GreenfootImage image) {
        super(image);
    }
    
}
