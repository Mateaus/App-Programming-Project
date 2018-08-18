package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UserInterfaceController implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void changeToMainScreen(ActionEvent event) throws Exception {
        LoginController loginController = new LoginController();
        loginController.changeToMainScreen(event);
    }
}
