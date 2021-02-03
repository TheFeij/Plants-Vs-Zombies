package Network;

import Template.GameState;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a user in the game
 *
 * @author Feij
 */
public class User implements Serializable {

    //username of the user
    private String username;
    //password of the user
    private String password;
    //number of normal loses
    private int normalLoses;
    //number of normal wins
    private int normalWins;
    //number of hard loses
    private int hardLoses;
    //number of hard wins
    private int hardWins;
    //total score of the user
    private int score;
    //server save repository
    private HashMap<String, GameState> saves;


    /**
     * A constructor to create a new user
     * @param username username of the user
     * @param password password of the user
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.score = 0;
        normalLoses = 0;
        normalWins = 0;
        hardLoses = 0;
        hardWins = 0;
        saves = new HashMap<>();
    }


    ////setters///////////////////////////////////////////////////////////
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setHardLoses(int hardLoses) {
        this.hardLoses = hardLoses;
    }
    public void setHardWins(int hardWins) {
        this.hardWins = hardWins;
    }
    public void setNormalLoses(int normalLoses) {
        this.normalLoses = normalLoses;
    }
    public void setNormalWins(int normalWins) {
        this.normalWins = normalWins;
    }
    //////////////////////////////////////////////////////////////////////

    ////getters///////////////////////////////////////////////////////////
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public int getScore() {
        return score;
    }
    public int getHardLoses() {
        return hardLoses;
    }
    public int getHardWins() {
        return hardWins;
    }
    public int getNormalLoses() {
        return normalLoses;
    }
    public int getNormalWins() {
        return normalWins;
    }
    public GameState getSave(String saveName){
        return saves.get(saveName);
    }
    public ArrayList<String> getSavesList(){
        ArrayList<String> saveList = new ArrayList<>();
        for(Map.Entry e : saves.entrySet()){
            saveList.add((String) e.getKey());
        }
        return saveList;
    }
    //////////////////////////////////////////////////////////////////////


    /**
     * A method to increase users score by a certain amount
     * @param amount amount of score to be added
     */
    public void increaseScore(int amount){
        if(amount == -1)
            normalLoses++;
        else if(amount == 3)
            normalWins++;
        else if(amount == -3)
            hardLoses++;
        else  if(amount == 10)
            hardWins++;
        score += amount;
    }

    /**
     * A method to add a save to users saves
     * @param saveName name of the save
     * @param save save to be added
     */
    public void addSave(String saveName, GameState save){
        saves.put(saveName, save);
    }

}
