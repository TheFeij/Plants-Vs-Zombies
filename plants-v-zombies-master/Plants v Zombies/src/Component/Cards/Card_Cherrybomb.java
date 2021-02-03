package Component.Cards;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;

/**
 * This class represents a cherry bomb card
 *
 * @author Mohammad
 */
public class Card_Cherrybomb extends Card implements Serializable {

    /**
     * Perform any initialization that is required
     * @param locX The card x coordinate
     * @param locY The card y coordinate
     * @param state The game state
     */
    public Card_Cherrybomb (int locX, int locY, GameState state){
        super (locX, locY, state) ;
        setCurrentImage(new ImageIcon("./Pics/card_cherrybomb.png").getImage());
        setPeriod(30000);
        if (getState().getType().equals("Hard")){
            setPeriod(45000);
        }
    }


    //setters/////////////////////////////////////////////////////////////////
    public void setActivePic(){
        setCurrentImage(new ImageIcon("./Pics/card_cherrybomb.png").getImage());
    }
    public void setInActivePic(){
        setCurrentImage(new ImageIcon("./Pics/card_cherrybomb_inactive.png").getImage());
    }
    //////////////////////////////////////////////////////////////////////////
}
