package Classes;

public class Context {

    private final static Context instance = new Context();

    public static Context getInstance() {
        return instance;
    }

    private UserInformation userInformation = new UserInformation();

    public UserInformation currentUserInformation() {
        return userInformation;
    }
}
