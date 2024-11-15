package hms.model.user;

public class Pharmacist extends Staff {
    public Pharmacist(String id, String username, String firstName, String lastName, String password, String email, String phoneNumber, UserRole role) {
        super(id, username, firstName, lastName, password, email, phoneNumber, role);
    }
}
