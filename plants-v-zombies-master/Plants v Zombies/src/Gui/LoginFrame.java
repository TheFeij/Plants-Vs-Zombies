package Gui;
import Network.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * This class handles sign in and sign up
 *
 * @author Mohammad
 */
public class LoginFrame {

    //enter menu of the game
    private JFrame enterFrame ;
    //sign in frame of the game
    private JFrame signInFrame ;
    //sign up frame of the game
    private JFrame signUpFrame ;

    /**
     * A constructor to create a new loginFrame
     */
    public LoginFrame(){
        enterFrame = new JFrame() ;
        signInFrame = new JFrame() ;
        signUpFrame = new JFrame() ;
        setBackgroundImage();
        setEnterFrameInfo() ;
        setSignInFrameInfo();
        setSignUpFrameInfo();

        enterFrame.setVisible(true);
    }

    //enter frame/////////////////////////////////////
    /**
     * A method Set enter frame info
     */
    public void setEnterFrameInfo (){
        enterFrame.setTitle("Enter") ;
        enterFrame.setSize(400, 200) ;
        enterFrame.setLocation(600, 300) ;
        enterFrame.setLocationRelativeTo(null);
        //enterFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        enterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addEnterFrameComponents() ;
    }
    /**
     * A method to Add enter frame components
     */
    public void addEnterFrameComponents (){
        JButton btn = new JButton() ;
        btn.setSize(200, 40);
        btn.setLocation(0, 50);
        btn.setText("sign up") ;
        addActionListenerToEnterComponents(btn) ;
        enterFrame.add(btn) ;

        btn = new JButton() ;
        btn.setSize(200, 40);
        btn.setLocation(200, 50);
        btn.setText("sign in") ;
        addActionListenerToEnterComponents(btn) ;
        enterFrame.add(btn) ;
    }
    //////////////////////////////////////////////////

    //sign in frame//////////////////////////////////
    /**
     * A method to Set sign in frame info
     */
    public void setSignInFrameInfo (){
        signInFrame.setTitle("Sign in") ;
        signInFrame.setSize(400, 200) ;
        signInFrame.setLocation(600, 300) ;
        signInFrame.setLocationRelativeTo(null);
        signInFrame.setLayout(null) ;
        addSignInFrameComponents() ;
    }
    /**
     * A method to Add sign in frame components
     */
    public void addSignInFrameComponents (){
        JLabel usernameLabel = new JLabel("  Username :") ;
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setFont(new Font(usernameLabel.getFont().getName(), Font.PLAIN, 25));
        usernameLabel.setSize(150, 40) ;
        usernameLabel.setLocation(0, 10) ;

        JTextField usernameTextField = new JTextField() ;
        usernameTextField.setSize(150, 40) ;
        usernameTextField.setLocation(200, 10);

        JLabel passwordLabel = new JLabel("  Password :") ;
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setFont(new Font(passwordLabel.getFont().getName(), Font.PLAIN, 25));
        passwordLabel.setSize(150, 40) ;
        passwordLabel.setLocation(0, 60) ;

        JTextField passwordTextField = new JTextField() ;
        passwordTextField.setSize(150, 40) ;
        passwordTextField.setLocation(200, 60) ;

        JButton OKButton = new JButton("OK") ;
        OKButton.setSize(100, 30) ;
        OKButton.setLocation(150, 110) ;
        addActionListenerToSignInOKButton(OKButton, usernameTextField, passwordTextField) ;


        signInFrame.add(usernameLabel) ;
        signInFrame.add(usernameTextField) ;
        signInFrame.add(passwordLabel) ;
        signInFrame.add(passwordTextField) ;
        signInFrame.add(OKButton) ;
    }
    /////////////////////////////////////////////////

