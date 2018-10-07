package application.model.creation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

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

    public void loadAccounts() {
    	User user;
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
				user = new User(name, username, password);
				addUser(user);
			}
			
			System.out.println(getUser().size());			
			
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
