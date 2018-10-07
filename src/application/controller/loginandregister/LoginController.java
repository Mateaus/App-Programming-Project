package application.controller.loginandregister;

import TmpFolder.Context;
import application.model.UserInterface;
import application.model.creation.Account;
import HttpRequests.HttpHandler;
import HttpRequests.LoginRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML private Button loginBtn;
    @FXML private Pane logLayout;
    @FXML private TextField emailTF, passTF;

    private String email, password;
    private double x, y;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Any actions done to buttons,etc will be passed through here first.
        // Example: Button1 changes when it's pressed. It will read this function
        // to fetch the action.
    	Account account = new Account();
    	account.populateAccounts();

        loginBtn.setOnAction(
                event -> {
                    email = emailTF.getText().toString();
                    password = passTF.getText().toString();
                    account.loginAccount(email, password, event);
                }
        );

        logLayout.setOnKeyPressed(
                 event -> {
                    switch (event.getCode()) {
                        case ENTER:
                            email = emailTF.getText().toString();
                            password = passTF.getText().toString();
                            account.loginAccount(email, password, event);
                    }
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

    //TODO: Fix toolbarDrag to only be on the toolbar and not the entire layout.

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
