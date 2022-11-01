import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Car extends CollidableObject {
    
    // car object with default image
    public Car() {
        super("vehicles/car.png");
    }

    // car invisible object
    public Car(int width, int height) {
        super(width, height);
    }

    // car object with given image path
    public Car(String image) {
        super(image);
    }

    // car object with given image object
    public Car(GreenfootImage image) {
        super(image);
    }
    
}
