import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button with a changeable image.
 * 
 * @author Jonathan Gordon and Ben Bricken
 * @version May 2017
 */
public class Button extends Actor
{
    private String type;
    
    public Button(String type) {
        this.type = type;
        setImage(type + ".png");
    }
    
    public String getType() {
        return type;
    }
}
