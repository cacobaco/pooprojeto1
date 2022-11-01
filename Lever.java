import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Lever extends CollidableObject {

    // lever invisible object
    public Lever(int width, int height) {
        super(width, height);
    }

    // lever object with given image path
    public Lever(String image) {
        super(image);
    }

    // lever object with given image object
    public Lever(GreenfootImage image) {
        super(image);
    }
    
}
