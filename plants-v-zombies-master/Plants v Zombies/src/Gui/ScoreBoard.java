package Gui;

import Network.Client;
import Network.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class represents a scoreboard
 *
 * @author Feij, Mohammad
 */
public class ScoreBoard extends JFrame {

    //table to show top players
    private JTable scoreboard;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    //client to connect to server
    private Client client;


    /**
     * A constructor to create a new Scoreboard
     * @param client
     */
    public ScoreBoard(Client client){
        super("Top Players");
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.client = client;

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Rank");
        tableModel.addColumn("Username");
        tableModel.addColumn("Score");
        tableModel.addColumn("Normal Wins/Hard Wins");
        tableModel.addColumn("Normal Loses/Hard Loses");
        scoreboard = new JTable(tableModel);
        scoreboard.setBackground(Color.cyan);
        scoreboard.setOpaque(true);

        scrollPane = new JScrollPane(scoreboard);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        scrollPane.createVerticalScrollBar();

        client.connect("Get Users");
        ArrayList<User> users = client.getUsers();
        for(User user : users){
            String[] information = new String[5];
            information[0] = String.valueOf(users.indexOf(user) + 1);
            information[1] = user.getUsername();
            information[2] = String.valueOf(user.getScore());
            information[3] = user.getNormalWins() + "/" + user.getHardWins();
            information[4] = user.getNormalLoses() + "/" + user.getHardLoses();

            tableModel.insertRow(tableModel.getRowCount(), information);
        }

        add(scrollPane);
        setVisible(true);
    }


    public void dispose(){
        setVisible(false);
        MainMenuFrame mainMenuFrame = new MainMenuFrame(client);
    }
}
