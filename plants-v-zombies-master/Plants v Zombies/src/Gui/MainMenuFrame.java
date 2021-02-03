package Gui;
import Network.Client;
import Template.Game;
import Template.GameState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents a main menu frame
 *
 * @author Mohammad, Feij
 */
public class MainMenuFrame {

    //main menu frame
    private JFrame mainFrame;
    //client to connect server
    private Client client;


    /**
     * A constructor to create a new main menu frame
     * @param client client to connect to server
     */
    public MainMenuFrame(Client client){
        setMainFrameInfo();
        this.client = client;
        mainFrame.setVisible(true);
    }



    /**
     * A method to set main menu info
     */
    public void setMainFrameInfo (){
        mainFrame = new JFrame() ;
        mainFrame.setTitle("Main menu") ;
        mainFrame.setSize(400, 400) ;
        mainFrame.setLocation(600, 200) ;
        mainFrame.setLayout(new GridLayout(5, 1));
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMainFrameComponents() ;
    }

    /**
     * A method to add main menu components
     */
    public void addMainFrameComponents (){
        JButton btn = new JButton() ;
        btn.setText("New Game") ;
        addActionListenerToNewGameButton(btn) ;
        mainFrame.add(btn) ;

        btn = new JButton() ;
        btn.setText("Load Game") ;
        addActionListenerToLoadGameButton(btn) ;
        mainFrame.add(btn) ;

        btn = new JButton() ;
        btn.setText("Ranking") ;
        addActionListenerToRankingButton(btn) ;
        mainFrame.add(btn) ;

        btn = new JButton() ;
        btn.setText("Settings") ;
        addActionListenerToSettingsButton(btn) ;
        mainFrame.add(btn) ;

        btn = new JButton() ;
        btn.setText("Quit Game") ;
        addActionListenerToQuitGameButton(btn) ;
        mainFrame.add(btn) ;

    }

    /**
     * A method to set visibility of the frame
     * @param visibility visibility of the frame
     */
    public void setVisible(boolean visibility){
        mainFrame.setVisible(visibility);
    }





    //handlers////////////////////////////////////////////////////////////

    /**
     * A class to handle new game button
     * @param btn new game button
     */
    public void addActionListenerToNewGameButton(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameState state = new GameState(client.getDifficulty(), client.getUsername(), client.getSound());
                Game game = new Game();
                setVisible(false);
                game.start(state,client);
            }
        });
    }

    /**
     * A class to handle new load game button
     * @param btn load game button
     */
    public void addActionListenerToLoadGameButton(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                LoadPage loadPage = new LoadPage(client);
            }
        });
    }

    /**
     * A class to handle ranking button
     * @param btn ranking button
     */
    public void addActionListenerToRankingButton(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.connect("Get Users");
                setVisible(false);
                ScoreBoard scoreBoard = new ScoreBoard(client);
            }
        });
    }

    /**
     * A class to handle settings button
     * @param btn settings button
     */
    public void addActionListenerToSettingsButton(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SettingsFrame settingsFrame = new SettingsFrame(client);
            }
        });
    }

    /**
     * A class to handle quit game button
     * @param btn quit game button
     */
    public void addActionListenerToQuitGameButton(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

}
