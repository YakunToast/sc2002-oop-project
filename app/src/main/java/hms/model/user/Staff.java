package hms.model.user;

/**
 * Represents a Staff user, extending the base User class.
 */
abstract class Staff extends BaseUser {
    public Staff(String id, String username, String firstName, String lastName, String password, String email, String phoneNumber, UserRole role) {
        super(id, username, firstName, lastName, password, email, phoneNumber, role);
    }
}
