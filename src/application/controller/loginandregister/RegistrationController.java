package application.controller.loginandregister;

import HttpRequests.HttpHandler;
import HttpRequests.RegisterRequest;
import application.model.creation.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    @FXML
    private TextField nameTF, usernameTF, passTF, passCheckTF;
    
    @FXML
    private Label errorMessage;

    private double x, y;

    public void initialize(URL location, ResourceBundle resources) {
    	
    }

    public void registerUser(ActionEvent event) {
        String name = nameTF.getText().toString();
        String username = usernameTF.getText().toString();
        String password = passTF.getText().toString();
        String passwordCheck = passCheckTF.getText().toString();
        try {
            if(nameTF != null && !name.isEmpty() && usernameTF != null && !username.isEmpty()
                    && passTF != null && !password.isEmpty() && password.equals(passwordCheck)) {
            	// Creates a new account.
                Account account = new Account();
                account.createAccount(name, username, passwordCheck, event);
                
            } else if (nameTF != null && !name.isEmpty() && usernameTF != null && !username.isEmpty()
                    && passTF != null && !password.isEmpty() && !password.equals(passwordCheck)) {
            	String passException = "Passwords do not match";
            	errorMessage.setText(passException);
            } else {
            	String fillException = "Fill the blanks";
            	errorMessage.setText(fillException);
            }
        } catch (Exception e) {
            System.out.printf("Account not created\n" + e);
        }


    }

    public void changeToMainScreen(ActionEvent event) throws Exception {
        LoginController loginController = new LoginController();
        loginController.changeToMainScreen(event);
    }

    /**
     *  The methods below are used for the only purpose of controlling the customized created
     *  toolbar.
     *
     *  toolbarDragging methods helps us move the scene by dragging on the toolbar area.
     */
    public void toolbarDragging(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - x);
        stage.setY(mouseEvent.getScreenY() - y);
    }

    /**
     * toolbarPressed gets us the x and y values to then be used on toolbarDragging
     * as we click on the toolbar location.
     */

    public void toolbarPressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }

    /**
     * toolbarMinimize is utilized to minimize the screen.
     */

    public void toolbarMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * toolbarExit is utilized to exit the screen.
     */

    public void toolbarExit(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
