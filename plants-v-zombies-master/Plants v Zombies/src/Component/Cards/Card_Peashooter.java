package Component.Cards;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;

/**
 * A class to make pea shooter card
 *
 * @author Mohammad
 */
public class Card_Peashooter extends Card implements Serializable {

    /**
     * Perform any initialization that is required
     * @param locX The card x coordinate
     * @param locY The card y coordinate
     * @param state The game state
     */
    public Card_Peashooter (int locX, int locY, GameState state){
        super (locX, locY, state) ;
        setCurrentImage(new ImageIcon("./Pics/card_peashooter.png").getImage());
        setPeriod(7500);
    }

    //setters/////////////////////////////////////////////////////////////////
    public void setActivePic(){
        setCurrentImage(new ImageIcon("./Pics/card_peashooter.png").getImage());
    }
    public void setInActivePic(){
        setCurrentImage(new ImageIcon("./Pics/card_peashooter_inactive.png").getImage());
    }
    //////////////////////////////////////////////////////////////////////////
}
