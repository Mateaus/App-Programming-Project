package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    @FXML
    private TextField username, password;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void registerUser(ActionEvent event) throws Exception{
        /*TODO: Implements a sort of database to store user information.
          We will utilize the variables created above (username, password).

          UserInformation userInformation = new UserInformation(username.getText().toString, password.getText().toString);
          Database database = new Database(userInformation);


        */


    }

    public void changeToMainScreen(ActionEvent event) throws Exception {
        LoginController loginController = new LoginController();
        loginController.changeToMainScreen(event);
    }
}
