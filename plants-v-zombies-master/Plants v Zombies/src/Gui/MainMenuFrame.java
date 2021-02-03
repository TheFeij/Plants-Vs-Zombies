package Gui;
import Network.Client;
import Network.User;
import Template.Game;
import Template.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame {
    private JFrame mainFrame;
    private Client client;


    public MainMenuFrame(Client client){
        setMainFrameInfo();

        this.client = client;
        mainFrame.setVisible(true);
    }


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

    public void setVisible(boolean visibility){
        mainFrame.setVisible(visibility);
    }









    // Action listeners

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

    public void addActionListenerToLoadGameButton(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                LoadPage loadPage = new LoadPage(client);
            }
        });
    }

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

    public void addActionListenerToSettingsButton(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SettingsFrame settingsFrame = new SettingsFrame(client);
            }
        });
    }

    public void addActionListenerToQuitGameButton(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }




}
