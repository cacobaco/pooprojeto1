import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Controls extends AnimatedWorld {

    public Controls() {   
        super(800, 800);
        setBackground();
        addJoinAnimation();
    }
    
    public void act(){
        checkStart();
    }

    // checks when join animation ends so player can press space button
    public void checkStart() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            removeJoinAnimation();
        }

        if (getJoinAnimation() == null) {
            if (getLeaveAnimation() == null) {
                if (Greenfoot.isKeyDown("space")) {
                    addLeaveAnimation();
                }
            } else {
                if (getLeaveAnimation().hasEnded()) {
                    start();
                }
            }
        }
    }

    // starts the game
    public void start() {
        Greenfoot.setWorld(new Level0());
    }

    // sets controls's background
    public void setBackground() {
        setBackground("controls.png");
        getBackground().scale(getWidth(), getHeight());
    }
    
}