package application.controller.login;

import application.model.TitleBar;
import application.model.UserInterface;
import application.model.database.User;
import application.model.database.UserDAO;
import application.model.database.UserLoginResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainLoginController implements Initializable {

    @FXML 
    private Button loginBtn, minBtn, exitBtn;
    @FXML 
    private Pane logLayout;
    @FXML 
    private TextField emailTF, passTF;
    @FXML 
    private GridPane titleBar;
    
    private UserDAO userDAO = new UserDAO();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Any actions done to buttons,etc will be passed through here first.
        // Example: Button1 changes when it's pressed. It will read this function
        // to fetch the action.
    	
        loginBtn.setOnAction(
                event -> {
                    String username = emailTF.getText().toString();
                    String password = passTF.getText().toString();
                    Boolean authentication = userDAO.authenticateUser(new User(username, password));
              
                    if(authentication.equals(true)) {
                    	try {
                    		UserInterface userInterface = new UserInterface();
                    		UserLoginResponse userLoginResponse = userDAO.getUser(username);
                    		if (userLoginResponse != null) {
                    			userInterface.startUI(event, userLoginResponse);
                    		} else {
                    			System.out.println("Username doesn't exist!");
                    		}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println(e);
						}
                    } else {
                    	System.out.println("Wrong password");
                    }
                }
        );

        logLayout.setOnKeyPressed(
                 event -> {
                    switch (event.getCode()) {
                        case ENTER:
                        	String username = emailTF.getText().toString();
                            String password = passTF.getText().toString();
                            Boolean authentication = userDAO.authenticateUser(new User(username, password));
                            if(authentication.equals(true)) {
                            	UserInterface userInterface = new UserInterface();
                            	try {
        							userInterface.startUI(event, userDAO.getUser(username));
        						} catch (IOException e) {
        							// TODO Auto-generated catch block
        							System.out.println(e);
        						}
                            } else {
                            	System.out.println("Wrong password");
                            }
                        default:
                        	break;	
                    }
                }
        );
        
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

    /* We will be calling this function every time we want to return to the main menu/log screen */
    public void changeToMainScreen(ActionEvent event) throws Exception {
        // Loads the main/log layout
        Pane main = (Pane) FXMLLoader.load(getClass().getResource("/resources/layout/loginandregister/login_layout.fxml"));
        Scene mainScreen = new Scene(main);

        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScreen);
        window.show(); // changes to the new window
    }

    public void changeToRegisterScreen(ActionEvent event) throws Exception {
        // Loads the registration layout
        Pane registration = (Pane) FXMLLoader.load(getClass().getResource("/resources/layout/loginandregister/registration_layout.fxml"));
        Scene registrationScreen = new Scene(registration);

        // This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(registrationScreen);
        window.show(); // changes to the new window
    }
}
