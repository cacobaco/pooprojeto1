import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class SwitchWorldAnimation extends Actor {
    
    private final boolean fade;
    private final Color color;
    private int transparency;
    private boolean ended;
    
    public SwitchWorldAnimation(int width, int height, boolean fade) {
        setImage(new GreenfootImage(width, height));
        this.fade = fade;
        this.color = Color.BLACK;
        this.transparency = (fade) ? 0 : 255;
        this.ended = false;
        getImage().setTransparency(this.transparency);
        getImage().setColor(this.color);
        getImage().fill();
    }

    public SwitchWorldAnimation(int width, int height, boolean fade, Color color) {
        setImage(new GreenfootImage(width, height));
        this.fade = fade;
        this.color = color;
        this.transparency = (fade) ? 0 : 255;
        this.ended = false;
        getImage().setTransparency(this.transparency);
        getImage().setColor(this.color);
        getImage().fill();
    }
    
    public void act() {
        update();
    }
    
    // updates the animation screen
    public void update() {
        if (fade) {
            if (transparency >= 255) {
                ended = true;
            } else {
                getImage().setTransparency(transparency += 5);
            }
        } else {
            if (transparency <= 0) {
                ended = true;
            } else {
                getImage().setTransparency(transparency -= 5);
            }
        }
    }
    
    // getters and setters
    public boolean getFade() {
        return this.fade;
    }

    public Color getColor() {
        return this.color;
    }

    public void setTransparency(int transparency) {
        this.transparency = transparency;
    }

    public int getTransparency() {
        return this.transparency;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public boolean hasEnded() {
        return this.ended;
    }
    
}
