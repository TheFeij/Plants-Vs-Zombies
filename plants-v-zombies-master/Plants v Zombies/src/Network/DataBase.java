package Network;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class represents a database
 */
public class DataBase implements Serializable {

    private ArrayList<User> users;


    public DataBase(){
        users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean addUser(User user){
        User tempUser = findUser(user.getUsername());
        if(tempUser != null)
            return false;   //user already exists!
        users.add(user);
        return true;
    }

    public User findUser(String username){
        for(User user : users)
            if(user.getUsername().equals(username))
                return user;
        return null;
    }

    public boolean increaseScore(String username, int amount){
        User user = findUser(username);
        if(user == null)
            return false;
        user.increaseScore(amount);
        while (true){
            int index = users.indexOf(user);
            users.remove(user);
            if((user.getScore() > users.get(index - 1).getScore()) && index != 0){
                users.add(index - 1, user);
            }
            else{
                users.add(index, user);
                break;
            }
        }
        return true;
    }

    public boolean decreaseScore(String username, int amount){
        User user = findUser(username);
        if(user == null)
            return false;
        user.increaseScore(amount);
        while (true){
            int index = users.indexOf(user);
            if((user.getScore() < users.get(index + 1).getScore()) && users.size() >= index + 1){
                users.remove(user);
                users.add(index + 1, user);
            }
            else{
                break;
            }
        }
        return true;
    }

    public boolean login(String username, String password){
        User user = findUser(username);
        if(user == null)
            return false;
        return user.getUsername().equals(username) && user.getPassword().equals(password);
    }

}
