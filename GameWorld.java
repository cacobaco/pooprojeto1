import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class GameWorld extends World {
    
    private SwitchWorldAnimation joinAnimation; // when joining this level
    private SwitchWorldAnimation leaveAnimation; // when leaving this level
    private boolean blockMovement; // used in Player to block movement and interactions
    private Player player1;
    private Player player2;
    private LifeHeart[] lifePlayer1; // player 1 life
    private LifeHeart[] lifePlayer2; // player 2 life
    
    public GameWorld(int width, int height) {
        super(width, height, 1);
        setPaintOrder(SwitchWorldAnimation.class, LifeHeart.class, Player.class, GameObject.class);
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

    // blocks movement
    public void blockMovement() {
        blockMovement = true;
    }

    // unblocks movement
    public void unblockMovement() {
        blockMovement = false;
    }

    // spawns a fresh player 1 at a given x and y, if one is spawned replaces
    public void spawnPlayer1(int x, int y) {
        if (player1 != null) despawnPlayer1();

        String[] controls = {"w", "a", "s", "d", "e"};
        
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

        String[] controls = {"i", "j", "k", "l", "u"};
        
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

    // adds life bar from player 1, if he's spawned, if life is already added replaces
    public void addLifePlayer1() {
        if (lifePlayer1 != null) removeLifePlayer1();
        if (player1 == null) return;

        lifePlayer1 = new LifeHeart[player1.getLife()];
        
        for (int i = 0; i < player1.getLife(); i++) {
            LifeHeart heart = new LifeHeart();
            lifePlayer1[i] = heart;
            addObject(heart, i * 45 + 30, 30);
        }
    }

    // removes life bar from player 1, if added
    public void removeLifePlayer1() {
        if (lifePlayer1 == null) return;
        for (LifeHeart heart : lifePlayer1) {
            removeObject(heart);
        }
        lifePlayer1 = null;
    }

    // adds life bar from player 2, if he's spawned, if life is already added replaces
    public void addLifePlayer2() {
        if (lifePlayer2 != null) removeLifePlayer2();
        if (player2 == null) return;

        lifePlayer2 = new LifeHeart[player2.getLife()];
        
        for (int i = 0; i < player2.getLife(); i++) {
            LifeHeart heart = new LifeHeart();
            lifePlayer2[i] = heart;
            addObject(heart, getWidth() - i * 45 - 30, 30);
        }
    }

    // removes life bar from player 2, if added
    public void removeLifePlayer2() {
        if (lifePlayer2 == null) return;
        for (LifeHeart heart : lifePlayer2) {
            removeObject(heart);
        }
        lifePlayer2 = null;
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
    
    public void setBlockMovement(boolean blockMovement) {
        this.blockMovement = blockMovement;
    }
    
    public boolean getBlockMovement() {
        return this.blockMovement;
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
    
    public void setLifePlayer1(LifeHeart[] lifePlayer1) {
        this.lifePlayer1 = lifePlayer1;
    }
    
    public LifeHeart[] getLifePlayer1() {
        return this.lifePlayer1;
    }
    
    public void setLifePlayer2(LifeHeart[] lifePlayer2) {
        this.lifePlayer2 = lifePlayer2;
    }
    
    public LifeHeart[] getLifePlayer2() {
        return this.lifePlayer2;
    }
    
}
