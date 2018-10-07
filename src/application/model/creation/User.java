package application.model.creation;

public class User {

    private String Name;
    private String Username;
    private String Password;

    public User(String name, String username, String password){
        this.Name = name;
        this.Username = username;
        this.Password = password;
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
