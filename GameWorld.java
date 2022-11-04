import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class GameWorld extends AnimatedWorld {

    private Timer timer;
    private Player player1;
    private Player player2;
    private StaminaBar staminaBar1;
    private StaminaBar staminaBar2;
    
    public GameWorld(int width, int height) {
        super(width, height);
    }

    // adds default timer, if one is active replaces
    public void addTimer() {
        if (this.timer != null) removeTimer();
        this.timer = new Timer(300, 55);
        addObject(timer, getWidth()/2, 25);
    }

    // adds default timer, if one is active replaces
    public void addTimer(boolean count) {
        if (this.timer != null) removeTimer();
        this.timer= new Timer(300, 55, count);
        addObject(timer, getWidth()/2, 25);
    }

    // adds timer, if one is active replaces
    public void addTimer(Timer timer) {
        if (this.timer != null) removeTimer();
        this.timer = timer;
        addObject(timer, getWidth()/2, 25);
    }

    // removes timer, if active
    public void removeTimer() {
        if (timer == null) return;
        removeObject(timer);
        timer = null;
    }

    // spawns a fresh player 1 at a given x and y, if one is spawned replaces
    public void spawnPlayer1(int x, int y) {
        if (player1 != null) despawnPlayer1();

        String[] controls = {"w", "a", "s", "d", "e", "f", "shift"};
        
        GreenfootImage[] images = {
            new GreenfootImage("boneco_red/w1.png"),
            new GreenfootImage("boneco_red/w2.png"),
            new GreenfootImage("boneco_red/w3.png"),
            new GreenfootImage("boneco_red/w2.png"),
            new GreenfootImage("boneco_red/a1.png"),
            new GreenfootImage("boneco_red/a2.png"),
            new GreenfootImage("boneco_red/a3.png"),
            new GreenfootImage("boneco_red/a2.png"),
            new GreenfootImage("boneco_red/s1.png"),
            new GreenfootImage("boneco_red/s2.png"),
            new GreenfootImage("boneco_red/s3.png"),
            new GreenfootImage("boneco_red/s2.png"),
            new GreenfootImage("boneco_red/d1.png"),
            new GreenfootImage("boneco_red/d2.png"),
            new GreenfootImage("boneco_red/d3.png"),
            new GreenfootImage("boneco_red/d2.png")
        };
        
        for (GreenfootImage image : images) {
            image.scale(image.getWidth()/3, image.getHeight()/3);
        }
        
        player1 = new Player(controls, images);
        
        addObject(player1, x, y);
    }

    // spawns an existent player 1 at a given x and y, if one is spawned replaces
    public void spawnPlayer1(int x, int y, Player player1) {
        if (this.player1 != null) despawnPlayer1();
        this.player1 = player1;
        addObject(player1, x, y);
    }

    // despawns player 1, if spawned
    public void despawnPlayer1() {
        if (player1 == null) return;
        removeObject(player1);
        player1 = null;
    }
    
    // spawns a fresh player 2 at a given x and y, if one is spawned replaces
    public void spawnPlayer2(int x, int y) {
        if (player2 != null) despawnPlayer2();

        String[] controls = {"i", "j", "k", "l", "u", "h", "space"};
        
        GreenfootImage[] images = {
            new GreenfootImage("boneco_purple/w1.png"),
            new GreenfootImage("boneco_purple/w2.png"),
            new GreenfootImage("boneco_purple/w3.png"),
            new GreenfootImage("boneco_purple/w2.png"),
            new GreenfootImage("boneco_purple/a1.png"),
            new GreenfootImage("boneco_purple/a2.png"),
            new GreenfootImage("boneco_purple/a3.png"),
            new GreenfootImage("boneco_purple/a2.png"),
            new GreenfootImage("boneco_purple/s1.png"),
            new GreenfootImage("boneco_purple/s2.png"),
            new GreenfootImage("boneco_purple/s3.png"),
            new GreenfootImage("boneco_purple/s2.png"),
            new GreenfootImage("boneco_purple/d1.png"),
            new GreenfootImage("boneco_purple/d2.png"),
            new GreenfootImage("boneco_purple/d3.png"),
            new GreenfootImage("boneco_purple/d2.png")
        };
        
        for (GreenfootImage image : images) {
            image.scale(image.getWidth()/3, image.getHeight()/3);
        }
        
        player2 = new Player(controls, images);
        
        addObject(player2, x, y);
    }

    // spawns an existent player 2 at a given x and y, if one is spawned replaces
    public void spawnPlayer2(int x, int y, Player player2) {
        if (this.player2 != null) despawnPlayer2();
        this.player2 = player2;
        addObject(player2, x, y);
    }

    // despawns player 2, if spawned
    public void despawnPlayer2() {
        if (player2 == null) return;
        removeObject(player2);
        player2 = null;
    }

    // freezes both players
    public void freezePlayers() {
        if (player1 != null) player1.freeze();
        if (player2 != null) player2.freeze();
    }

    // unfreezes both players
    public void unfreezePlayers() {
        if (player1 != null) player1.unfreeze();
        if (player2 != null) player2.unfreeze();
    }

    // adds default stamina bar 1, if one is active replaces
    public void addStaminaBar1() {
        if (this.staminaBar1 != null) removeStaminaBar1();
        this.staminaBar1 = new StaminaBar(player1);
        addObject(staminaBar1, 100, 50);
    }

    // adds stamina bar 1, if one is active replaces
    public void addStaminaBar1(StaminaBar staminaBar1) {
        if (this.staminaBar1 != null) removeStaminaBar1();
        this.staminaBar1 = staminaBar1;
        addObject(staminaBar1, 100, 50);
    }

    // removes stamina bar 1, if active
    public void removeStaminaBar1() {
        if (staminaBar1 == null) return;
        removeObject(staminaBar1);
        staminaBar1 = null;
    }

    // adds default stamina bar 2, if one is active replaces
    public void addStaminaBar2() {
        if (this.staminaBar2 != null) removeStaminaBar2();
        this.staminaBar2 = new StaminaBar(player2);
        addObject(staminaBar2, getWidth() - 100, 50);
    }

    // adds stamina bar 2, if one is active replaces
    public void addStaminaBar2(StaminaBar staminaBar2) {
        if (this.staminaBar2 != null) removeStaminaBar2();
        this.staminaBar2 = staminaBar2;
        addObject(staminaBar2, getWidth() - 100, 50);
    }

    // removes stamina bar 2, if active
    public void removeStaminaBar2() {
        if (staminaBar2 == null) return;
        removeObject(staminaBar2);
        staminaBar2 = null;
    }

    // executes the debug method from all game objects (for level construction purposes)
    public void debug() {
        for (GameObject object : getObjects(GameObject.class)) {
            object.debug();
        }
    }
    
    // getters and setters
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public Timer getTimer() {
        return this.timer;
    }
    
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
    
    public Player getPlayer1() {
        return this.player1;
    }
    
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
    
    public Player getPlayer2() {
        return this.player2;
    }

    public void setStaminaBar1(StaminaBar staminaBar1) {
        this.staminaBar1 = staminaBar1;
    }

    public StaminaBar getStaminaBar1() {
        return this.staminaBar1;
    }

    public void setStaminaBar2(StaminaBar staminaBar2) {
        this.staminaBar2 = staminaBar2;
    }

    public StaminaBar getStaminaBar2() {
        return this.staminaBar2;
    }
    
}
