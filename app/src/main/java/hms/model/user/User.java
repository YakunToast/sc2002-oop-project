package hms.model.user;

import java.io.Serializable;

import org.mindrot.jbcrypt.BCrypt;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String hashedPassword;
    private String email;
    private String phoneNumber;
    private UserRole role;

    public User(
            String id,
            String username,
            String firstName,
            String lastName,
            String password,
            String email,
            String phoneNumber,
            UserRole role) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.setPassword(password);
    }

    /**
     * @return String
     */
    public String getId() {
        return id;
    }

    
    /** 
     * @return String
     */
    public String getUsername() {
        return username;
    }

    
    /** 
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    /** 
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    
    /** 
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    /** 
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    
    /** 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    
    /** 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    
    /** 
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    
    /** 
     * @return String
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    
    /** 
     * @param password
     */
    public void setPassword(String password) {
        // Hash password using BCrypt
        this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    
    /** 
     * @param password
     * @return boolean
     */
    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.hashedPassword);
    }

    
    /** 
     * @return UserRole
     */
    public UserRole getRole() {
        return role;
    }

    
    /** 
     * @return String
     */
    public String getName() {
        return firstName + " " + lastName;
    }
}
