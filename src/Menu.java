import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The opening menu screen. Options to play, exit, or view controls.
 * 
 * @author Jonathan Gordon and Ben Bricken
 * @version May 2017
 */
public class Menu extends World
{
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 700;
    public static final Button PLAY = new Button("play");
    public static final Button EXIT = new Button("exit");
    public static final Button CONTROLS = new Button("controls");

    /**
     * Constructor the Menu screen. Contains a 'play' and 'exit' button.
     */
    public Menu()
    {    
        super(WIDTH, HEIGHT, 1);
        addObject(EXIT, 400, 400);
        addObject(PLAY, 800, 400);
        addObject(CONTROLS, 600, 500);
    }

    /**
     * Detects whether the exit, play, or control button has been clicked. The exit button exits the program,
     * the play button takes the players to the CharacterSelection screen, and the control button takes the
     * players to the ControlScreen.
     */
    public void act() {
        if (Greenfoot.mouseClicked(EXIT)) {
            System.exit(0);
        }

        if (Greenfoot.mouseClicked(PLAY)) {
            Greenfoot.setWorld(new CharacterSelection());
        }
        
        if (Greenfoot.mouseClicked(CONTROLS)) {
            Greenfoot.setWorld(new ControlScreen());
        }
    }
}
