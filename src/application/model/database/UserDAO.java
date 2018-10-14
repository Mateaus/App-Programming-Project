package application.model.database;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import HttpRequests.GetUserRequest;
import HttpRequests.HttpHandler;
import HttpRequests.LoginRequest;
import HttpRequests.RegisterRequest;
import application.model.database.User;

public class UserDAO {
	
	public void createUser(User user) {
		
		try {
    		RegisterRequest registerRequest = new RegisterRequest(user.getName(),user.getUsername(),user.getPassword());
    		HttpHandler httpHandler = new HttpHandler(registerRequest.getRegisterRequestUrl(), registerRequest.getValuePairs());
    		HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
    		EntityUtils.consume(httpResponse.getEntity());
    		
    	} catch (Exception e) {
    		System.out.println(e);
    	}
	}
	
	public Boolean authenticateUser(User user) {
		try {
            LoginRequest loginRequest = new LoginRequest(user.getUsername(), user.getPassword());
            HttpHandler httpHandler = new HttpHandler(loginRequest.getLoginRequestUrl(), loginRequest.getValuePairs());
            HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
            String responsejson = EntityUtils.toString(httpResponse.getEntity());
            EntityUtils.consume(httpResponse.getEntity());
            
            JSONObject jsonObject = new JSONObject(responsejson);
            String success = jsonObject.get("success").toString();  
                   
            if(success.equals("true")) {
            	String dbId = jsonObject.get("user_id").toString();
            	user.setId(dbId);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
	}
	
	public UserLoginResponse getUser(String username) {
		
		try {
			GetUserRequest getUserRequest = new GetUserRequest(username);
			HttpHandler httpHandler = new HttpHandler(getUserRequest.getUserRequestUrl(), getUserRequest.getValuePairs());
			HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
			String responsejson = EntityUtils.toString(httpResponse.getEntity());
			EntityUtils.consume(httpResponse.getEntity());
			
			JSONObject jsonObject = new JSONObject(responsejson);
			String userExist = jsonObject.get("success").toString();
			
			if(userExist.equals("true")) {
				String id = jsonObject.getJSONObject("student").get("id").toString();
				String name = jsonObject.getJSONObject("student").get("name").toString();
				String userName = jsonObject.getJSONObject("student").get("username").toString();
				
				UserLoginResponse userLoginResponse = new UserLoginResponse(id, name, userName);
				
				return userLoginResponse;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public User findUser(User user) {
		return user;
	}
	
	public void updateUser(User user) {
		
	}
	
	public void deleteUser(User user) {
		
	}

}
