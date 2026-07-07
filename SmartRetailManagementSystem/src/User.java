import java.util.Scanner;

/**
 * Abstract superclass for the smart retail's user (staff or customer).
 * <p>This is a base class that provides common attributes including
 * name, username, password, and account status to its subclasses.</p>
 */
public abstract class User {
    private String name;
    private String username, password;
    protected Scanner input;
    private boolean accStatus = true;

    /**
     * Constructs a User object.
     * @param name The name of the user, including staff and customer.
     * @param username The username used for user to log in.
     * @param password The password used for user to log in.
     */
    public User(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the name of the system user.
     * @return The name of the user, including staff and customer.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the username of the system user.
     * @return The username used for user to log in.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the account status of the system user.
     * @return The account status for the user.
     */
    public boolean getStatus(){
        return accStatus;
    }

    /**
     * Toggles the user's account status between active and inactive.
     */
    public void toggleStatus() {
        this.accStatus = !this.accStatus;
    }

    /**
     * Sets the name of the system user.
     * @param name The name of the user, including staff and customer.
     */
    public void setName(String name){
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    /**
     * Sets the username of the system user.
     * @param username The username used for user to log in.
     */
    public void setUsername(String username){
        if (username != null && !username.trim().isEmpty()) {
            this.username = username;
        }
    }

    /**
     * Sets the password of the system user.
     * @param password The password used for user to log in.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Performs actions after a successful login.
     * @param input The Scanner object used to read user input.
     */
    protected abstract void onLoginSuccess(Scanner input);

    /**
     * Checks the user's login credentials.
     * @param user The username entered by user.
     * @param pass The password entered by user.
     * @return true if the username and password match; otherwise, false.
     */
    protected boolean authenticate(String user, String pass){
        return username.equals(user) && password.equals(pass);
    }

}