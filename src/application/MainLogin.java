package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainLogin extends Application {

    @Override
    public void start(Stage primaryStage) {
        try{
            // Load the first scene/screen, code can be modified but for now it's not necessary.
            Parent root = FXMLLoader.load(getClass().getResource("/resources/layout/loginandregister/login_layout.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
