package sample;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatGUIController extends Thread implements Initializable {

    @FXML
    public Button chatBtn;
    @FXML
    public TextField msgField;
    @FXML
    public TextArea msgRoom;
    @FXML
    public Pane profile;
    @FXML
    public Button addFile;
    @FXML
    public Button exitApp;
    @FXML
    public Button setPath;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    public static String ipAddress;
    public static int portnum;
    public static String username;

    private String filePath;
    private boolean status;

    public void connectSocket() {
        try {
            socket = new Socket(ipAddress, portnum);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println(username + " connected.");

            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (status) {
                String msg = reader.readLine();

                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fulmsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
                System.out.println(fulmsg);
                if (cmd.equalsIgnoreCase(Controller.username + ":")) {
                    continue;
                } else if (fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }

                msgRoom.appendText(msg + "\n");
            }
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**UNFINISHED**/
    public void logOutBtn(ActionEvent e)
    {
        //LOGOUT
        System.out.println("LOGOUT");

        writer.println(username + " disconnected.\n");
        writer.println();
        status = false;

        Stage stage = (Stage) exitApp.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    /**UNFINISHED**/
    public void addFileBtn(ActionEvent e){
        FileChooser fileChooser;

        //ADD FILE
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");

        File selectedFile;
        selectedFile = fileChooser.showOpenDialog(stage);

        String filename;
        filename = selectedFile.getName();

        writer.println(username + " sends " + filename);
        msgRoom.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        msgRoom.appendText(username + " sends " + filename +"\n");


        try
        {
            ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
            FileInputStream fis = new FileInputStream(filePath);
            byte [] bytes = new byte[360000];
            fis.read(bytes,0,(int) bytes.length);
            String extension = filename.substring(filename.lastIndexOf("."),filename.length());
            ous.writeObject(new FilePacket(bytes,extension,"\n" + username + " sent a file"));
            fis.close();
        } catch (IOException io)
        {
            io.printStackTrace();
        }
    }

    public void setDirectory(ActionEvent e)
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        filePath = directoryChooser.showDialog(stage).getAbsolutePath();

        //System.out.println("FILEPATH " + filePath);
    }

    public void send() {
        String msg = msgField.getText();
        writer.println(Controller.username + ": " + msg);
        msgRoom.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        msgRoom.appendText("Me: " + msg + "\n");
        msgField.setText("");
        if (msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //clientName.setText(Controller.username);
        connectSocket();
        status = true;
    }
}