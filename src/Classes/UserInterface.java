package Classes;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;


public class UserInterface {

    private static BorderPane borderPane = new BorderPane();
    private static double x, y;
    private Event event;

    public static BorderPane getBorderPane() {
        return borderPane;
    }

    public void startUI(ActionEvent actionEvent, KeyEvent keyEvent) throws IOException {
        // if action event == null, event = keyEvent else actionEvent;
        event = (actionEvent == null ? keyEvent : actionEvent);

        // Loading our fxml files to be put into a borderpane.
        URL menuUrl = getClass().getResource("/resources/layout/UI_Layouts/sidemenu_layout.fxml");
        VBox leftMenu = FXMLLoader.load(menuUrl);

        URL infoUrl = getClass().getResource("/resources/layout/UI_Layouts/welcome_layout.fxml");
        Pane infoPane = FXMLLoader.load(infoUrl);

        URL titleUrl = getClass().getResource("/resources/layout/TempBurner_Layouts/titlebar_layout.fxml");
        GridPane titleBar = FXMLLoader.load(titleUrl);

        // Setting the fxml files that were loaded into our borderpane.
        borderPane.setTop(titleBar);
        borderPane.setCenter(infoPane);
        // Initializing title bar events.
        borderPane.setOnMouseDragged(
                eventDrag -> {
                    toolbarDragging(eventDrag);
                }
        );
        borderPane.setOnMousePressed(
                eventPres -> {
                    toolbarPressed(eventPres);
                }
        );

        // Creating the new scene/window with the borderline we created programatically.
        if(borderPane.getScene() == null){
            Scene scene = new Scene(borderPane, 820, 700);
            // Loading the scene created into the stage and then we show it.
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(borderPane.getScene());
            window.show();
        }

    }

    /*
     *  The methods below are used for the only purpose of controlling the customized created
     *  toolbar.
     *
     *  toolbarDragging methods helps us move the scene by dragging on the toolbar area.
     */
    public static void toolbarDragging(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - x);
        stage.setY(mouseEvent.getScreenY() - y);
    }

    /*
     * toolbarPressed gets us the x and y values to then be used on toolbarDragging
     * as we click on the toolbar location.
     */

    public static void toolbarPressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }
}
