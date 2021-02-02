package Component.Cards;

import Template.GameState;

import javax.swing.*;
import java.io.Serializable;

public class Card_Repeater extends Card implements Serializable {

    /**
     * Perform any initialization that is required
     * @param locX The card x coordinate
     * @param locY The card y coordinate
     * @param state The game state
     */
    public Card_Repeater (int locX, int locY, GameState state){
        super (locX, locY, state) ;
        setCurrentImage(new ImageIcon("./Pics/card_repeater.jpg").getImage());
        setPeriod(7500);
//        if (getState().getType().equals("Hard")){
//            setPeriod(30000);
//        }
    }

    public void setActivePic(){
        setCurrentImage(new ImageIcon("./Pics/card_repeater.jpg").getImage());
    }
    public void setInActivePic(){
        setCurrentImage(new ImageIcon("./Pics/card_repeater_inactive.jfif").getImage());
    }

}
