import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The fighters of the game. Capable of side-to-side movement, jumping, attacking, blocking, and special abilites,
 * with animations for each of these actions.
 * 
 * @author Jonathan Gordon and Ben Bricken
 * @version May 2017
 */
public abstract class Fighter extends GameEntity
{
    public static final int WIDTH = 50;
    public static final int HEIGHT = 100;
    public static final int JUMP_SPEED = -15;
    public static final double GRAVITY = 0.5;
    public static final int DEFAULT_MOVEMENT_SPEED = 5;
    public static final int ATTACK_COOLDOWN = 30;
    public static final int ATTACK_DURATION = 40;
    public static final int BLOCK_COOLDOWN = 10;
    public static final int BLOCK_DURATION = 50;
    public static final int SPECIAL_COOLDOWN = 200;
    public static final int STUN_TIME = 35;
    public static final int DAMAGE = 5;
    public static final double BLOCKING_DAMAGE_MULTIPLIER = 0.2;
    public static final int ATTACK_BOUNCE_VELOCITY = 5;
    public static final int PROJECTILE_BOUNCE_VELOCITY = 10;
    public static final double BOUNCE_DECELERATION = 0.9;
    public static final String[] JUMP_CONTROLS = {"w", "up"};
    public static final String[] RIGHT_CONTROLS = {"d", "right"};
    public static final String[] LEFT_CONTROLS = {"a", "left"};
    public static final String[] ATTACK_CONTROLS = {"v", "/"};
    public static final String[] BLOCK_CONTROLS = {"s", "down"};
    public static final String[] SPECIAL_CONTROLS = {"b", "."};

    private int health = 100, stunCounter = 0, attackTimer = 0, blockTimer = 0, specialTimer = 0;
    private double xVelocity = 0, yVelocity = 0;
    private boolean attacking = false, blocking = false, stunned = false;
    private static int movementSpeed = DEFAULT_MOVEMENT_SPEED; // static so Jeb's special ability can slow all Fighters, not just one Fighter object

    private int playerNumber;
    private String direction;

    public Fighter(int startX, int startY, int playerNumber) {
        super(startX, startY, WIDTH, HEIGHT);
        this.playerNumber = playerNumber;
        direction = (playerNumber == 0) ? "right" : "left"; // sets initial directions for player1 to right, player2 to left
        setImage(getType() + "Model_" + direction + ".png");
    }

    /**
     * Updates the Fighter's position based on its xVelocity and yVelocity. Also detects if any keys are pressed
     * or any GameEntities collide, and decrements the player's stun, attack, block, and special timers.
     * This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (stunned) {
            xVelocity *= BOUNCE_DECELERATION;
        } else {
            xVelocity = 0;
        }

        if(isOnGround()){
            yVelocity = 0;
        } else {
            yVelocity += GRAVITY;
        }

        checkControls();
        checkCollision();
        decrementCounters();

        setX(getXPosition() + xVelocity);
        setY(getYPosition() + yVelocity);

        setLocation((int)getXPosition(), (int)getYPosition());
    }

    /**
     * Detects if the player is pressing any controls for movement, jumping, attacking, blocking, or special attacking.
     * If they are, and specific conditions are met, the appropriate action is performed.
     */
    public void checkControls() {
        if(Greenfoot.isKeyDown(JUMP_CONTROLS[playerNumber]) && isOnGround() && !stunned && !blocking) {  // can't jump if airborne, stunned, or blocking
            jump();
        }

        if (!(Greenfoot.isKeyDown(RIGHT_CONTROLS[playerNumber]) && Greenfoot.isKeyDown(LEFT_CONTROLS[playerNumber])) && !stunned && !blocking) {  // can't move if stunned or blocking, or pressing both left and right simultaneously
            if (Greenfoot.isKeyDown(RIGHT_CONTROLS[playerNumber])) {
                moveRight();
            } else if (Greenfoot.isKeyDown(LEFT_CONTROLS[playerNumber])){
                moveLeft();
            }
        }

        if (Greenfoot.isKeyDown(ATTACK_CONTROLS[playerNumber]) && !stunned && !blocking) attack();
        if (Greenfoot.isKeyDown(BLOCK_CONTROLS[playerNumber]) && !stunned) block();
        if (Greenfoot.isKeyDown(SPECIAL_CONTROLS[playerNumber]) && !stunned && !blocking && specialTimer == 0) special();
    }

    /**
     * Detects if the Fighter has collided with another GameEntity, including Projectiles, Obstacles, and other Fighters.
     * In both a Projectile and Fighter collision (assuming the other fighter is attacking), this fighter will take damage,
     * bounce backwards, and be stunned for about 0.5 seconds. Additionally, if there is an Obstacle present in the arena,
     * Fighters will not be able to move past the Obstacle's edges.
     */
    public void checkCollision() {
        for (Fighter f : getIntersectingObjects(Fighter.class)) {
            if (f.isAttacking()) {
                f.setAttacking(false);  // prevents multiple hits in one animation without ending the animation image

                if (isBlocking()) {
                    health -= BLOCKING_DAMAGE_MULTIPLIER * DAMAGE;
                } else {
                    health -= DAMAGE;
                    bounceBack(ATTACK_BOUNCE_VELOCITY);
                }
            }
        }

        for (Projectile p : getIntersectingObjects(Projectile.class)) {        
            if(p.getOwner() != playerNumber) {
                if (isBlocking()) {
                    health -= BLOCKING_DAMAGE_MULTIPLIER * Projectile.DAMAGE;
                } else {
                    health -= Projectile.DAMAGE;
                    bounceBack(PROJECTILE_BOUNCE_VELOCITY);
                }

                getWorld().removeObject(p);
            }
        }

        Obstacle wall = (Obstacle)getOneIntersectingObject(Obstacle.class);
        
        if (wall != null) {
            if (wall.getXPosition() > getXPosition() && xVelocity > 0) {
                xVelocity = 0;
            } else if (wall.getXPosition() < getXPosition() && xVelocity < 0) {
                xVelocity = 0;
            }
        }
    }

