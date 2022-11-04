import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Helicopter extends CollidableObject {

    // helicopter invisible object
    public Helicopter(int width, int height) {
        super(width, height);
    }

    // helicopter object with given image path
    public Helicopter(String image) {
        super(image);
    }

    // helicopter object with given image object
    public Helicopter(GreenfootImage image) {
        super(image);
    }
    
}
