import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class GameOver extends World {

    private Text restartText;
    private SwitchWorldAnimation leaveAnimation;
    
    public GameOver() {
        super(800, 800, 1);
        setPaintOrder(SwitchWorldAnimation.class, Text.class);
        setBackground();
        addRestartText();
    }
    
    public void act() {
        restart();
    }
    
    // sets background and draws game over text
    public void setBackground() {
        // background's background
        setBackground(new GreenfootImage(getWidth(), getHeight()));
        getBackground().setColor(Color.BLACK);
        getBackground().fill();

        // background's game over
        getBackground().setColor(Color.RED);
        getBackground().setFont(new Font("Arial", true, false, 125));
        getBackground().drawString("Perdeu!", getWidth()/2-225, getHeight()/2-25);
    }

    // adds restart text, if added replaces
    public void addRestartText() {
        if (restartText != null) removeRestartText();
        restartText = new Text(525, 50, "Pressione espaço para recomeçar!", Color.WHITE, "Arial", true, true, 30);
        addObject(restartText, getWidth()/2, getHeight()-200);
    }

    // removes restart text, if added
    public void removeRestartText() {
        if (restartText == null) return;
        removeObject(restartText);
        restartText = null;
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
    
    // checks when user press space to restart and when can restart
    public void restart() {
        if (leaveAnimation == null) {
            if (Greenfoot.isKeyDown("space")) {
                removeRestartText();
                addLeaveAnimation();
            }
        } else {
            if (leaveAnimation.hasEnded()) {
                Greenfoot.setWorld(new Menu());
            }
        }
    }
    
    // getters and setters
    public void setRestartText(Text restartText) {
        this.restartText = restartText;
    }

    public Text getRestartText() {
        return this.restartText;
    }

    public void setLeaveAnimation(SwitchWorldAnimation leaveAnimation) {
        this.leaveAnimation = leaveAnimation;
    }
    
    public SwitchWorldAnimation getLeaveAnimation() {
        return this.leaveAnimation;
    }
    
}
