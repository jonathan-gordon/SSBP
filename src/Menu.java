import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The opening menu screen. Options to play or exit.
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

    /**
     * Constructor the Menu screen. Contains a 'play' and 'exit' button.
     */
    public Menu()
    {    
        super(WIDTH, HEIGHT, 1);
        addObject(EXIT, 400, 400);
        addObject(PLAY, 800, 400);
    }

    /**
     * Detects whether the exit or play button has been clicked. The exit button exits the program,
     * and the play button takes the players to the CharacterSelection screen.
     */
    public void act() {
        if (Greenfoot.mouseClicked(EXIT)) {
            System.exit(0);
        }

        if (Greenfoot.mouseClicked(PLAY)) {
            Greenfoot.setWorld(new CharacterSelection());
        }
    }
}
