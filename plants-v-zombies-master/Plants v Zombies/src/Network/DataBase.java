package Network;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class represents a database
 *
 * @author Feij
 */
public class DataBase implements Serializable {

    //list of all users in server
    private ArrayList<User> users;


    /**
     * A constructor to create a new database
     */
    public DataBase(){
        users = new ArrayList<>();
    }


    /**
     * A method to get list of uers
     * @return list of users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * A method to add a user to database
     * @param user user to be added
     * @return true if user added successfully
     */
    public boolean addUser(User user){
        User tempUser = findUser(user.getUsername());
        if(tempUser != null)
            return false;   //user already exists!
        users.add(user);
        return true;
    }

    /**
     * A method to find a user
     * @param username username of the user to be found
     * @return the user we want
     */
    public User findUser(String username){
        for(User user : users)
            if(user.getUsername().equals(username))
                return user;
        return null;
    }

    /**
     * A method to increase score of a user
     * @param username username of the user
     * @param amount the amount of increasing
     * @return true if process was successfully done
     */
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

    /**
     * A method to decrease score of a user
     * @param username username of the user
     * @param amount the amount of decreasing
     * @return true if process was successfully done
     */
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

    /**
     * A method to check login information
     * @param username username to be checked
     * @param password password to be checked
     * @return true if information are correct
     */
    public boolean login(String username, String password){
        User user = findUser(username);
        if(user == null)
            return false;
        return user.getUsername().equals(username) && user.getPassword().equals(password);
    }

}