    /**
     * Method for attacking. Triggers attack animation and sets attacking boolean to true. Does nothing if attack
     * is still cooling down.
     */
    public void attack() {
        if (attackTimer == 0) {
            attackTimer = ATTACK_DURATION;
            attacking = true;
            setImage(getType() + "Attacking_" + direction + ".png");
            
            Fighter otherPlayer = (playerNumber == 0) ? getWorldOfType(Arena.class).getPlayer2() : getWorldOfType(Arena.class).getPlayer1();
            if (Greenfoot.getRandomNumber(100) < 50 && otherPlayer.getHealth() > DAMAGE) Greenfoot.playSound(getType() + "Attack.wav");  // only make attack sound 50% of time. Also, don't make attack sound if you are about to win, so it doesn't override your victory sound
        }
    }

    /**
     * Aspects of the special ability that all Fighters share. Each subclass of Fighter will override and further
     * implement this method.
     */
    public void special() {
        setSpecialTimer(SPECIAL_COOLDOWN);
        Greenfoot.playSound(getType() + "Special.wav");
    }

    /**
     * Method for blocking. Triggers block animation and sets blocking boolean to true. Does nothing if attack
     * and block is still cooling down.
     */
    public void block() {
        if (blockTimer == 0 && attackTimer == 0) {  // uses attackTimer as a condition for blocking to punish bad attacking
            blockTimer = BLOCK_DURATION;
            blocking = true;
            setImage(getType() + "Blocking_" + direction + ".png");
            Greenfoot.playSound(getType() + "Block.wav");
        }
    }

    /**
     * Decrements the Fighter's stun, attack, block, and special timers.
     */
    public void decrementCounters() {
        if (stunCounter > 0) {
            if (isOnGround()) stunCounter--;
        } else {
            stunned = false;
        }

        if (attackTimer > 0) {
            attackTimer--;

            if (attackTimer == ATTACK_COOLDOWN) { // ends attack animation, but player must still wait for the cooldown to end to attack again
                attacking = false;
                setImage(getType() + "Model_" + direction + ".png");
            }
        }

        if (blockTimer > 0) {
            blockTimer--;

            if (blockTimer == BLOCK_COOLDOWN) { // ends block animation, but player must still wait for the cooldown to end to block again
                blocking = false;
                setImage(getType() + "Model_" + direction + ".png");
            }
        }

        if (specialTimer > 0) {
            specialTimer--;
        }
    }

    /**
     * Bounces the Fighter backward with an int veloctiy, and stuns them for ~0.5 seconds. Will only be triggered if 
     * the Fighter is hit by another Fighter or a Projectile.
     * 
     * @param velocity  the velocity the fighter will gain
     */
    public void bounceBack(int velocity) {
        stunCounter = STUN_TIME; // prevents movement for ~0.5 seconds if fighter has been hit
        stunned = true;
        
        if (direction.equals("right")) velocity *= -1;
        
        xVelocity = velocity;
    }

    public void moveRight() {
        xVelocity = movementSpeed;
    }

    public void moveLeft() {
        xVelocity = -movementSpeed;
    }

    public void jump() {
        yVelocity = JUMP_SPEED;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public boolean isStunned() {
        return stunned;
    }

    public boolean isOnGround() {
        return getYPosition() + HEIGHT >= Arena.GROUND_HEIGHT;
    }

    /**
     * Sets the movementSpeed for all Fighters to the specified speed. Static so Jeb's special ability can slow
     * all Fighters, not just this one Fighter object.
     */
    public static void setMovementSpeed(int speed) {
        movementSpeed = speed;
    }

    /**
     * Sets direction to "right" or "left" and changes Fighter image accordingly
     */
    public void setDirection(String direction) {
        this.direction = direction;
        setImage(getType() + "Model_" + direction + ".png");
    }

    public int getHealth(){return health;}

    public int getMovementSpeed(){return movementSpeed;}

    public int getPlayerNumber(){return playerNumber;}

    public int getstunCounter(){return stunCounter;}

    public int getAttackTimer(){return attackTimer;}

    public int getSpecialTimer(){return specialTimer;}

    public double getXVelocity(){return xVelocity;}

    public double getYVelocity(){return yVelocity;}

    public String getDirection(){return direction;}

    public String getType(){return "Fighter";}

    public void setHealth(int health) {this.health = health;}

    public void setAttacking(boolean attacking) {this.attacking = attacking;}

    public void setSpecialTimer(int timer){specialTimer = timer;}
}
