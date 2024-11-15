package sc2002.model.role;

import java.io.Serializable;

public class Pharmacist extends Staff implements Serializable {
    public Pharmacist(String id, String username, String firstName, String lastName, String password, String email, String phoneNumber, UserRole role) {
        super(id, username, firstName, lastName, password, email, phoneNumber, role);
    }
}
