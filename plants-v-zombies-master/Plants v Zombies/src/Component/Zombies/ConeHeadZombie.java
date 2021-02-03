package Component.Zombies;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;

/**
 * This class represents a coneHead zombie.
 *
 * @author Feij
 * @since 2021.1.24
 */
public class ConeHeadZombie extends Zombie implements Serializable {
    /**
     * A constructor to create a new coneHead zombie
     * @param locX x location of the coneHead
     * @param locY x location of the coneHead
     * @param state state of the game
     */
    public ConeHeadZombie(int locX, int locY, GameState state, String type){
        super(locX, locY, 560, 10, state);
        setCurrentImage(new ImageIcon("./Pics/coneheadzombie.gif").getImage());
        if(type.equals("Normal"))
            getTimer().schedule(getTask(), 0, 53);
        else if(type.equals("Hard")){
            setDamage(15);
            getTimer().schedule(getTask(), 0, 45);
        }
    }

    /**
     * A method to set zombie image to moving
     */
    public void setMovingImage(){
        if(getLife() < 200)
            setCurrentImage(new ImageIcon("./Pics/zombie_normal.gif").getImage());
        else
            setCurrentImage(new ImageIcon("./Pics/coneheadzombie.gif").getImage());
    }

    /**
     * A method to change zombie situation to moving in map
     * this method should be overridden
     */
    public void moving() {
        setMovingImage();
        setSpeed(2);
    }

    /**
     * A method to update zombie state
     */
    public void update(){
        setMovingImage();
        super.update();
    }

    /**
     *A method to set preparations to load the zombie
     */
    public void load(){
        super.load();
        setMovingImage();
        if(getState().getType().equals("Normal"))
            getTimer().schedule(getTask(), 0, 53);
        else if(getState().getType().equals("Hard")){
            getTimer().schedule(getTask(), 0, 45);
        }
    }

}
