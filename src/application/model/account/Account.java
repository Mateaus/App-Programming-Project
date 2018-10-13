package application.model.account;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import HttpRequests.HttpHandler;
import HttpRequests.LoginRequest;
import HttpRequests.RegisterRequest;

public class Account {
    private User user;

    public Account() {
        this.user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addUser(User user){
    	this.user = user;
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
    	//TODO: If we need to find the specific user's id who has logged in, use this method.
    }
    
    
}
