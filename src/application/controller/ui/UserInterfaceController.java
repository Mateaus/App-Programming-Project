package application.controller.ui;

import TmpFolder.Context;
import application.controller.login.MainLoginController;
import application.model.UserInterface;
import HttpRequests.ActivityRequest;
import HttpRequests.HttpHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserInterfaceController {
	
	
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

    /**
     * logOut sets user back to status "offline" and we return back to the loginScreen.
     */

    public void logOut(ActionEvent event) {
        try {
            /*String studentId = Context.getInstance().currentUserInformation().getStudentId();
            ActivityRequest activityRequest = new ActivityRequest(studentId, "offline");
            HttpHandler httpHandler = new HttpHandler(activityRequest.getActivityRequestUrl(), activityRequest.getValuePairs());
            HttpResponse httpResponse =  httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
            EntityUtils.consume(httpResponse.getEntity());*/


            MainLoginController loginController = new MainLoginController();
            loginController.changeToMainScreen(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            /*String studentId = Context.getInstance().currentUserInformation().getStudentId();
            ActivityRequest activityRequest = new ActivityRequest(studentId, "offline");
            HttpHandler httpHandler = new HttpHandler(activityRequest.getActivityRequestUrl(), activityRequest.getValuePairs());
            HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
            EntityUtils.consume(httpResponse.getEntity());*/
        	Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
