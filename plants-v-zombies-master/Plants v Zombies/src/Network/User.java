package Network;

import Template.Game;
import Template.GameState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a user in the game
 */
public class User implements Serializable {

    private String username;
    private String password;
    private int normalLoses;
    private int normalWins;
    private int hardLoses;
    private int hardWins;
    private int score;
    private HashMap<String, GameState> saves;


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
    //////////////////////////////////////////////////////////////////////


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
    public void addSave(String saveName, GameState save){
        saves.put(saveName, save);
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
}
