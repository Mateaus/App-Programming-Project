package application.controller.ui;

import application.controller.login.MainLoginController;
import application.model.TitleBar;
import application.model.UserInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserInterfaceController implements Initializable{
	
	@FXML
	private Button minBtn, exitBtn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
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
	
	
    /**
     * switchToWelcome switches to the Welcome tab screen.
     */

    public void switchToCreate(ActionEvent event) {

        try {
            URL createUrl = getClass().getResource("/resources/layout/ui/create_layout.fxml");
            Pane paneCreate = FXMLLoader.load(createUrl);

            BorderPane border = UserInterface.getBorderPane();
            border.setCenter(paneCreate);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * switchToMap switches to the Map tab screen.
     */

    public void switchToSearch(ActionEvent event) {

        try {
            URL searchUrl = getClass().getResource("/resources/layout/ui/search_layout.fxml");
            Pane paneSearch = FXMLLoader.load(searchUrl);

            BorderPane border = UserInterface.getBorderPane();
            border.setCenter(paneSearch);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToHelp(ActionEvent event) {
        try {
            URL helpUrl = getClass().getResource("/resources/layout/ui/help_layout.fxml");
            Pane paneHelp = FXMLLoader.load(helpUrl);

            BorderPane border = UserInterface.getBorderPane();
            border.setCenter(paneHelp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void switchToSettings(ActionEvent event) {
    	try {
    		URL settingsUrl = getClass().getResource("/resources/layout/ui/settings_layout.fxml");
    		Pane paneSettings = FXMLLoader.load(settingsUrl);
    		
    		BorderPane border = UserInterface.getBorderPane();
    		border.setCenter(paneSettings);
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * logOut sets user back to status "offline" and we return back to the loginScreen.
     */

    public void logOut(ActionEvent event) {
        try {
            MainLoginController loginController = new MainLoginController();
            loginController.changeToMainScreen(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
