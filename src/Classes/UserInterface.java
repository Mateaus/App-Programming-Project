package Classes;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserInterface {

    public void start(ActionEvent event) throws Exception {

        // Loads the userInterface layout
        Pane userInterface = (Pane) FXMLLoader.load(getClass().getResource("/resources/layout/userInterface_layout.fxml"));
        Scene userScreen = new Scene(userInterface);

        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(userScreen);
        window.show(); // changes to the new window

    }

}
