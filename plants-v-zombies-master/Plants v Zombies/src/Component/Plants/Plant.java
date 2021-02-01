package Component.Plants;
import Template.GameState;
import Component.Component;
import javax.swing.*;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents a plant
 *
 * @author Feij
 */
public abstract class Plant extends Component implements Serializable {

    public Plant(int locX, int locY, int life, GameState state){
        super(locX, locY, 0, life, 0, state);

    }

}
