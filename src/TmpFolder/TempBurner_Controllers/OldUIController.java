package TmpFolder.TempBurner_Controllers;

import TmpFolder.Context;
import application.controller.login.MainLoginController;
import HttpRequests.ActivityRequest;
import HttpRequests.HttpHandler;
import HttpRequests.StatusRequest;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class OldUIController implements Initializable {

    @FXML private Label studentLB;
    @FXML private Label studentsnmLB;
    @FXML private Button logoutBtn;
    private String studentId;
    private volatile boolean isRunning = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // We get the name from our global class which we stored from the previous scene.
        studentLB.setText(Context.getInstance().currentUserInformation().getStudentName());
        try {
            /*
             * Request to the database to change anyone who logged in to be set as "online" status in the
             * database server.
             */
            String studentId = Context.getInstance().currentUserInformation().getStudentId();
            ActivityRequest activityRequest = new ActivityRequest(studentId, "online");
            HttpHandler httpHandler = new HttpHandler(activityRequest.getActivityRequestUrl(), activityRequest.getValuePairs());
            HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
            EntityUtils.consume(httpResponse.getEntity());

            /*
             * Request to the database to look everyone who's status is currently "online" and to get their names.
             * We then set our test label "studentsnmLB" to be equals to the string of all the names we gathered
             * from the database.
             */
            StatusRequest statusRequest = new StatusRequest("online");
            httpHandler = new HttpHandler(statusRequest.getStatusRequestUrl(), statusRequest.getValuePairs());
            httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
            String json = EntityUtils.toString(httpResponse.getEntity());
            EntityUtils.consume(httpResponse.getEntity());
            JSONObject jsonObject = new JSONObject(json);
            int studentsAvailable = Integer.parseInt(jsonObject.getJSONObject("available").get("number").toString());
            String studentNames = "";
            for (int i = 0; i < studentsAvailable; i++) {
                studentNames = studentNames + "-" + jsonObject.getJSONObject("student"+i).get("name").toString() + "\n";
            }
            //System.out.println(studentNames);
            studentsnmLB.setText(studentNames);
        } catch (Exception e) {
            System.out.println(e);
        }

        // TODO: Write a cleaner thread and make it stop when not being used.
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                int i = 0;
                while (isRunning) {
                    final int finalI = i;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                StatusRequest statusRequest = new StatusRequest("online");
                                HttpHandler httpHandler = new HttpHandler(statusRequest.getStatusRequestUrl(), statusRequest.getValuePairs());
                                HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
                                String json = EntityUtils.toString(httpResponse.getEntity());
                                EntityUtils.consume(httpResponse.getEntity());
                                JSONObject jsonObject = new JSONObject(json);
                                int studentsAvailable = Integer.parseInt(jsonObject.getJSONObject("available").get("number").toString());
                                String studentNames = "";
                                for (int i = 0; i < studentsAvailable; i++) {
                                    studentNames = studentNames + "-" + jsonObject.getJSONObject("student"+i).get("name").toString() + "\n";
                                }
                                //System.out.println(studentNames);
                                studentsnmLB.setText(studentNames);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    });
                    i++;
                    Thread.sleep(10000);
                }
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        logoutBtn.setOnAction(
                event -> {
                    try {
                        this.isRunning = false;
                        String studentId = Context.getInstance().currentUserInformation().getStudentId();
                        ActivityRequest activityRequest = new ActivityRequest(studentId, "offline");
                        HttpHandler httpHandler = new HttpHandler(activityRequest.getActivityRequestUrl(), activityRequest.getValuePairs());
                        httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());

                        MainLoginController loginController = new MainLoginController();
                        loginController.changeToMainScreen(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
        );
    }

    public void blackBoardApp(ActionEvent event) throws Exception {
        // Loads userInterface layout.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TmpFolder/TempBurner_Layouts/blackboardapp_layout.fxml"));
        Pane userInterface = loader.load(); // userInterface holds the loader information
        Scene scene = new Scene(userInterface); // new scene holding userInterface

        // Stage now under windows, calls scene and loads the scene.
        //TODO: New screen when clicked for this APP
        Stage windows = new Stage(); //(Stage) ((Node)event.getSource()).getScene().getWindow(); // hides previous window
        windows.setScene(scene);
        windows.show();
    }
    
    public void weatherApp(ActionEvent event) throws Exception
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/TmpFolder/TempBurner_Layouts/weatherapp_layout.fxml"));
        Pane userInterface = loader.load(); // userInterface holds the loader information
        Scene scene = new Scene(userInterface); // new scene holding userInterface

        // Stage now under windows, calls scene and loads the scene.
        //TODO: New screen when clicked for this APP
        Stage windows = (Stage) ((Node)event.getSource()).getScene().getWindow(); // hides previous window
        windows.setScene(scene);
        windows.show();
    }

    public void setStudentName(String student) {
        this.studentLB.setText(student);
    }

    public void setStudentId(String student) {
        this.studentId = student;
    }

    public String getStudentId() {
        return this.studentId;
    }
}
