import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Bike extends CollidableObject {
    
    // bike object with default image
    public Bike() {
        super("vehicles/bike.png");
    }

    // bike invisible object
    public Bike(int width, int height) {
        super(width, height);
    }

    // bike object with given image path
    public Bike(String image) {
        super(image);
    }

    // bike object with given image object
    public Bike(GreenfootImage image) {
        super(image);
    }
    
}
