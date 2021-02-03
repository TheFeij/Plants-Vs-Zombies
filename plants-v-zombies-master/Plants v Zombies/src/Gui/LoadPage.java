package Gui;

import Network.Client;
import Template.Game;
import Template.GameState;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * This class is a load page
 *
 * @author Feij, Mohammad;
 */
public class LoadPage extends JFrame {

    //a table to show saves
    private JTable saveList;
    //table model
    private DefaultTableModel tableModel;
    private JButton loadButton, backButton;
    private JScrollPane scrollPane;
    private Client client;

    /**
     * A constructor to create a new load page
     */
    public LoadPage(Client client){
        super();
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.client = client;

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Save Name");
        saveList = new JTable(tableModel);
        saveList.setBackground(Color.cyan);
        saveList.setOpaque(true);

        Handler handler = new Handler();

        scrollPane = new JScrollPane(saveList);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        scrollPane.createVerticalScrollBar();

        loadButton = new JButton("Load");
        loadButton.setFont(new Font("serif", Font.BOLD, 25));
        loadButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        loadButton.setPreferredSize(new Dimension(loadButton.getWidth() + 50, loadButton.getHeight() + 50));
        loadButton.setBackground(Color.ORANGE);
        loadButton.setOpaque(true);
        loadButton.addActionListener(handler);

        backButton = new JButton("Back");
        backButton.setFont(new Font("serif", Font.BOLD, 25));
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        backButton.setBackground(Color.ORANGE);
        backButton.setOpaque(true);
        backButton.addActionListener(handler);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1,2));
        southPanel.add(loadButton);
        southPanel.add(backButton);
        southPanel.setBackground(Color.ORANGE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        mainPanel.setBackground(Color.ORANGE);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        update();
        //serverSavesUpdate();

        setContentPane(mainPanel);
        setVisible(true);
    }

    /**
     * A method to update saves list
     */
    private void update(){
        int numberOfRows  = tableModel.getRowCount();
        for(int i = 0 ; i < numberOfRows ; i++){
            tableModel.removeRow(0);
        }
        try{
            Path path = Paths.get("./Saves/" + client.getUsername());
            if(Files.isDirectory(path)){
                DirectoryStream<Path> directory = Files.newDirectoryStream(path);
                for(Path pth : directory){
                    String[] name = new String[1];
                    name[0]  = String.valueOf(pth.getFileName());
                    tableModel.insertRow(tableModel.getRowCount(), name);
                }
            }
        }catch (IOException ignored){

        }

    }

    /**
     * A method to update server save list
     */
    public void serverSavesUpdate(){
        int numberOfRows  = tableModel.getRowCount();
        for(int i = 0 ; i < numberOfRows ; i++){
            tableModel.removeRow(0);
        }

        client.connect("Get Save List");
        ArrayList<String> saves = client.getSaveList();
        for(String saveName : saves){
            String[] name = new String[1];
            name[0]  = saveName;
            tableModel.insertRow(tableModel.getRowCount(), name);
        }
    }




    /**
     * This class handle events of the frame
     */
    private class Handler implements ActionListener {


        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == loadButton){
                load();
                //dataBaseLoad();
            }
            else if(e.getSource() == backButton){
                setVisible(false);
                MainMenuFrame mainMenuFrame = new MainMenuFrame(client);
            }

        }

        /**
         * A method to load a local saved game
         */
        public void load(){
            new File("/Saves/" + client.getUsername()).mkdirs();
            try{
                String save = (String) tableModel.getValueAt(saveList.getSelectedRow(), 0);
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("./Saves/"+ client.getUsername() +
                        "/" + save));
                GameState state = (GameState)in.readObject();
                Game game = new Game();
                setVisible(false);
                game.start(state,client);
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Failed To Load!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            update();
        }

        /**
         * A method to load a game from server
         */
        public void dataBaseLoad(){
            String saveName = (String) tableModel.getValueAt(saveList.getSelectedRow(), 0);
            client.setWantedSave(saveName);
            client.connect("Get A Save");
            GameState state = client.getSave();
            Game game = new Game();
            setVisible(false);
            game.start(state,client);
        }
    }
}
