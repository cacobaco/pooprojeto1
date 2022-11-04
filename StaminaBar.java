import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class StaminaBar extends Actor {
    
    private GreenfootImage[] images;
    private Player player;
    
    public StaminaBar(Player player) {
        setImages();
        this.player = player;
        update(player.getStamina());
    }
    
    public void act() {
        update(player.getStamina());
    }

    // updates the stamina bar
    public void update(int percent) {
        if (percent <= 0) setImage(images[0]); // 0
        else if (percent <= 25) setImage(images[1]); // 1 - 25
        else if (percent <= 50) setImage(images[2]); // 26 - 50
        else if (percent <= 75) setImage(images[3]); /// 51 - 75
        else if (percent <= 99) setImage(images[4]); // 76 - 99
        else if (percent >= 100) setImage(images[5]); // 100
    }

    // sets the images
    public void setImages() {
        this.images = new GreenfootImage[] {
            new GreenfootImage("stamina/0.png"),
            new GreenfootImage("stamina/1.png"),
            new GreenfootImage("stamina/2.png"),
            new GreenfootImage("stamina/3.png"),
            new GreenfootImage("stamina/4.png"),
            new GreenfootImage("stamina/5.png")
        };
        
        for (GreenfootImage image : images) {
            image.scale(image.getWidth()/5, image.getHeight()/5);
        }
    }

    // getters and setters
    public void setImages(GreenfootImage[] images) {
        this.images = images;
    }

    public GreenfootImage[] getImages() {
        return this.images;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
    
}
