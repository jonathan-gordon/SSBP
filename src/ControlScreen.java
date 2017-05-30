import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The control screen. Lists player controls and allows user to return to opening menu.
 * 
 * @author Jonathan Gordon and Ben Bricken
 * @version May 2017
 */
public class ControlScreen extends World
{
    public static final Button RETURN = new Button("return");
    
    /**
     * Constructor for the ControlScreen. Has a button to return to Menu screen.
     */
    public ControlScreen()
    {
        super(Menu.WIDTH, Menu.HEIGHT, 1);
        addObject(RETURN, 600, 500);
    }
    
    /**
     * Detects whether the return button has been clicked. If so, the simulation returns to the Menu screen.
     */
    public void act() {
        if (Greenfoot.mouseClicked(RETURN)) {
            Greenfoot.setWorld(new Menu());
        }
    }
}
