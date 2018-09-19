package Controllers;

import HttpRequests.HttpHandler;
import HttpRequests.RegisterRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import javax.rmi.CORBA.Util;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    @FXML
    private TextField nameTF, usernameTF, passTF;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void registerUser(ActionEvent event) {
        String name = nameTF.getText().toString();
        String username = usernameTF.getText().toString();
        String password = passTF.getText().toString();
        try {
            if(nameTF != null && !name.isEmpty() && usernameTF != null && !username.isEmpty()
                    && passTF != null && !password.isEmpty()) {
                RegisterRequest registerRequest = new RegisterRequest(name, username, password);
                HttpHandler httpHandler = new HttpHandler(registerRequest.getRegisterRequestUrl(), registerRequest.getValuePairs());
                HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
                EntityUtils.consume(httpResponse.getEntity());
                LoginController loginController = new LoginController();
                loginController.changeToMainScreen(event);
            } else {
                System.out.printf("Fill the blanks\n");
            }
        } catch (Exception e) {
            System.out.printf("Account not created\n" + e);
        }


    }

    public void changeToMainScreen(ActionEvent event) throws Exception {
        LoginController loginController = new LoginController();
        loginController.changeToMainScreen(event);
    }
}
