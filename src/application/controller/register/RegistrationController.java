package application.controller.register;

import application.controller.login.MainLoginController;
import application.model.TitleBar;
import application.model.account.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController extends TitleBar implements Initializable{

    @FXML
    private TextField nameTF, usernameTF, passTF, passCheckTF;
    
    @FXML
    private Label errorMessage;
    
    @FXML
    private GridPane titleBar;
    
    @FXML
    private Button minBtn, exitBtn;


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
            if(nameTF != null && !name.isEmpty() && usernameTF != null && !username.isEmpty()
                    && passTF != null && !password.isEmpty() && password.equals(passwordCheck) && !passwordCheck.isEmpty()) {
            	// Creates a new account.
                Account account = new Account();
                account.createAccount(name, username, password);
            	MainLoginController loginController = new MainLoginController();
        		loginController.changeToMainScreen(event);
                
            } else if (nameTF != null && !name.isEmpty() && usernameTF != null && !username.isEmpty()
                    && passTF != null && !password.isEmpty() && !password.equals(passwordCheck)  && !passwordCheck.isEmpty()) {
            	// TODO: Need to include in layout.
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
        MainLoginController loginController = new MainLoginController();
        loginController.changeToMainScreen(event);
    }

}
