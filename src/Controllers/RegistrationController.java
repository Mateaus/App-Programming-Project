package Controllers;

import Database.DatabaseStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javax.rmi.CORBA.Util;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    public DatabaseStatus databaseStatus = new DatabaseStatus();

    @FXML
    private TextField nameTF, emailTF, passTF;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void registerUser(ActionEvent event) {
        String name = nameTF.getText().toString();
        String email = emailTF.getText().toString();
        String password = passTF.getText().toString();
        try {
            if(nameTF != null && !name.isEmpty() && emailTF != null && !email.isEmpty() && passTF != null && !password.isEmpty()) {
                databaseStatus.registerAccount(name, email, password);
                LoginController loginController = new LoginController();
                loginController.changeToMainScreen(event);
                System.out.printf("Account registered\n");
            } else {
                System.out.printf("Fill the blanks\n");
            }
        } catch (Exception e) {
            System.out.printf("Account not created\n" + e);
        }


    }

    public void changeToMainScreen(ActionEvent event) throws Exception {
        LoginController loginController = new LoginController();
        loginController.changeToMainScreen(event);
    }
}
