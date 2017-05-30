import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A component of the player health bar. Symbolizes a 2 HP fragment of the player's health.
 * 
 * @author Jonathan Gordon and Ben Bricken
 * @version May 2017
 */
public class HealthBarBlock extends Actor
{
    private int healthID;
    
    public HealthBarBlock(int healthID) {
        this.healthID = healthID;
    }

    public int getHealthID() {
        return healthID;
    }
}
