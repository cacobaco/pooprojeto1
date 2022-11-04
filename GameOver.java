import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class GameOver extends AnimatedWorld {

    private Text restartText; 
    
    public GameOver() {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, Text.class);
        setBackground();
        addRestartText();
        addJoinAnimation();
        playSound();
    }
    
    public void act() {
        checkRestart();
    }

    // checks when user press space to restart and when can restart
    public void checkRestart() {
        if (getJoinAnimation() != null && getJoinAnimation().hasEnded()) {
            removeJoinAnimation();
        }

        if (getJoinAnimation() == null) {
            if (getLeaveAnimation() == null) {
                if (Greenfoot.isKeyDown("space")) {
                    removeRestartText();
                    addLeaveAnimation();
                }
            } else {
                if (getLeaveAnimation().hasEnded()) {
                    restart();
                }
            }
        }
    }

    // restarts the game
    public void restart() {
        Greenfoot.setWorld(new Controls());
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

    // plays the malefical voice laugh sound
    public void playSound() {
        Greenfoot.playSound("laugh.mp3");
    }
    
    // getters and setters
    public void setRestartText(Text restartText) {
        this.restartText = restartText;
    }

    public Text getRestartText() {
        return this.restartText;
    }
    
}
