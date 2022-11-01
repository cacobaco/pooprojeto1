import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class CollidableObject extends GameObject {
    
    // collidable invisible object
    public CollidableObject(int width, int height) {
        super(width, height);
    }

    // collidable object with given image path
    public CollidableObject(String image) {
        super(image);
    }

    // collidable object with given image object
    public CollidableObject(GreenfootImage image) {
        super(image);
    }
    
}
