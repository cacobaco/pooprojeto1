import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class GameWin extends AnimatedWorld {

    private final int score;
    private Text restartText;
    
    public GameWin(int score) {
        super(800, 800);
        setPaintOrder(SwitchWorldAnimation.class, Text.class);
        setBackground();
        this.score = score;
        addWinText();
        addScoreText();
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
    
    // sets background
    public void setBackground() {
        setBackground(new GreenfootImage(getWidth(), getHeight()));
        getBackground().setColor(Color.BLACK);
        getBackground().fill();
    }

    // adds win text to the background
    public void addWinText() {
        getBackground().setColor(Color.GREEN);
        getBackground().setFont(new Font("Arial", true, false, 125));
        getBackground().drawString("Venceu!", getWidth()/2-225, getHeight()/2-125);
    }
    
    // adds score text to the background
    public void addScoreText() {
        getBackground().setColor(Color.WHITE);
        getBackground().setFont(new Font("Arial", true, false, 60));
        getBackground().drawString("Score: " + score, getWidth()/2-150, getHeight()/2);
    }

    // adds restart text, if added replaces
    public void addRestartText() {
        if (restartText != null) removeRestartText();
        restartText = new Text(525, 50, "Pressione espaço para rejogar!", Color.WHITE, "Arial", true, true, 30);
        addObject(restartText, getWidth()/2 + 25, getHeight()-200);
    }

    // removes restart text, if added
    public void removeRestartText() {
        if (restartText == null) return;
        removeObject(restartText);
        restartText = null;
    }

    // plays the win sound
    public void playSound() {
        Greenfoot.playSound("win.mp3");
    }
    
    // getters and setters
    public void setRestartText(Text restartText) {
        this.restartText = restartText;
    }

    public Text getRestartText() {
        return this.restartText;
    }

    public int getScore() {
        return this.score;
    }
    
}