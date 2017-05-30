import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Fighter with a special ability that puts all actors into slow motion.
 * 
 * @author Jonathan Gordon and Ben Bricken
 * @version May 2017
 */
public class Jeb extends Fighter
{
    public static final int SLOW_MO_DURATION = 150;
    
    private int slowMoTimer = 0;
    private boolean specialActivated = false;
    
    public Jeb(int startX, int startY, int number) {
        super(startX, startY, number);
    }
    
    /**
     * Continually decrements Jeb's slowMoTimer. Once it reaches zero, Projectiles and Fighters return to their
     * normal speed.
     */
    public void act() 
    {
        super.act();
        if (specialActivated) slowMoTimer--;
        
        if (specialActivated && slowMoTimer == 0) {
            Projectile.setSpeed(Projectile.DEFAULT_SPEED);
            Fighter.setMovementSpeed(Fighter.DEFAULT_MOVEMENT_SPEED);
            specialActivated = false;
        }
    }    
    
    /**
     * Sets the speed of Projectiles and Fighters to 1/3 of their normal speed, for 150 ticks of the simulation.
     */
    public void special() {
        super.special();
        slowMoTimer = SLOW_MO_DURATION;
        specialActivated = true;
        Projectile.setSpeed(Projectile.DEFAULT_SPEED / 3);
        Fighter.setMovementSpeed(Fighter.DEFAULT_MOVEMENT_SPEED / 3);
    }
    
    public String getType() {
        return "jeb";
    }
}
