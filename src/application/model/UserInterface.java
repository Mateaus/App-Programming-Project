package application.model;


import javafx.event.Event;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;

import application.model.database.GetGroupInformation;
import application.model.database.UserLoginResponse;


public class UserInterface {

    private static BorderPane borderPane = new BorderPane();
    private static UserLoginResponse userLoginResponse;
    
    public static BorderPane getBorderPane() {
        return borderPane;
    }

    public void startUI(Event event, UserLoginResponse userLoginResponse) throws IOException {
        // Loading our fxml files to be put into a borderpane.
    	GetGroupInformation group = new GetGroupInformation();
		//System.out.println(group.groupID);
		String id = userLoginResponse.getId();
		Pane infoPane;
		
		URL titleUrl = getClass().getResource("/resources/layout/ui/titlebar_layout.fxml");
        GridPane titleBar = FXMLLoader.load(titleUrl);

        // Setting the fxml files that were loaded into our borderpane.
        borderPane.setTop(titleBar);
        
        /**
         * Commented out portions are to set the first primary view when the user logs in to the
         *  create layout.
         */
//		if(group.checkUserGroup(id))
//		{
        /** 
         * Search layout to be displayed primarily instead of create, since more people 
         *  would probably be looking for groups rather than creating... Also it just looks nicer
         */
		URL infoUrl = getClass().getResource("/resources/layout/ui/search_layout.fxml");
		infoPane = FXMLLoader.load(infoUrl);
		borderPane.setCenter(infoPane);
//		}
//		else
//		{
//			URL infoUrl = getClass().getResource("/resources/layout/ui/create_layout.fxml");
//			infoPane = FXMLLoader.load(infoUrl);
//			
//		}
		borderPane.setCenter(infoPane);
		
        // Initializing title bar events.
        titleBar.setOnMouseDragged(
        		eventDrag -> {
        			TitleBar.toolbarDragging(eventDrag);
        			//toolbarDragging(eventDrag);
        		}
        ); 
        
        titleBar.setOnMousePressed(
        		eventPres -> {
        			TitleBar.toolbarPressed(eventPres);
        			//toolbarPressed(eventPres);
        		}
        );  

        // Creating the new scene/window with the borderline we created programatically.
        if(borderPane.getScene() == null){
            Scene scene = new Scene(borderPane, 820, 700);
            // Loading the scene created into the stage and then we show it.
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            setUserLoginResponse(userLoginResponse);
            window.setScene(scene);
            window.show();
        } else {
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            setUserLoginResponse(userLoginResponse);
            window.setScene(borderPane.getScene());
            window.show();
        }

    }
    
    public static UserLoginResponse getUserLoginResponse() {
    	return userLoginResponse;
    }
    
    public static void setUserLoginResponse(UserLoginResponse user) {
    	userLoginResponse = user;
    }
}