    //sign up frame//////////////////////////////////
    /**
     * A method to Set sign up frame info
     */
    public void setSignUpFrameInfo (){
        signUpFrame.setTitle("Sign up") ;
        signUpFrame.setSize(400, 200);
        signUpFrame.setLocation(600, 300) ;
        signInFrame.setLocationRelativeTo(null);
        signUpFrame.setLayout(null) ;
        addSignUpFrameComponents() ;
    }
    /**
     * A method to Add sign up frame components
     */
    public void addSignUpFrameComponents (){
        JLabel usernameLabel = new JLabel("  Username :") ;
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setFont(new Font(usernameLabel.getFont().getName(), Font.PLAIN, 25));
        usernameLabel.setSize(150, 40) ;
        usernameLabel.setLocation(0, 10) ;

        JTextField usernameTextField = new JTextField() ;
        usernameTextField.setSize(150, 40) ;
        usernameTextField.setLocation(200, 10);
//        addKeyListenerToSignUpJTextField(usernameTextField) ;

        JLabel passwordLabel = new JLabel("  Password :") ;
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setFont(new Font(passwordLabel.getFont().getName(), Font.PLAIN, 25));
        passwordLabel.setSize(150, 40) ;
        passwordLabel.setLocation(0, 60) ;

        JTextField passwordTextField = new JTextField() ;
        passwordTextField.setSize(150, 40) ;
        passwordTextField.setLocation(200, 60) ;
//        addKeyListenerToSignUpJTextField(passwordTextField) ;

        JButton OKButton = new JButton("OK") ;
        OKButton.setSize(100, 30) ;
        OKButton.setLocation(150, 110) ;
        addActionListenerToSignUpOKButton(OKButton, usernameTextField, passwordTextField) ;


        signUpFrame.add(usernameLabel) ;
        signUpFrame.add(usernameTextField) ;
        signUpFrame.add(passwordLabel) ;
        signUpFrame.add(passwordTextField) ;
        signUpFrame.add(OKButton) ;
    }
    /////////////////////////////////////////////////

    /**
     * A method to set background image of the frames
     */
    public void setBackgroundImage(){
        try {
            enterFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("./Pics/background.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        enterFrame.pack();
        try {
            signInFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("./Pics/background.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        signInFrame.pack();
        try {
            signUpFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("./Pics/background.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        signUpFrame.pack();
    }





    //handlers/////////////////////////////////////////////////////////

    /**
     * Action listener for enter frame components
     * @param btn button to be handled
     */
    public void addActionListenerToEnterComponents (JButton btn){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource() ;
                switch (button.getText()){
                    case "sign in" :
                        signInFrame.setVisible(true) ;
                        break ;
                    case "sign up" :
                        signUpFrame.setVisible(true) ;
                        break ;
                    default :
                        break ;
                }

            }
        });
    }

    /**
     * Action listener for sign in ok button
     * @param btn button to be handled
     * @param usernameTextField username text field
     * @param passwordTextField password text field
     */
    public void addActionListenerToSignInOKButton (JButton btn, JTextField usernameTextField, JTextField passwordTextField){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                if(username.equals("") || password.equals(""))
                    return;
                if(username.startsWith(" ") || password.startsWith(" "))
                    return;
                enterFrame.setVisible(false);
                signInFrame.setVisible(false);
                signUpFrame.setVisible(false);
                Client client = new Client();
                client.setPassword(password);
                client.setUsername(username);
                client.connect("Login");
            }
        });
    }

    /**
     * Action listener for sign up ok button
     * @param btn button to be handled
     * @param usernameTextField username text field
     * @param passwordTextField password text field
     */
    public void addActionListenerToSignUpOKButton (JButton btn, JTextField usernameTextField, JTextField passwordTextField){
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                if(username.equals("") || password.equals(""))
                    return;
                if(username.startsWith(" ") || password.startsWith(" "))
                    return;
                enterFrame.setVisible(false);
                signInFrame.setVisible(false);
                signUpFrame.setVisible(false);
                Client client = new Client();
                client.setPassword(password);
                client.setUsername(username);
                client.connect("Sign Up");

            }
        });
    }
}
