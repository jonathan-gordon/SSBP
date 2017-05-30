import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The arena in which the players fight. Responsible for constructing the fighters and displaying their health.
 * 
 * @author Jonathan Gordon and Ben Bricken
 * @version May 2017
 */
public class Arena extends World
{
    public static final int PLAYER_1_START_X = 300;
    public static final int PLAYER_1_START_Y = 400;
    public static final int PLAYER_2_START_X = 900;
    public static final int PLAYER_2_START_Y = 400;
    public static final int GROUND_HEIGHT = 600;
    public static final int NUM_HEALTH_BLOCKS = 50;
    public static final int HEALTH_BLOCK_SPACING = 8;
    public static final int HEALTH_BAR_HEIGHT = 50;
    public static final int PLAYER_1_BAR_X = 100;
    public static final int PLAYER_2_BAR_X = 700;
    public static final int LEFT_WALL_X = 62;
    public static final int RIGHT_WALL_X = 1138;
    public static final int WALL_Y = 500;
    public static final Obstacle LEFT_WALL = new Obstacle(LEFT_WALL_X, WALL_Y, false);
    public static final Obstacle RIGHT_WALL = new Obstacle(RIGHT_WALL_X, WALL_Y, false);

    private Fighter player1, player2;
    private ArrayList<HealthBarBlock> player1Bar, player2Bar;
    private boolean switchedSides = false;

    /**
     * Constructor for the arena. Takes the player selections in the CharacterSelection screen as parameters
     * and uses them to construct the appropriate fighter. Also sets health bars to full.
     * 
     * @param fighter1, fighter2    The character selections made in the CharacterSelection screen.
     */
    public Arena(String fighter1, String fighter2)
    {    
        super(1200, 700, 1, true);
        setHealthBars();

        if (fighter1.equals("trump")) {
            player1 = new Trump(PLAYER_1_START_X, PLAYER_1_START_Y, 0);
        } else if (fighter1.equals("ottum")) {
            player1 = new Ottum(PLAYER_1_START_X, PLAYER_1_START_Y, 0);
        } else if (fighter1.equals("jeb")) {
            player1 = new Jeb(PLAYER_1_START_X, PLAYER_1_START_Y, 0);
        }

        if (fighter2.equals("trump")) {
            player2 = new Trump(PLAYER_2_START_X, PLAYER_2_START_Y, 1);
        } else if (fighter2.equals("ottum")) {
            player2 = new Ottum(PLAYER_2_START_X, PLAYER_2_START_Y, 1);
        } else if (fighter2.equals("jeb")) {
            player2 = new Jeb(PLAYER_2_START_X, PLAYER_2_START_Y, 1);
        }

        addObject(player1, PLAYER_1_START_X, PLAYER_1_START_Y);
        addObject(player2, PLAYER_2_START_X, PLAYER_2_START_Y);
        addObject(LEFT_WALL, LEFT_WALL_X, WALL_Y);
        addObject(RIGHT_WALL, RIGHT_WALL_X, WALL_Y);
    }

    /**
     * Checks each player's health, as well as if they have switched sides.
     * If the players have switched, their fighters change direction to face each other.
     */
    public void act() {
        checkHealth();

        if (player1.getX() > player2.getX() && !switchedSides) {
            player1.setDirection("left");
            player2.setDirection("right");
            switchedSides = true;
        } else if (player1.getX() < player2.getX() && switchedSides) {
            player1.setDirection("right");
            player2.setDirection("left");
            switchedSides = false;
        }
    }

    /**
     * Constructs the health bars for both players. The health bar is comprised of 50 health 'blocks' in
     * an array, which are removed when the player's health goes below the block's specified health number.
     */
    public void setHealthBars() {
        player1Bar = new ArrayList<HealthBarBlock>();
        player2Bar = new ArrayList<HealthBarBlock>();

        for (int i = 0; i < NUM_HEALTH_BLOCKS; i++) {
            player1Bar.add(new HealthBarBlock(i * 2));  // i * 2 because there are 50 blocks, but a player's health is out of 100
            player2Bar.add(new HealthBarBlock(i * 2));

            addObject(player1Bar.get(i), PLAYER_1_BAR_X + i * HEALTH_BLOCK_SPACING, HEALTH_BAR_HEIGHT);
            addObject(player2Bar.get(i), PLAYER_2_BAR_X + i * HEALTH_BLOCK_SPACING, HEALTH_BAR_HEIGHT);
        }
    }

    /**
     * Checks if a player's health is less than the healthID of one of his HealthBarBlocks. If so, the block
     * is removed from the health bar. Also checks if either player's health is <= 0, in which case the game ends.
     */
    public void checkHealth() {
        for (int i = player1Bar.size() - 1; i >= 0; i--) {
            if (player1.getHealth() <= player1Bar.get(i).getHealthID()) {
                removeObject(player1Bar.get(i));
                player1Bar.remove(i);
            }
        }

        for (int i = player2Bar.size() - 1; i >= 0; i--) {
            if (player2.getHealth() <= player2Bar.get(i).getHealthID()) {
                removeObject(player2Bar.get(i));
                player2Bar.remove(i);
            }
        }

        if (player1.getHealth() <= 0 && player2.getHealth() <= 0) {
            Greenfoot.stop();
            showText("It's a draw!", 600, 100);
        } else if (player1.getHealth() <= 0) {
            endGame(2);
            Greenfoot.playSound(player2.getType() + "Win.wav");
        } else if (player2.getHealth() <= 0) {
            endGame(1);
            Greenfoot.playSound(player1.getType() + "Win.wav");
        }
    }
    
    /**
     * Ends the game and displays the winner.
     * 
     * @param winner    the number of the player who won.
     */
    public void endGame(int winner) {
        Greenfoot.stop();
        showText("Player " + winner + " wins!", 600, 100);
    }

    public Fighter getPlayer1() {
        return player1;
    }

    public Fighter getPlayer2() {
        return player2;
    }
}
