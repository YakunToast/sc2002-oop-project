package hms.model.user;

/**
 * Represents a pharmacist in the hospital management system. Inherits from the Staff class and sets
 * the UserRole to PHARMACIST.
 */
public class Pharmacist extends Staff {
    /**
     * Constructs a new Pharmacist object with the specified details.
     *
     * @param id the unique identifier for the pharmacist
     * @param username the username used for authentication
     * @param firstName the first name of the pharmacist
     * @param lastName the last name of the pharmacist
     * @param password the password used for authentication
     * @param email the email address of the pharmacist
     * @param phoneNumber the phone number of the pharmacist
     */
    public Pharmacist(
            String id,
            String username,
            String firstName,
            String lastName,
            String password,
            String email,
            String phoneNumber) {
        super(id, username, firstName, lastName, password, email, phoneNumber, UserRole.PHARMACIST);
    }
}
