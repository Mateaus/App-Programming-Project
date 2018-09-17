package Controllers;

import Classes.Context;
import Database.DatabaseStatus;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class UserInterfaceController implements Initializable {

    public DatabaseStatus databaseStatus = new DatabaseStatus();

    @FXML private Label studentLB;
    @FXML private Label studentsnmLB;
    @FXML private Button logoutBtn;
    private String studentId;
    private volatile boolean isRunning = true;

    Timer t = new Timer();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // We start reading from here
        Boolean flag = true;
        studentLB.setText(Context.getInstance().currentUserInformation().getStudentName());

        try {
            String studentId = Context.getInstance().currentUserInformation().getStudentId();
            ActivityRequest activityRequest = new ActivityRequest(studentId, "online");
            HttpHandler httpHandler = new HttpHandler(activityRequest.getActivityRequestUrl(), activityRequest.getValuePairs());
            httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            StatusRequest statusRequest = new StatusRequest("online");
            HttpHandler httpHandler = new HttpHandler(statusRequest.getStatusRequestUrl(), statusRequest.getValuePairs());
            HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
            String json = EntityUtils.toString(httpResponse.getEntity());
            JSONObject jsonObject = new JSONObject(json);
            int studentsAvailable = Integer.parseInt(jsonObject.getJSONObject("available").get("number").toString());
            String studentNames = "";
            for (int i = 0; i < studentsAvailable; i++) {
                studentNames = studentNames + "-" + jsonObject.getJSONObject("student"+i).get("name").toString() + "\n";
            }
            System.out.println(studentNames);
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
                                JSONObject jsonObject = new JSONObject(json);
                                int studentsAvailable = Integer.parseInt(jsonObject.getJSONObject("available").get("number").toString());
                                String studentNames = "";
                                for (int i = 0; i < studentsAvailable; i++) {
                                    studentNames = studentNames + "-" + jsonObject.getJSONObject("student"+i).get("name").toString() + "\n";
                                }
                                System.out.println(studentNames);
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

                        LoginController loginController = new LoginController();
                        loginController.changeToMainScreen(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
        );


    }

    public void blackBoardApp(ActionEvent event) throws Exception {
        // Loads userInterface layout.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/layout/blackboardapp_layout.fxml"));
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
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/layout/weatherapp_layout.fxml"));
        Pane userInterface = loader.load(); // userInterface holds the loader information
        Scene scene = new Scene(userInterface); // new scene holding userInterface

        // Stage now under windows, calls scene and loads the scene.
        //TODO: New screen when clicked for this APP
        Stage windows = (Stage) ((Node)event.getSource()).getScene().getWindow(); // hides previous window
        windows.setScene(scene);
        windows.show();
    }

    
    public void logOut(ActionEvent event) throws Exception {
        String studentId = Context.getInstance().currentUserInformation().getStudentId();
        System.out.println(studentId);
        ActivityRequest activityRequest = new ActivityRequest(studentId, "offline");
        HttpHandler httpHandler = new HttpHandler(activityRequest.getActivityRequestUrl(), activityRequest.getValuePairs());
        httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());

        LoginController loginController = new LoginController();
        loginController.changeToMainScreen(event);
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
