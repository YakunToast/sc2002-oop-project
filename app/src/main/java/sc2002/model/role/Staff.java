package sc2002.model.role;

/**
 * Represents a Staff user, extending the base User class.
 */
public class Staff extends User {
    public Staff(String id, String username, String firstName, String lastName, String password, String email, String phoneNumber, UserRole role) {
        super(id, username, firstName, lastName, password, email, phoneNumber, role);
    }
}
