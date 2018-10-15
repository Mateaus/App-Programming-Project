package application.controller.ui;

import java.net.URL;
import java.util.ResourceBundle;

import application.controller.login.MainLoginController;
import application.model.UserInterface;
import application.model.database.User;
import application.model.database.UserDAO;
import application.model.database.UserLoginResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsController implements Initializable{
	
	@FXML 
	private Label idLb, warningLb, passLb;
	
	@FXML
	private Button saveBtn, changepassBtn;
	
	@FXML
	private Button logoutBtn, deleteaccBtn;
	
	@FXML
	private PasswordField newPF;
	
	@FXML
	private TextField usernameTF, nameTF;
	
	private UserLoginResponse user = UserInterface.getUserLoginResponse();
	private UserDAO userDAO = new UserDAO();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		idLb.setText(user.getId().toString());
		warningLb.setText("\t\tErasing this account\nis irreversible!");
		passLb.setText("Use this option to change\nyour password.");
			
		usernameTF.setText(user.getUsername().toString());
		nameTF.setText(user.getName().toString());
		
	}
	
	public void updateUserInformation(ActionEvent event) {
		String id = user.getId().toString();
		String name = nameTF.getText().toString();
		String username = usernameTF.getText().toString();
		Boolean updateSuccessful = false;
		
		if(!name.isEmpty() && name != null && !username.isEmpty() && username != null 
				&& !name.equals(user.getName().toString()) && !username.equals(user.getUsername().toString())) {
			updateSuccessful = userDAO.updateInformation(new UserLoginResponse(id, name, username));
			System.out.println("Change both fields.");
			// set label to update successful
		} else if (!name.isEmpty() && name != null && !name.equals(user.getName().toString())){
			updateSuccessful = userDAO.updateInformation(new UserLoginResponse(id, name, user.getUsername().toString()));
			System.out.println("Changed first field");
			// set label to update successful
		} else if (!username.isEmpty() && username != null && !username.equals(user.getUsername().toString())) {
			updateSuccessful = userDAO.updateInformation(new UserLoginResponse(id, user.getName().toString(), username));
			System.out.println("Change second field.");
			// set label to update successful
		} else if(name.isEmpty() && username.isEmpty()){
			//TODO: Create a label/text warning showing that there is an error
			System.out.println("No blank textfields allowed!");
		} else {
			//Do nothing :(
		}
	}
	
	public void updateUserPassword(ActionEvent event) {
		String username = user.getUsername().toString();
		String password = newPF.getText().toString();
		
		if(!password.isEmpty() && password != null) {
			Boolean updateSuccessful = userDAO.updatePassword(new User(username, password));
			if(updateSuccessful.equals(true)) {
				//update label saying the update was a success...
			} else {
				//update label saying error updating password...
			}
		} else {
			//password field is empty so do nothing click click...
		}
	}
	
	public void deleteUser(ActionEvent event) {
		String id = user.getId().toString();
		Boolean deleteSuccessful = userDAO.deleteUser(id);
		if(deleteSuccessful.equals(true)) {
			// update label that the account was deleted
            try {
            	MainLoginController loginController = new MainLoginController();
				loginController.changeToMainScreen(event);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// error deleting account
		}
		
	}
}
