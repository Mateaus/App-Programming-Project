package Controllers;

import Classes.Context;
import Classes.UserInterface;
import HttpRequests.ActivityRequest;
import HttpRequests.HttpHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class UIController {

    /**
     * switchToWelcome switches to the Welcome tab screen.
     */

    public void switchToWelcome(ActionEvent event) {

        try {
            URL paneOneUrl = getClass().getResource("/resources/layout/welcome_layout.fxml");
            Pane paneOne = FXMLLoader.load( paneOneUrl );

            BorderPane border = UserInterface.getBorderPane();
            border.setCenter(paneOne);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * switchToMap switches to the Map tab screen.
     */

    public void switchToMap(ActionEvent event) {

        try {
            URL paneTwoUrl = getClass().getResource("/resources/layout/map_layout.fxml");
            Pane paneTwo = FXMLLoader.load( paneTwoUrl );

            BorderPane border = UserInterface.getBorderPane();
            border.setCenter(paneTwo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * logOut sets user back to status "offline" and we return back to the loginScreen.
     */

    public void logOut(ActionEvent event) {
        try {
            String studentId = Context.getInstance().currentUserInformation().getStudentId();
            ActivityRequest activityRequest = new ActivityRequest(studentId, "offline");
            HttpHandler httpHandler = new HttpHandler(activityRequest.getActivityRequestUrl(), activityRequest.getValuePairs());
            httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());

            LoginController loginController = new LoginController();
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
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
