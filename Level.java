import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Level extends GameWorld {

    public Level(int width, int height, int player1X, int player1Y, int player2X, int player2Y) {
        super(width, height);
        setPaintOrder(SwitchWorldAnimation.class, LifeHeart.class, Player.class, GameObject.class);
        spawnPlayer1(player1X, player1Y);
        spawnPlayer2(player2X, player2Y);
        addLifePlayer1();
        addLifePlayer2();
    }
    
    public Level(int width, int height, int player1X, int player1Y, int player2X, int player2Y, Player player1, Player player2) {
        super(width, height);
        setPaintOrder(SwitchWorldAnimation.class, LifeHeart.class, Player.class, GameObject.class);
        spawnPlayer1(player1X, player1Y, player1);
        spawnPlayer2(player2X, player2Y, player2);
        addLifePlayer1();
        addLifePlayer2();
    }
    
}
