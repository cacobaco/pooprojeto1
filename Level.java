import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Level extends GameWorld {
    
    private Elevator elevator;
    
    public Level(int width, int height, int player1X, int player1Y, int player2X, int player2Y) {
        super(width, height);
        setPaintOrder(SwitchWorldAnimation.class, Player.class, GameObject.class);
        spawnPlayer1(player1X, player1Y);
        spawnPlayer2(player2X, player2Y);
    }
    
    public Level(int width, int height, int player1X, int player1Y, int player2X, int player2Y, Player player1, Player player2) {
        super(width, height);
        setPaintOrder(SwitchWorldAnimation.class, Player.class, GameObject.class);
        spawnPlayer1(player1X, player1Y, player1);
        spawnPlayer2(player2X, player2Y, player2);
    }
    
    // getters and setters
    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }
    
    public Elevator getElevator() {
        return this.elevator;
    }
    
}
