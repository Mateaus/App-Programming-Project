package application.model.account;

public class User {

	private String Id;
    private String Name;
    private String Username;
    private String Password;

    public User(String name, String username, String password){
        this.Name = name;
        this.Username = username;
        this.Password = password;
    }
    
    public User(String id, String username) {
    	this.Id = id;
    	this.Username = username;
    }
    
    public String getId() {
    	return Id;
    }
    
    public void setId(String id) {
    	Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
