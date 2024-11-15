package sc2002.model.role;

import java.io.Serializable;

import org.mindrot.jbcrypt.BCrypt;

public abstract class User implements Serializable {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String hashedPassword;
    private String email;
    private String phoneNumber;
    private UserRole role;

    public User(String id, String username, String firstName, String lastName, String password, String email, String phoneNumber, UserRole role) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.setPassword(password);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserRole getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        // Hash password using BCrypt
        this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.hashedPassword);
    }
}
