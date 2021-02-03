package Component.Bullets;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;

/**
 * This class represents a Pea.
 *
 * @author Feij
 * @since 2021.1.24
 */
public class Pea extends Bullet implements Serializable {

    /**
     * A constructor to create a new Pea
     * @param locX x location of the Pea in the game map
     * @param locY y location of the Pea in the game map
     * @param state game state
     */
    public Pea(int locX, int locY, GameState state) {
        super(locX, locY, 2, 30, state);
        setCurrentImage(new ImageIcon("./Pics/pea.png").getImage());
        getTimer().schedule(getTask(), 0, 8);
    }


    /**
     * A method to set preparations for loading the component
     */
    public void load(){
        super.load();
        setCurrentImage(new ImageIcon("./Pics/pea.png").getImage());
        getTimer().schedule(getTask(), 0, 8);
    }

}
