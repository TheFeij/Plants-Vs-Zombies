package Component.Bullets;
import Template.GameState;

import javax.swing.*;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents a Pea.
 *
 * @author Feij
 * @since 2021.2.1
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


    public void load(){
        super.load();
        setCurrentImage(new ImageIcon("./Pics/pea.png").getImage());
        getTimer().schedule(getTask(), 0, 8);
    }

}
