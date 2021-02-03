package Gui;
import Network.Client;
import javax.swing.*;
import java.awt.*;

/**
 * This class is a setting frame
 *
 * @author Mohammad, Feij
 */
public class SettingsFrame extends JFrame{

    private JComboBox<String> soundComboBox, difficultyComboBox;
    //client to connect to server
    private Client client;

    /**
     * A constructor to create new Settings menu
     *
     * @param client client to connect to server
     */
    public SettingsFrame(Client client){
        super();
        setSettingsFrameInfo();
        this.client = client;
        setVisible(true);
    }



    /**
     * A method Set settings frame info
     */
    public void setSettingsFrameInfo(){
        setTitle("Settings") ;
        setSize(400, 200) ;
        setLocation(600, 300) ;
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 2));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addSettingsFrameComponents() ;
    }

    /**
     * A method to Add Settings frame components
     */
    public void addSettingsFrameComponents(){
        JLabel label = new JLabel("Sound :");
        add(label);

        soundComboBox = new JComboBox<>();
        soundComboBox.addItem("Mute");
        soundComboBox.addItem("UnMute");
        add(soundComboBox);

        label = new JLabel("difficulty :");
        add(label);

        difficultyComboBox = new JComboBox<>();
        difficultyComboBox.addItem("Hard");
        difficultyComboBox.addItem("Normal");
        add(difficultyComboBox);


    }

    public void dispose(){
        String difficulty = (String) difficultyComboBox.getSelectedItem();
        String sound = (String) soundComboBox.getSelectedItem();
        client.setDifficulty(difficulty);
        client.setSound(sound);
        setVisible(false);
        MainMenuFrame mainMenuFrame = new MainMenuFrame(client);
        super.dispose();
    }

}
