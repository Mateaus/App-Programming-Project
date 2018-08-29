package Controllers;

import Database.DatabaseStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
        // Loads userInterface layout.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/layout/blackboardapp_layout.fxml"));
        Pane userInterface = loader.load(); // userInterface holds the loader information
        Scene scene = new Scene(userInterface); // new scene holding userInterface

        // Stage now under windows, calls scene and loads the scene.
        //TODO: New screen when clicked for this APP
        Stage windows = new Stage(); //(Stage) ((Node)event.getSource()).getScene().getWindow(); // hides previous window
        windows.setScene(scene);
        windows.show();
    }

    public void logOut(ActionEvent event) throws Exception {
        LoginController loginController = new LoginController();
        loginController.changeToMainScreen(event);
    }

    public void setStudentName(String student) {
        this.studentLB.setText(student);
    }
}
