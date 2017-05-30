import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The character selection screen. Contains buttons for fighters to select.
 * 
 * @author Jonathan Gordon and Ben Bricken
 * @version May 2017
 */
public class CharacterSelection extends World
{
    public static final Button[] PLAYER_1_BUTTONS = {new Button("ottum"), new Button("trump"), new Button("jeb")};
    public static final Button[] PLAYER_2_BUTTONS = {new Button("ottum"), new Button("trump"), new Button("jeb")};
    public static final int PLAYER_1_BUTTONS_X = 200, PLAYER_2_BUTTONS_X = 1010,
                            PLAYER_1_BUTTONS_Y = 150, PLAYER_2_BUTTONS_Y = 140,
                            PLAYER_1_BUTTONS_SPACING = 225, PLAYER_2_BUTTONS_SPACING = 210;

    public static final Button CONTINUE_BUTTON = new Button("continue");
    public static final int CONTINUE_BUTTON_X = 600, CONTINUE_BUTTON_Y = 100;

    private String player1Selection, player2Selection;

    /**
     * Constructor for the CharacterSelection screen. Adds buttons for each fighter on each side of the screen,
     * as well as a button to continue to the arena.
     */
    public CharacterSelection()
    {    
        super(Menu.WIDTH, Menu.HEIGHT, 1);

        for (int i = 0; i < PLAYER_1_BUTTONS.length; i++) {
            PLAYER_1_BUTTONS[i].setImage(PLAYER_1_BUTTONS[i].getType() + ".png");
            PLAYER_2_BUTTONS[i].setImage(PLAYER_2_BUTTONS[i].getType() + ".png");
            addObject(PLAYER_1_BUTTONS[i], PLAYER_1_BUTTONS_X, PLAYER_1_BUTTONS_Y + PLAYER_1_BUTTONS_SPACING * i);
            addObject(PLAYER_2_BUTTONS[i], PLAYER_2_BUTTONS_X, PLAYER_2_BUTTONS_Y + PLAYER_2_BUTTONS_SPACING * i);
        }

        addObject(CONTINUE_BUTTON, CONTINUE_BUTTON_X, CONTINUE_BUTTON_Y);
    }

    /**
     * Detects when a fighter button has been clicked, and sets the button's image to its 'selected' state.
     * When both players have selected a fighter, they can click the continue button to go to the arena.
     */
    public void act() {
        for (int i = 0; i < PLAYER_1_BUTTONS.length; i++) {
            if (Greenfoot.mouseClicked(PLAYER_1_BUTTONS[i])) {
                for (Button button : PLAYER_1_BUTTONS) button.setImage(button.getType() + ".png");  // sets each button to the 'unselected' state, ensuring not more than one button is selected at a time
                player1Selection = PLAYER_1_BUTTONS[i].getType();
                PLAYER_1_BUTTONS[i].setImage(PLAYER_1_BUTTONS[i].getType() + "Selected.png");
            }
            
            if (Greenfoot.mouseClicked(PLAYER_2_BUTTONS[i])) {
                for (Button button : PLAYER_2_BUTTONS) button.setImage(button.getType() + ".png");
                player2Selection = PLAYER_2_BUTTONS[i].getType();
                PLAYER_2_BUTTONS[i].setImage(PLAYER_2_BUTTONS[i].getType() + "Selected.png");
            }
        }

        if(player1Selection != null && player2Selection != null) {
            CONTINUE_BUTTON.setImage("greenContinue.png");
            
            if(Greenfoot.mouseClicked(CONTINUE_BUTTON)){
                Greenfoot.setWorld(new Arena(player1Selection, player2Selection));
            }
        }
    }

    public String getPlayer1Selection() {
        return player1Selection;
    }

    public String getPlayer2Selection() {
        return player2Selection;
    }
}
