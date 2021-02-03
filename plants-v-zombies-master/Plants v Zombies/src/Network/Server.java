package Network;

import Template.GameState;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * This class represents a server
 *
 * @author Feij
 */
public class Server {

    public static void main(String[] args) {
        DataBase dataBase = loadDataBase();
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket welcomingSocket = new ServerSocket(7660)) {
            System.out.print("Server started.\nWaiting for a client ... \n");
            while (true) {
                Socket connectionSocket = welcomingSocket.accept();
                System.out.println("client accepted!");
                pool.execute(new ClientHandler(connectionSocket, dataBase));
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    /**
     * A method to load database
     * @return database
     */
    public static DataBase loadDataBase(){
        Path path = Paths.get("./Server/database.bin");
        if(Files.exists(path)){
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("./Server/database.bin"))){
                return (DataBase)in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            return new DataBase();
        }
        return null;
    }

}




/**
 * A class to handle clients
 */
class ClientHandler implements Runnable {

    private Socket connectionSocket;
    private DataBase dataBase;

    /**
     * A constructor to create a new client handler
     * @param connectionSocket connection socket
     * @param dataBase database
     */
    public ClientHandler(Socket connectionSocket, DataBase dataBase) {
        this.connectionSocket = connectionSocket;
        this.dataBase = dataBase;
    }


    @Override
    public void run() {
        try {
            OutputStream out = connectionSocket.getOutputStream();
            InputStream in = connectionSocket.getInputStream();
            byte[] buffer = new byte[8196];
            int read = in.read(buffer);
            String command = new String(buffer, 0, read);

            if(command.equals("Login") || command.equals("Sign Up")){
                out.write("Receiving Username".getBytes());
                read = in.read(buffer);
                String username = new String(buffer, 0, read);
                out.write("Receiving Password".getBytes());
                read = in.read(buffer);
                String password = new String(buffer, 0, read);

                if(command.equals("Login")){
                    if(dataBase.login(username, password))
                        out.write("Successful".getBytes());
                    else
                        out.write("Failed".getBytes());
                }
                else{
                    User user = new User(username, password);
                    if (dataBase.addUser(user))
                        out.write("Successful".getBytes());
                    else
                        out.write("Failed".getBytes());
                }

            }
            else if(command.equals("Give Score")){
                out.write("Receiving Username".getBytes());
                read = in.read(buffer);
                String username = new String(buffer, 0, read);
                out.write("Receiving Score".getBytes());
                read = in.read(buffer);
                int score = Integer.parseInt(new String(buffer, 0, read));
                if(score < 0)
                    dataBase.decreaseScore(username, score);
                else
                    dataBase.increaseScore(username, score);
            }
            else if(command.equals("Get Users")) {
                ArrayList<User> users = dataBase.getUsers();
                ObjectOutputStream outputStream = new ObjectOutputStream(out);
                outputStream.writeObject(users);
            }
            ///database saving option
            else if(command.equals("Get Save List")){
                out.write("Receiving Username".getBytes());
                read = in.read(buffer);
                String username = new String(buffer, 0, read);
                User tempUser = dataBase.findUser(username);
                ArrayList<String> saves = tempUser.getSavesList();
                ObjectOutputStream outputStream = new ObjectOutputStream(out);
                outputStream.writeObject(saves);
            }
            ///database saving option
            else if(command.equals("Get A Save")){
                out.write("Receiving Username".getBytes());
                read = in.read(buffer);
                String username = new String(buffer, 0, read);
                out.write("Receiving Save Name".getBytes());
                read = in.read(buffer);
                String saveName = new String(buffer, 0, read);

                User tempUser = dataBase.findUser(username);
                GameState save = tempUser.getSave(saveName);
                ObjectOutputStream outputStream = new ObjectOutputStream(out);
                outputStream.writeObject(save);
            }
            else if(command.equals("Save")){
                out.write("Receiving Username".getBytes());
                read = in.read(buffer);
                String username = new String(buffer, 0, read);
                out.write("Receiving Save Name".getBytes());
                read = in.read(buffer);
                String saveName = new String(buffer, 0, read);
                out.write("Receiving Save".getBytes());

                try(ObjectInputStream inputStream = new ObjectInputStream(in)){
                    GameState save = (GameState)inputStream.readObject();
                    User user = dataBase.findUser(username);
                    user.addSave(saveName, save);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            saveDataBase(dataBase);
        } catch (IOException ignored) {
        } finally {
            closeConnection();
        }
    }

    /**
     * A method to save database
     * @param dataBase database to be saved
     */
    public static void saveDataBase(DataBase dataBase){
        if(!Files.exists(Paths.get("./Server")))
            new File("./Server").mkdirs();

        Path path = Paths.get("./Server/database.bin");
        if(Files.exists(path)){
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./Server/database.bin"))){
            out.writeObject(dataBase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method to close connection
     */
    private void closeConnection(){
        try {
            System.out.println("Done!");
            connectionSocket.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
