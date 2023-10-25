package User;

public abstract class User {
    private final String username;
    private final String password;
    private boolean loggedIn = false;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean state) {
        loggedIn = state;
    }

    public abstract void showUserProfile();
}
