package Controllers;

import Classes.UserInformation;
import Classes.UserInterface;
import Database.DatabaseStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserInterfaceController implements Initializable {

    public DatabaseStatus databaseStatus = new DatabaseStatus();

    private String email, password;
    @FXML private Label studentLB;


    public void initialize(URL location, ResourceBundle resources) {
        // We start reading from here

    }

    public void changeToMainScreen(ActionEvent event) throws Exception {
        LoginController loginController = new LoginController();
        loginController.changeToMainScreen(event);
    }

    public void setStudentName(String student) {
        this.studentLB.setText(student);
    }
}
