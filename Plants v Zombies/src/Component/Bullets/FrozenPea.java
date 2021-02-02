package Component.Bullets;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;
import java.util.Timer;

/**
 * This class represents a Frozen Pea.
 *
 * @author Feij
 * @since 2021.2.1
 */
public class FrozenPea extends Bullet implements Serializable {


    /**
     * A constructor to create a new FrozenPea
     * @param locX x location of the FrozenPea in the game map
     * @param locY y location of the FrozenPea in the game map
     * @param state game state
     */
    public FrozenPea(int locX, int locY, GameState state) {
        super(locX, locY, 2, 35, state);
        setCurrentImage(new ImageIcon("./Pics/freezepea.png").getImage());
        getTimer().schedule(getTask(), 0, 8);
    }

    public void load(){
        super.load();
        setCurrentImage(new ImageIcon("./Pics/freezepea.png").getImage());
        getTimer().schedule(getTask(), 0, 8);
    }

}
