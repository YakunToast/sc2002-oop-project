package hms.model.user;

/** Represents a Staff user, extending the base User class. */
public class Staff extends User {

    /**
     * Constructs a new Staff user with the specified details.
     *
     * @param id the unique identifier for the staff user
     * @param username the username for the staff user
     * @param firstName the first name of the staff user
     * @param lastName the last name of the staff user
     * @param password the password for the staff user
     * @param email the email address of the staff user
     * @param phoneNumber the phone number of the staff user
     * @param role the role assigned to the staff user
     */
    public Staff(
            String id,
            String username,
            String firstName,
            String lastName,
            String password,
            String email,
            String phoneNumber,
            UserRole role) {
        super(id, username, firstName, lastName, password, email, phoneNumber, role);
    }
}
