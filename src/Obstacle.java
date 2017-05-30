import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A representation of a wall, used in Trump's special ability.
 * 
 * @author Jonathan Gordon and Ben Bricken
 * @version May 2017
 */
public class Obstacle extends GameEntity
{
    public static final int WIDTH = 62;
    public static final int HEIGHT = 225;
    private int timer = 150;
    private boolean special;
    
    public Obstacle(int startX, int startY, boolean special) {
        super(startX, startY, WIDTH, HEIGHT);
        this.special = special;
    }
    
    /**
     * Decrements the Obstacle's timer by 1 each tick. After 150 ticks, it removes itself from the world.
     */
    public void act() 
    {
        if (timer == 0 && special) getWorld().removeObject(this);
        timer--;
    }   
    
    public String getType() {
        return "Obstacle";
    }
}
