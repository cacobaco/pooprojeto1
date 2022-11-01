import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Menu extends World {

    private PlayButton playButton;
    private SwitchWorldAnimation leaveAnimation;
    
    public Menu() {
        super(800, 800, 1);
        setPaintOrder(SwitchWorldAnimation.class, Button.class);
        setBackground();
        addPlayButton();
    }
    
    public void act() {
        start();
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
        addObject(playButton, getWidth()/2, getHeight() - getHeight()/10);
    }

    // removes play button, if added
    public void removePlayButton() {
        if (playButton == null) return;
        removeObject(playButton);
        playButton = null;
    }

    // adds default leave animation, if one is active replaces
    public void addLeaveAnimation() {
        if (this.leaveAnimation != null) removeLeaveAnimation();
        this.leaveAnimation = new SwitchWorldAnimation(getWidth(), getHeight(), true);
        addObject(leaveAnimation, getWidth()/2, getHeight()/2);
    }

    // adds leave animation, if one is active replaces
    public void addLeaveAnimation(SwitchWorldAnimation leaveAnimation) {
        if (this.leaveAnimation != null) removeLeaveAnimation();
        this.leaveAnimation = leaveAnimation;
        addObject(leaveAnimation, getWidth()/2, getHeight()/2);
    }

    // removes leave animation, if active
    public void removeLeaveAnimation() {
        if (leaveAnimation == null) return;
        removeObject(leaveAnimation);
        leaveAnimation = null;
    }
    
    // checks when user press play button and when can start
    public void start() {
        if (leaveAnimation == null) {
            if (playButton.isPressed()) {
                removePlayButton();
                addLeaveAnimation();
            }
        } else {
            if (leaveAnimation.hasEnded()) {
                Greenfoot.setWorld(new Level0());
            }
        }
    }
    
    // getters and setters
    public void setPlayButton(PlayButton playButton) {
        this.playButton = playButton;
    }
    
    public PlayButton getPlayButton() {
        return this.playButton;
    }
    
    public void setLeaveAnimation(SwitchWorldAnimation leaveAnimation) {
        this.leaveAnimation = leaveAnimation;
    }
    
    public SwitchWorldAnimation getLeaveAnimation() {
        return this.leaveAnimation;
    }
    
}
