package application.model.account;

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
import application.controller.login.MainLoginController;
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
        this.user = user;
    }

    public void addUser(User user){
    	this.user.add(user);
    }

    public void populateAccounts() {
    	try {
			URL url = new URL("http://last-minute-screws.000webhostapp.com/Accounts.php");
			URLConnection request = url.openConnection();
			request.connect();
			
			JSONObject json = new JSONObject(IOUtils.toString(url, Charset.forName("UTF-8")));
			int accounts = Integer.parseInt(json.getJSONObject("available").get("number").toString());
			
			for(int i = 0; i < accounts; i++) {
				String name = json.getJSONObject("student"+i).get("name").toString();
				String username = json.getJSONObject("student"+i).get("username").toString();
				String password = json.getJSONObject("student"+i).get("password").toString();
				User user = new User(name, username, password);
				addUser(user);
			}
			//Size of accounts
			System.out.println(getUser().size());			
			
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Boolean loginAccount(String username, String password) {
    	try {
            LoginRequest loginRequest = new LoginRequest(username, password);
            HttpHandler httpHandler = new HttpHandler(loginRequest.getLoginRequestUrl(), loginRequest.getValuePairs());
            HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
            String responsejson = EntityUtils.toString(httpResponse.getEntity());
            EntityUtils.consume(httpResponse.getEntity());
            
            JSONObject jsonObject = new JSONObject(responsejson);
            String success = jsonObject.get("success").toString();  
                   
            if(success.equals("true")) {
            	String dbId = jsonObject.get("user_id").toString();
            	String dbUsername = jsonObject.get("username").toString();
                User user = new User(dbId, dbUsername);
                addUser(user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
    public void createAccount(String name, String username, String password) {
    	try {
    		RegisterRequest registerRequest = new RegisterRequest(name, username, password);
    		HttpHandler httpHandler = new HttpHandler(registerRequest.getRegisterRequestUrl(), registerRequest.getValuePairs());
    		HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
    		EntityUtils.consume(httpResponse.getEntity());
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
    
    public void findUserById(int Id) {
    	
    }
    
    
}
