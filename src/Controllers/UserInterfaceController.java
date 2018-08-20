package Controllers;

import Classes.BlackBoardApp;
import Database.DatabaseStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class UserInterfaceController implements Initializable {

    public DatabaseStatus databaseStatus = new DatabaseStatus();

    private String email, password;
    @FXML private Label studentLB;


    public void initialize(URL location, ResourceBundle resources) {
        // We start reading from here

    }

    public void blackBoardApp(ActionEvent event) throws Exception {
        BlackBoardApp blackBoardApp = new BlackBoardApp();
        blackBoardApp.start(event);
    }

    public void logOut(ActionEvent event) throws Exception {
        LoginController loginController = new LoginController();
        loginController.changeToMainScreen(event);
    }

    public void setStudentName(String student) {
        this.studentLB.setText(student);
    }
}
