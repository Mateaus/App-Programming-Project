package Controllers;

import Classes.Registration;
import Classes.UserInterface;
import Database.DatabaseStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    public DatabaseStatus databaseStatus = new DatabaseStatus();

    @FXML private Button loginBtn;
    @FXML private TextField emailTF, passTF;
    @FXML private Label connectionLb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Any actions done to buttons,etc will be passed through here first.
        // Example: Button1 changes when it's pressed. It will read this function
        // to fetch the action.
        if(databaseStatus.isDBConnected()) {
            connectionLb.setText("connected");
        } else {
            connectionLb.setText("not connected");
        }
    }

    public void changeToUIScreen(ActionEvent event) throws Exception {
        // Currently this is as placeholder until we are able to fetch data from DataBase
        UserInterface user = new UserInterface();
        user.start(event);

    }

    /* We will be calling this function every time we want to return to the main menu/log screen */
    public void changeToMainScreen(ActionEvent event) throws Exception {
        // Loads the main/log layout
        Pane main = (Pane) FXMLLoader.load(getClass().getResource("/resources/layout/login_layout.fxml"));
        Scene mainScreen = new Scene(main);

        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScreen);
        window.show(); // changes to the new window
    }

    public void changeToRegisterScreen(ActionEvent event) throws Exception {
        Registration registration = new Registration();
        registration.start(event);
    }

}
