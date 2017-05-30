import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A representation of a projectile, used in Mr. Ottum's special ability. Boom Java!
 * 
 * @author Jonathan Gordon and Ben Bricken
 * @version May 2017
 */
public class Projectile extends GameEntity
{
    public static final int WIDTH = 25;
    public static final int HEIGHT = 33;
    public static final int DAMAGE = 10;
    public static final int DEFAULT_SPEED = 15;

    private static int speed = DEFAULT_SPEED;  // static so Jeb's special ability can slow all Projectiles, not just one Projectile object
    private int owner;  // when checking collision in the Fighter class, specifying the owner prevents the producer of the Projectile from being injured
    private String direction;

    public Projectile(int startX, int startY, int owner, String direction) {
        super(startX, startY, WIDTH, HEIGHT);
        this.owner = owner;
        this.direction = direction;
    }
    
    /**
     * Moves the projectile left or right based on the current player direction and global Projectile speed.
     * Also checks for collision with Obstacles.
     */
    public void act() 
    {
        int dx = speed;
        if (getDirection().equals("left")) dx *= -1;

        setX(getXPosition() + dx);
        setLocation((int)getXPosition(), (int)getYPosition());
        
        if (getOneIntersectingObject(Obstacle.class) != null) getWorld().removeObject(this);
    }
    
    /**
     * Sets the speed for all Projectiles to the specified speed. Static so Jeb's special ability can slow
     * all Projectiles, not just this one Projectile object.
     */
    public static void setSpeed(int newSpeed) {
        speed = newSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getOwner() {
        return owner;
    }

    public String getDirection() {
        return direction;
    }

    public String getType() {
        return "Projectile";
    }
}
