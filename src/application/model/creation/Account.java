package application.model.creation;

import java.util.ArrayList;

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

    }
}
