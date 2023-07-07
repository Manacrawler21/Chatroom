package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Controller implements Serializable{
    @FXML
    private TextField ipTxtBox;
    @FXML
    private TextField portTxtBox;
    @FXML
    private TextField userTxtBox;


    public static String username;
    public static int portnum;
    public static String ipAddress;

    public static ArrayList<ChatClient> loggedInUser = new ArrayList<>();
    public static ArrayList<ChatClient> users = new ArrayList<ChatClient>();

    public void startButton(ActionEvent e) throws IOException{
        System.out.println("Start");

        /* Set portnum, IP address, and username */
        ipAddress = ipTxtBox.getText();
        portnum = Integer.parseInt(portTxtBox.getText());
        username = userTxtBox.getText();


        ChatGUIController.ipAddress = ipAddress;
        ChatGUIController.portnum = portnum;
        ChatGUIController.username = username;


        try {
            Stage stage = (Stage) userTxtBox.getScene().getWindow();
            Parent root = FXMLLoader.load(this.getClass().getResource("/sample/ChatGUI.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle(username + "");
            stage.setOnCloseRequest(event -> {
                System.exit(0);
            });
            stage.setResizable(false);
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        /**
         FXMLLoader loader = new FXMLLoader();

         //Call Chat Interface
         loader.setLocation(getClass().getResource("/sample/ChatGUI.fxml"));

         Parent chatParent = loader.load();
         Scene chatScene = new Scene(chatParent);

         Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
         window.setScene(chatScene);
         window.show();
         */

        /**
        try{
            //Call Chat Interface
            loader.setLocation(getClass().getResource("/sample/ChatGUI.fxml"));

            ChatGUIController chatGUI = loader.getController();
            chatGUI.setData(ipAddress, portnum, username);

            Parent chatParent = loader.load();
            Scene chatScene = new Scene(chatParent);

            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
            window.setScene(chatScene);
            window.show();
        }
        catch(UnknownHostException exception)
        {
            System.out.println("ServerNotFound");
            // Call error screen
            loader.setLocation(getClass().getResource("/sample/Error.fxml"));

            Parent chatParent = loader.load();
            Scene chatScene = new Scene(chatParent);

            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
            window.setScene(chatScene);
            window.show();
        }
        catch(IOException exception)
        {
            System.out.println("IO ERROR");
            // Call error screen
            loader.setLocation(getClass().getResource("/sample/Error.fxml"));

            Parent chatParent = loader.load();
            Scene chatScene = new Scene(chatParent);

            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
            window.setScene(chatScene);
            window.show();
        }
         **/
    }
}
