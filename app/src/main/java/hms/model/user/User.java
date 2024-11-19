package hms.model.user;

import java.io.Serializable;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Abstract class representing a user in the system. Contains common attributes and methods for user
 * management.
 */
public abstract class User implements Serializable {
    /** Serial version UID for Serializable interface. */
    private static final long serialVersionUID = 1L;

    /** Unique identifier for the user. */
    private String id;

    /** Username chosen by the user. */
    private String username;

    /** First name of the user. */
    private String firstName;

    /** Last name of the user. */
    private String lastName;

    /** Hashed password for security reasons. */
    private String hashedPassword;

    /** Email address of the user. */
    private String email;

    /** Phone number of the user. */
    private String phoneNumber;

    /** Role of the user within the system. */
    private UserRole role;

    /**
     * Constructs a new User with the specified details.
     *
     * @param id Unique identifier for the user.
     * @param username Username chosen by the user.
     * @param firstName First name of the user.
     * @param lastName Last name of the user.
     * @param password Password chosen by the user, which will be hashed.
     * @param email Email address of the user.
     * @param phoneNumber Phone number of the user.
     * @param role Role of the user within the system.
     */
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
     * Returns the unique identifier for the user.
     *
     * @return The user's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the username chosen by the user.
     *
     * @return The user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName New first name for the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the first name of the user.
     *
     * @return The user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName New last name for the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return The user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email New email address for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the email address of the user.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber New phone number for the user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the phone number of the user.
     *
     * @return The user's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the password for the user by hashing it.
     *
     * @param password New password for the user.
     */
    public void setPassword(String password) {
        // Hash password using BCrypt
        this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Verifies if the provided password matches the stored hashed password.
     *
     * @param password Password to verify.
     * @return True if the passwords match, false otherwise.
     */
    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.hashedPassword);
    }

    /**
     * Returns the role of the user within the system.
     *
     * @return The user's role.
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Returns the full name of the user (first name and last name).
     *
     * @return The user's full name.
     */
    public String getName() {
        return firstName + " " + lastName;
    }
}
