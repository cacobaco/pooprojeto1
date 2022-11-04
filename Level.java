import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Level extends GameWorld {
    
    private Elevator elevator;
    
    public Level(int width, int height) {
        super(width, height);
    }

    // spawns player 1
    public abstract void spawnPlayer1(boolean fromElevator);

    // spawns a given player 1
    public abstract void spawnPlayer1(boolean fromElevator, Player player1);

    // spawns player 2
    public abstract void spawnPlayer2(boolean fromElevator);

    // spawns a given  player 2
    public abstract void spawnPlayer2(boolean fromElevator, Player player2);
    
    // getters and setters
    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }
    
    public Elevator getElevator() {
        return this.elevator;
    }
    
}
