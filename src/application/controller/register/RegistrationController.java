package application.controller.register;

import application.controller.login.MainLoginController;
import application.model.TitleBar;
import application.model.UserInterface;
import application.model.database.User;
import application.model.database.UserDAO;
import application.model.database.UserLoginResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable{

    @FXML
    private TextField nameTF, usernameTF, passTF, passCheckTF;
    
    @FXML
    private Label errorMessage;
    
    @FXML
    private GridPane titleBar;
    
    @FXML
    private Button minBtn, exitBtn;
    
    private UserDAO userDAO = new UserDAO();


    public void initialize(URL location, ResourceBundle resources) {
    	
    	titleBar.setOnMouseDragged(
    			eventDrag -> {
    				TitleBar.toolbarDragging(eventDrag);
    			}
    	);
    	titleBar.setOnMousePressed(
    			eventPres -> {
    				TitleBar.toolbarPressed(eventPres);
    			}
    	);
    	
    	minBtn.setOnMouseClicked(
    			eventMinClicked -> {
    				TitleBar.toolbarMinimize(eventMinClicked);
    			}
    	);
    	
    	exitBtn.setOnMouseClicked(
    			eventExitClicked -> {
    				TitleBar.toolbarExit(eventExitClicked);
    			}
    	);
    	
    }

    public void registerUser(ActionEvent event) {
        String name = nameTF.getText().toString();
        String username = usernameTF.getText().toString();
        String password = passTF.getText().toString();
        String passwordCheck = passCheckTF.getText().toString();
        try {
        	/**
        	 * Verifies that the username the user is wanting to register with, does not already exist
        	 */
            if(nameTF != null && !name.isEmpty() && usernameTF != null && !username.isEmpty()
                    && passTF != null && !password.isEmpty() && password.equals(passwordCheck) && !passwordCheck.isEmpty()) 
            {
            	Boolean userAuthentication = userDAO.checkIfUsernameExists(new User(username, password));                
                if(userAuthentication == true) {
                	userDAO.createUser(new User(name, username, password));
                	MainLoginController loginController = new MainLoginController();
            		loginController.changeToMainScreen(event);
                } else {
                	/** Displays the error message that the username already exists */
                	errorMessage.setText("Username already exists");
                	errorMessage.setOpacity(1);
                }
            } else if (nameTF != null && !name.isEmpty() && usernameTF != null && !username.isEmpty()
                    && passTF != null && !password.isEmpty() && !password.equals(passwordCheck)  && !passwordCheck.isEmpty()) 
            {
            	/** Displays the error message that the passwords provided do not match */
            	errorMessage.setText("Passwords do not match");
            	errorMessage.setOpacity(1);
            } else {
            	/** Displays the error message that there are still blank fields */
            	String fillException = "Fill the blanks";
            	errorMessage.setText(fillException);
            	errorMessage.setOpacity(1);
            }
        } catch (Exception e) {
        	/** Displays the error message the the username already exists to the user */
        	System.out.printf("Account not created\n" + e);
        	errorMessage.setText("Account not created");
        	errorMessage.setOpacity(1);
        }


    }

    public void changeToMainScreen(ActionEvent event) throws Exception {
        MainLoginController loginController = new MainLoginController();
        loginController.changeToMainScreen(event);
    }

}
