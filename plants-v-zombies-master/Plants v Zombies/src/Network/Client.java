package Network;

import Template.GameState;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    private String username;
    private String password;
    private int score;
    private String sound;
    private String difficulty;
    private ArrayList<User> users;
    private ArrayList<String> saveList;
    private String wantedSave;
    private GameState save;

    public Client(){
        score = 0;
        sound = "UnMute";
        difficulty = "Normal";
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public void setSave(GameState save) {
        this.save = save;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setWantedSave(String wantedSave) {
        this.wantedSave = wantedSave;
    }

    public GameState getSave() {
        return save;
    }

    public ArrayList<String> getSaveList() {
        return saveList;
    }

    public String getSound() {
        return sound;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void connect(String command) {

        try (Socket client = new Socket("127.0.0.1", 7660)) {
            System.out.println("Connected to server.");
            OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();
            byte[] buffer = new byte[8196];
            out.write(command.getBytes());

            if(command.equals("Login") || command.equals("Sign Up")){
                in.read(buffer);
                out.write(username.getBytes());
                in.read(buffer);
                out.write(password.getBytes());

                int read = in.read(buffer);
                String message = new String(buffer, 0, read);
                if(message.equals("Successful")){
                    MainMenuFrame mainMenuFrame = new MainMenuFrame(this);
                }
                else {
                    LoginFrame loginFrame = new LoginFrame();
                    JOptionPane.showMessageDialog(null, "Failed!", "Warning!", JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(command.equals("Get Users")){
                try(ObjectInputStream inputStream = new ObjectInputStream(in)) {
                    users = (ArrayList<User>)inputStream.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if(command.equals("Give Score")){
                in.read(buffer);
                out.write(username.getBytes());
                in.read(buffer);
                out.write(score);
            }
            ///database save option
            else if((command.equals("Get Save List"))) {
                in.read(buffer);
                out.write(username.getBytes());
                try(ObjectInputStream inputStream = new ObjectInputStream(in)) {
                    saveList = (ArrayList<String>)inputStream.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if(command.equals("Get A Save")){
                in.read(buffer);
                out.write(username.getBytes());
                in.read(buffer);
                out.write(wantedSave.getBytes());
                try(ObjectInputStream inputStream = new ObjectInputStream(in)) {
                    save = (GameState) inputStream.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if(command.equals("Save")){
                in.read(buffer);
                out.write(username.getBytes());
                in.read(buffer);
                out.write(wantedSave.getBytes());
                in.read(buffer);
                try(ObjectOutputStream outputStream = new ObjectOutputStream(out)) {
                    outputStream.writeObject(save);
                }
            }

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}