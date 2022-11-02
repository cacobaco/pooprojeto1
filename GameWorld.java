import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class GameWorld extends World {
    
    private SwitchWorldAnimation joinAnimation; // when joining this level
    private SwitchWorldAnimation leaveAnimation; // when leaving this level
    private Player player1;
    private Player player2;
    
    public GameWorld(int width, int height) {
        super(width, height, 1);
        setPaintOrder(SwitchWorldAnimation.class, Player.class, GameObject.class);
    }

    // adds default join animation, if one is active replaces
    public void addJoinAnimation() {
        if (this.joinAnimation != null) removeJoinAnimation();
        this.joinAnimation = new SwitchWorldAnimation(getWidth(), getHeight(), false);
        addObject(joinAnimation, getWidth()/2, getHeight()/2);
    }

    // adds join animation, if one is active replaces
    public void addJoinAnimation(SwitchWorldAnimation joinAnimation) {
        if (this.joinAnimation != null) removeJoinAnimation();
        this.joinAnimation = joinAnimation;
        addObject(joinAnimation, getWidth()/2, getHeight()/2);
    }

    // removes join animation, if active
    public void removeJoinAnimation() {
        if (joinAnimation == null) return;
        removeObject(joinAnimation);
        joinAnimation = null;
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

    // spawns a fresh player 1 at a given x and y, if one is spawned replaces
    public void spawnPlayer1(int x, int y) {
        if (player1 != null) despawnPlayer1();

        String[] controls = {"w", "a", "s", "d", "e", "f"};
        
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

        String[] controls = {"i", "j", "k", "l", "u", "h"};
        
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

    // executes the debug method from all game objects (for level construction purposes)
    public void debug() {
        for (GameObject object : getObjects(GameObject.class)) {
            object.debug();
        }
    }
    
    // getters and setters
    public void setJoinAnimation(SwitchWorldAnimation joinAnimation) {
        this.joinAnimation = joinAnimation;
    }
    
    public SwitchWorldAnimation getJoinAnimation() {
        return this.joinAnimation;
    }
    
    public void setLeaveAnimation(SwitchWorldAnimation leaveAnimation) {
        this.leaveAnimation = leaveAnimation;
    }
    
    public SwitchWorldAnimation getLeaveAnimation() {
        return this.leaveAnimation;
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
    
}
