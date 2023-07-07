package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;

public class ErrorController implements Serializable {

    private Stage window;

    public void errorButton(ActionEvent e) throws IOException
    {
        Parent returnParent = FXMLLoader.load(getClass().getResource("/sample/Landing.fxml"));
        Scene returnScene = new Scene(returnParent);

        window = (Stage)((Node)e.getSource()).getScene().getWindow();

        window.setScene(returnScene);
        window.show();
    }
}
