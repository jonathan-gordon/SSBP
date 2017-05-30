import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Fighter with a special ability that uses Projectiles.
 * 
 * @author Jonathan Gordon and Ben Bricken
 * @version May 2017
 */
public class Ottum extends Fighter
{
    public Ottum(int startX, int startY, int playerNumber) {
        super(startX, startY, playerNumber);
    }

    /**
     * Constructs a new Projectile and launches it.
     */
    public void special() {
        super.special();
        int x = (int)getXPosition();
        int y = (int)getYPosition();
        Projectile ottumSpecial = new Projectile(x, y, getPlayerNumber(), getDirection());
        getWorld().addObject(ottumSpecial, x, y);
    }

    public String getType() {
        return "ottum";
    }
}
