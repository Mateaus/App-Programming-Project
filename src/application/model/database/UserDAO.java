package application.model.database;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import HttpRequests.DeleteUserRequest;
import HttpRequests.FindUserRequest;
import HttpRequests.HttpHandler;
import HttpRequests.LoginRequest;
import HttpRequests.RegisterRequest;
import HttpRequests.UpdateInformationRequest;
import HttpRequests.UpdatePasswordRequest;
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
	
	public UserLoginResponse findUser(String username) {
		
		try {
			FindUserRequest getUserRequest = new FindUserRequest(username);
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
	
	public Boolean updateInformation(UserLoginResponse user) {
		try {
			System.out.println(user.getId());
			System.out.println(user.getName());
			System.out.println(user.getUsername());
			UpdateInformationRequest updateInformation = new UpdateInformationRequest(user.getId(), user.getName(), user.getUsername()); 
			HttpHandler httpHandler = new HttpHandler(updateInformation.getUpdateRequestUrl(), updateInformation.getValuePairs());
			HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
			String responsejson = EntityUtils.toString(httpResponse.getEntity());
			EntityUtils.consume(httpResponse.getEntity());
			
			System.out.println(responsejson);
			
			JSONObject jsonObject = new JSONObject(responsejson);
			String updateSuccessful = jsonObject.get("success").toString();
			
			if(updateSuccessful.equals("true")) {
				return true;
			} else {
				return false;
			}
			
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public Boolean updatePassword(User user) {
		try {
			UpdatePasswordRequest updatePassword = new UpdatePasswordRequest(user.getUsername().toString(), user.getPassword().toString());
			HttpHandler httpHandler = new HttpHandler(updatePassword.getUpdateRequestUrl(), updatePassword.getValuePairs());
			HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
			String responsejson = EntityUtils.toString(httpResponse.getEntity());
			EntityUtils.consume(httpResponse.getEntity());
			
			JSONObject jsonObject = new JSONObject(responsejson);
			String updateSuccessful = jsonObject.get("success").toString();
			
			if(updateSuccessful.equals("true")) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	
	public Boolean deleteUser(String id) {
		try {
			DeleteUserRequest deleteUser = new DeleteUserRequest(id);
			HttpHandler httpHandler = new HttpHandler(deleteUser.getDeleteRequestUrl(), deleteUser.getValuePairs());
			HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
			String responsejson = EntityUtils.toString(httpResponse.getEntity());
			EntityUtils.consume(httpResponse.getEntity());
			
			JSONObject jsonObject = new JSONObject(responsejson);
			String deletionSuccessful = jsonObject.get("success").toString();
			
			if(deletionSuccessful.equals("true")) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;	
		}
		
	}

}
