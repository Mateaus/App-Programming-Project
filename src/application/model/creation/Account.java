package application.model.creation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import HttpRequests.HttpHandler;
import HttpRequests.LoginRequest;
import HttpRequests.RegisterRequest;
import TmpFolder.Context;
import application.controller.loginandregister.LoginController;
import application.model.UserInterface;
import javafx.event.ActionEvent;
import javafx.event.Event;

public class Account {
    private ArrayList<User> user;

    public Account() {
        this.user = new ArrayList<>();
    }

    public ArrayList<User> getUser() {
        return user;
    }

    public void setUser(ArrayList<User> user) {
        this.user.addAll(user);
    }

    public void addUser(User user){
        this.user.add(user);
    }

    public void populateAccounts() {
    	try {
    		User user = null;
			URL url = new URL("http://last-minute-screws.000webhostapp.com/Accounts.php");
			URLConnection request = url.openConnection();
			request.connect();
			
			JSONObject json = new JSONObject(IOUtils.toString(url, Charset.forName("UTF-8")));
			int accounts = Integer.parseInt(json.getJSONObject("available").get("number").toString());
			
			for(int i = 0; i < accounts; i++) {
				String name = json.getJSONObject("student"+i).get("name").toString();
				String username = json.getJSONObject("student"+i).get("username").toString();
				String password = json.getJSONObject("student"+i).get("password").toString();
				user = new User(name, username, password);
				addUser(user);
			}
			
			System.out.println(getUser().size());			
			
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void loginAccount(String email, String password, Event event) {
    	try {
            LoginRequest loginRequest = new LoginRequest(email, password);
            HttpHandler httpHandler = new HttpHandler(loginRequest.getLoginRequestUrl(), loginRequest.getValuePairs());
            HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
            String responsejson = EntityUtils.toString(httpResponse.getEntity());
            EntityUtils.consume(httpResponse.getEntity());

            JSONObject jsonObject = new JSONObject(responsejson);
            String studentId = jsonObject.get("user_id").toString();
            String studentName = jsonObject.get("name").toString();
            Context.getInstance().currentUserInformation().setStudentId(studentId);
            Context.getInstance().currentUserInformation().setStudentName(studentName);

            if(loginRequest.checkRequest(responsejson).equals(true)){
                UserInterface userInterface = new UserInterface();
                userInterface.startUI(event);
            } else {
                //TODO: Add something to show user they have entered wrong username or password.
                System.out.println("Wrong password");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void createAccount(String name, String username, String password, ActionEvent event) {
    	try {
    		RegisterRequest registerRequest = new RegisterRequest(name, username, password);
    		HttpHandler httpHandler = new HttpHandler(registerRequest.getRegisterRequestUrl(), registerRequest.getValuePairs());
    		HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
    		EntityUtils.consume(httpResponse.getEntity());
    		LoginController loginController = new LoginController();
    		loginController.changeToMainScreen(event);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
    
    
}
