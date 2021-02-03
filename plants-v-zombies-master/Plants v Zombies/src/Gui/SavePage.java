package Gui;

import Network.Client;
import Template.GameState;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

/**
 * This class is a save page
 *
 * @author Feij, Mohammad
 */
public class SavePage extends JFrame {

    //list of all saves
    private JTable saveList;
    private DefaultTableModel tableModel;
    private JButton saveButton, backButton;
    private JTextField saveName;
    private JScrollPane scrollPane;
    //client to connect to server
    private Client client;


    /**
     * A constructor to create a new save page
     *
     * @param client client to connect to server
     */
    public SavePage(Client client){
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

        scrollPane = new JScrollPane(saveList);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        scrollPane.createVerticalScrollBar();

        Handler handler = new Handler();

        saveButton = new JButton("Save");
        saveButton.setFont(new Font("serif", Font.BOLD, 25));
        saveButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        saveButton.setPreferredSize(new Dimension(saveButton.getWidth() + 50, saveButton.getHeight() + 50));
        saveButton.setBackground(Color.ORANGE);
        saveButton.setOpaque(true);
        saveButton.addActionListener(handler);

        backButton = new JButton("Back");
        backButton.setFont(new Font("serif", Font.BOLD, 25));
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        backButton.setBackground(Color.ORANGE);
        backButton.setOpaque(true);
        backButton.addActionListener(handler);

        saveName = new JTextField("  Enter Save Name...");
        saveName.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1,3));
        southPanel.add(saveButton);
        southPanel.add(saveName);
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
     * A method to update save list locally
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
     * A method to update save list from server
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
     * A class to handle events of the frame
     */
    private class Handler implements ActionListener{


        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(checkName(saveName.getText()))
                return;
            new File("./Saves/" + client.getUsername()).mkdirs();
            if(e.getSource() == saveButton){
                save();
                //serverSave();
            }
            else if(e.getSource() == backButton){
                setVisible(false);
                PauseMenuFrame pauseMenuFrame = new PauseMenuFrame(client);
            }

        }

        /**
         * A method to save game locally
         */
        public void save(){
            try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./Saves/" + client.getUsername() +
                    "/" + saveName.getText() + ".bin"));
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("./Saves/" +client.getUsername() +
                        "/" + "temp.bin")))
            {
                GameState tempState = (GameState)in.readObject();
                out.writeObject(tempState);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            update();
        }

        /**
         * A method to save game to server
         */
        public void serverSave(){
            client.setWantedSave("temp");
            client.connect("Get A Save");
            GameState tempState = client.getSave();
            client.setWantedSave(saveName.getText());
            client.setSave(tempState);
            client.connect("Save");
            serverSavesUpdate();
        }

        /**
         * A method to check save name
         * @param name name of the save
         * @return true if name is not valid
         */
        public boolean checkName(String name){
            if(name == null)
                return true;
            return name.contains("/") || name.contains("\\") || name.contains("?")
                    || name.contains("<") || name.contains(">") || name.contains("\"")
                    || name.contains("*") || name.contains("|") || name.contains("|");
        }

    }

}
