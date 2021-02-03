package Component.Cards;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;

/**
 * A class to make wall nut card
 *
 * @author Mohammad
 */
public class Card_Wallnut extends Card implements Serializable {

    /**
     * Perform any initialization that is required
     * @param locX The card x coordinate
     * @param locY The card y coordinate
     * @param state The game state
     */
    public Card_Wallnut (int locX, int locY, GameState state){
        super (locX, locY, state) ;
        setCurrentImage(new ImageIcon("./Pics/card_wallnut.png").getImage());
        setPeriod(30000);
    }


    //setters/////////////////////////////////////////////////////////////////
    public void setActivePic(){
        setCurrentImage(new ImageIcon("./Pics/card_wallnut.png").getImage());
    }
    public void setInActivePic(){
        setCurrentImage(new ImageIcon("./Pics/card_wallnut_Inactive.png").getImage());
    }
    //////////////////////////////////////////////////////////////////////////
}
