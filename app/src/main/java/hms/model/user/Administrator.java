package hms.model.user;

/** Represents a Administrator user, extending the base User class. */
public class Administrator extends User {
    public Administrator(
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
