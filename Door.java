import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Door extends CollidableObject {

    // door invisible object
    public Door(int width, int height) {
        super(width, height);
    }

    // door object with given image path
    public Door(String image) {
        super(image);
    }

    // door object with given image object
    public Door(GreenfootImage image) {
        super(image);
    }

    // plays the door sound
    public void playSound() {
        Greenfoot.playSound("door.mp3");
    }
    
}
