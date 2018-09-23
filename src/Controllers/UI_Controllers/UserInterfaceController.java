package Controllers.UI_Controllers;

import Classes.Context;
import Classes.UserInterface;
import Controllers.LoginAndRegister_Controllers.LoginController;
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

public class UserInterfaceController {

    /**
     * switchToWelcome switches to the Welcome tab screen.
     */

    public void switchToWelcome(ActionEvent event) {

        try {
            URL welcomeUrl = getClass().getResource("/resources/layout/UI_Layouts/welcome_layout.fxml");
            Pane paneWelcome = FXMLLoader.load(welcomeUrl);

            BorderPane border = UserInterface.getBorderPane();
            border.setCenter(paneWelcome);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * switchToMap switches to the Map tab screen.
     */

    public void switchToMap(ActionEvent event) {

        try {
            URL mapUrl = getClass().getResource("/resources/layout/UI_Layouts/map_layout.fxml");
            Pane paneMap = FXMLLoader.load(mapUrl);

            BorderPane border = UserInterface.getBorderPane();
            border.setCenter(paneMap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToWeather(ActionEvent event) {
        try {
            URL weatherUrl = getClass().getResource("/resources/layout/UI_Layouts/weatherapp_layout.fxml");
            Pane paneWeather = FXMLLoader.load(weatherUrl);

            BorderPane border = UserInterface.getBorderPane();
            border.setCenter(paneWeather);

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
            HttpResponse httpResponse =  httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
            EntityUtils.consume(httpResponse.getEntity());


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
        try {
            String studentId = Context.getInstance().currentUserInformation().getStudentId();
            ActivityRequest activityRequest = new ActivityRequest(studentId, "offline");
            HttpHandler httpHandler = new HttpHandler(activityRequest.getActivityRequestUrl(), activityRequest.getValuePairs());
            HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
            EntityUtils.consume(httpResponse.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
