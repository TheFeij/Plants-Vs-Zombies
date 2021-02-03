package Component.Plants;
import Template.GameState;
import Component.Component;
import java.io.Serializable;


/**
 * This class represents a plant
 *
 * @author Feij
 */
public abstract class Plant extends Component implements Serializable {

    /**
     * A constructor to create a new plant
     * @param locX x location of the plant
     * @param locY y location of the plant
     * @param life life of the plant
     * @param state state of the game
     */
    public Plant(int locX, int locY, int life, GameState state){
        super(locX, locY, 0, life, 0, state);
    }

}
