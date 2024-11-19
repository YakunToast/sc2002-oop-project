package hms.model.user;

/**
 * Represents an administrator user within the system, extending the base User class. Administrators
 * have specific roles and permissions that differentiate them from other user types.
 */
public class Administrator extends User {

    /**
     * Constructs a new Administrator instance with the specified details.
     *
     * @param id the unique identifier for the administrator
     * @param username the username used for logging into the system
     * @param firstName the administrator's first name
     * @param lastName the administrator's last name
     * @param password the password used for authentication
     * @param email the administrator's email address
     * @param phoneNumber the administrator's phone number
     */
    public Administrator(
            String id,
            String username,
            String firstName,
            String lastName,
            String password,
            String email,
            String phoneNumber) {
        super(
                id,
                username,
                firstName,
                lastName,
                password,
                email,
                phoneNumber,
                UserRole.ADMINISTRATOR);
    }
}
