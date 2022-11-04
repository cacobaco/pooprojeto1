import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Box extends CollidableObject {

    // box invisible object
    public Box(int width, int height) {
        super(width, height);
    }

    // box object with given image path
    public Box(String image) {
        super(image);
    }

    // box object with given image object
    public Box(GreenfootImage image) {
        super(image);
    }
    
}
