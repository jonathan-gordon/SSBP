import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An entity within the game. Includes fighters, obstacles, and projectiles.
 * 
 * @author Jonathan Gordon and Ben Bricken
 * @version May 2017
 */
public abstract class GameEntity extends Actor
{
    private double x, y;  // x and y are stored as doubles so velocity can be added in values less than 1
    private int width, height;

    /**
     * Constructor for GameEntities. All GameEntities must have an x, y, width, and height.
     * 
     * @param x, y, width, height   the starting values for GameEntities
     */
    public GameEntity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getXPosition() {
        return x;
    }

    public double getYPosition() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getType() {
        return "GameEntity";
    }
}
