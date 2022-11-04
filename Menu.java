import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Menu extends AnimatedWorld {

    private PlayButton playButton;
    
    public Menu() {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, Button.class);
        setBackground();
        addPlayButton();
    }
    
    public void act() {
        checkStart();
    }

    // checks when user press play button and when can start
    public void checkStart() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            removeJoinAnimation();
        }

        if (getJoinAnimation() == null) {
            if (getLeaveAnimation() == null) {
                if (playButton.isPressed()) {
                    removePlayButton();
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
        Greenfoot.setWorld(new Controls());
    }
    
    // sets menu's background
    public void setBackground() {
        setBackground("menu.png");
        getBackground().scale(getWidth(), getHeight());
    }
    
    // adds play button, if not added
    public void addPlayButton() {
        if (playButton != null) return;
        playButton = new PlayButton();
        addObject(playButton, 425, 690);
    }

    // removes play button, if added
    public void removePlayButton() {
        if (playButton == null) return;
        removeObject(playButton);
        playButton = null;
    }
    
    // getters and setters
    public void setPlayButton(PlayButton playButton) {
        this.playButton = playButton;
    }
    
    public PlayButton getPlayButton() {
        return this.playButton;
    }
    
}
