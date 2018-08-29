package Controllers;

import Classes.UserInformation;
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

    public void changeToUIScreen(ActionEvent event) {
        // Currently this is as placeholder until we are able to fetch data from DataBase
        String email = emailTF.getText().toString();
        String password = passTF.getText().toString();
        try {
            if(databaseStatus.isLogin(email, password)) {
                UserInformation userInformation = new UserInformation(email, password);
                UserInterface user = new UserInterface();
                user.start(event, userInformation);
            } else {
                connectionLb.setText("Wrong username or password");
            }
        } catch (Exception e) {
            connectionLb.setText("Wrong username or password");
        }

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
        // Loads the registration layout
        Pane registration = (Pane) FXMLLoader.load(getClass().getResource("/resources/layout/registration_layout.fxml"));
        Scene registrationScreen = new Scene(registration);

        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(registrationScreen);
        window.show(); // changes to the new window
    }

}
