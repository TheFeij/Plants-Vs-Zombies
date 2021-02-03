package Component.Zombies;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;

/**
 * This class represents a normal zombie.
 *
 * @author Feij
 * @since 2021.1.24
 */
public class NormalZombie extends Zombie implements Serializable {


    /**
     * A constructor to create a new normal zombie
     * @param locX x location of the zombie
     * @param locY x location of the zombie
     * @param state state of the game
     */
    public NormalZombie(int locX, int locY, GameState state){
        super(locX, locY, 200, 5, state);
        setCurrentImage(new ImageIcon("./Pics/zombie_normal.gif").getImage());
        getTimer().schedule(getTask(), 0, 60);
    }



    /**
     * A method to set zombie image to moving
     */
    public void setMovingImage(){
        setCurrentImage(new ImageIcon("./Pics/zombie_normal.gif").getImage());
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
     *A method to set preparations to load the zombie
     */
    public void load(){
        super.load();
        setMovingImage();
        getTimer().schedule(getTask(), 0, 60);
    }
}
