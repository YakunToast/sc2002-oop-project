package sc2002.model.role;

import java.util.UUID;

public class Doctor extends Staff {
    public Doctor(UUID id, String username, String firstName, String lastName, String password, String email, String phoneNumber, UserRole role) {
        super(id, username, firstName, lastName, password, email, phoneNumber, role);
    }
}
