import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Fighter with a special ability that uses obstacles.
 * 
 * @author Jonathan Gordon and Ben Bricken
 * @version May 2017
 */
public class Trump extends Fighter
{
    public Trump(int startX, int startY, int number) {
        super(startX, startY, number);
    }
    
    /**
     * Constructs a new Obstacle halfway between this player and the other player.
     */
    public void special() {
        super.special();
        int x = (int)getXPosition();
        int y = (int)getYPosition();
        
        if (getPlayerNumber() == 0) {
            x += (int)getWorldOfType(Arena.class).getPlayer2().getXPosition();
        } else {
            x += (int)getWorldOfType(Arena.class).getPlayer1().getXPosition();
        }
        
        x /= 2;
        Obstacle wall = new Obstacle(x, y, true);
        getWorld().addObject(wall, x, y);
    }
    
    public String getType() {
        return "trump";
    }
}
