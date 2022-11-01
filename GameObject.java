import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class GameObject extends Actor {
    
    // invisible object (eg. for backgrounds)
    public GameObject(int width, int height) {
        setImage(new GreenfootImage(width, height));
    }

    // object with a given image path
    public GameObject(String image) {
        setImage(image);
    }

    // object with a given image object
    public GameObject(GreenfootImage image) {
        setImage(image);
    }
    
    // draws a red border
    public void debug() {
        getImage().setColor(Color.RED);
        getImage().drawRect(0, 0, getImage().getWidth() - 1, getImage().getHeight() - 1);
    }
    
    // resizes the object and draws a red border
    public void debug(int width, int height) {
        setImage(new GreenfootImage(width, height));
        getImage().setColor(Color.RED);
        getImage().drawRect(0, 0, getImage().getWidth() - 1, getImage().getHeight() - 1);
    }
    
}
