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

public class PauseMenuFrame {
    private JFrame pauseFrame;
    private Client client;

    public PauseMenuFrame(Client client){
        setMainFrameInfo();

        this.client = client;

        pauseFrame.setVisible(true);
    }


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


    public void setVisible(boolean visibility){
        pauseFrame.setVisible(visibility);
    }

    // Action listeners


    public void addActionListenerToResumeGameButton(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //serverResume();
                resume();
            }


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

    public void addActionListenerToSaveGameButton(JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SavePage savePage = new SavePage(client);
            }
        });
    }

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
