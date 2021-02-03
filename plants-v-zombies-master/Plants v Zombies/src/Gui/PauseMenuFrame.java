package Gui;
import Network.Client;
import Template.Game;
import Template.GameState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * this class represents a pause menu frame
 *
 * @author Feij, Mohammad
 */
public class PauseMenuFrame {

    //pause menu frame
    private JFrame pauseFrame;
    //client to connect to server
    private Client client;


    /**
     * A constructor to create a new pause menu frame
     * @param client client to connect to server
     */
    public PauseMenuFrame(Client client){
        setMainFrameInfo();
        this.client = client;
        pauseFrame.setVisible(true);
    }


    /**
     * A method to set pause frame info
     */
    public void setMainFrameInfo (){
        pauseFrame = new JFrame() ;
        pauseFrame.setTitle("Main menu") ;
        pauseFrame.setSize(400, 200) ;
        pauseFrame.setLocation(600, 300) ;
        pauseFrame.setLayout(new GridLayout(3, 1));
        pauseFrame.setResizable(false);
        pauseFrame.setLocationRelativeTo(null);
        pauseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addPauseFrameComponents() ;
    }

    /**
     * A method to add pause frame components
     */
    public void addPauseFrameComponents (){
        JButton btn = new JButton() ;
        btn.setText("Resume Game") ;
        addActionListenerToResumeGameButton(btn) ;
        pauseFrame.add(btn) ;

        btn = new JButton() ;
        btn.setText("Save Game") ;
        addActionListenerToSaveGameButton(btn) ;
        pauseFrame.add(btn) ;

        btn = new JButton() ;
        btn.setText("Exit to Main Menu") ;
        addActionListenerToExitToMainMenuButton(btn) ;
        pauseFrame.add(btn) ;

    }

    /**
     * A method to set visibility of the frame
     * @param visibility visibility of the frame
     */
    public void setVisible(boolean visibility){
        pauseFrame.setVisible(visibility);
    }




    //handlers////////////////////////////////////////////////////////////////

    /**
     * A class to handle resume button
     * @param btn resume button
     */
    public void addActionListenerToResumeGameButton(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //serverResume();
                resume();
            }


            /**
             * A method to resume game from local
             */
            public void resume(){
                try{
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream("./Saves/" + client.getUsername() + "/temp.bin"));
                    GameState state = (GameState)in.readObject();
                    Game game = new Game();
                    setVisible(false);
                    game.start(state, client);
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Failed To Load!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }

            /**
             * A method to resume game from server
             */
            public void serverResume(){
                client.setWantedSave("temp");
                client.connect("Get A Save");
                GameState state = client.getSave();
                Game game = new Game();
                setVisible(false);
                game.start(state, client);
            }
        });
    }

    /**
     * A class to handle save button
     * @param btn save button
     */
    public void addActionListenerToSaveGameButton(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SavePage savePage = new SavePage(client);
            }
        });
    }

    /**
     * A class to handle exit to main menu button
     * @param btn exit to main menu button
     */
    public void addActionListenerToExitToMainMenuButton(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                MainMenuFrame mainMenuFrame = new MainMenuFrame(client);
            }
        });
    }
}
