package hms.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.Pharmacist;
import hms.model.user.User;
import hms.model.user.UserRole;

/** Repository class for managing user data. */
public class UserRepository extends BaseRepository {

    private Map<String, User> users;

    /** Constructs a new UserRepository instance. */
    public UserRepository() {
        users = new HashMap<>();
    }

    /**
     * Adds a user to the repository.
     *
     * @param user the user to be added
     */
    public void addUser(User User) {
        users.put(User.getId(), User);
    }

    /**
     * Removes a user from the repository.
     *
     * @param user the user to be removed
     * @return true if the user was successfully removed, false otherwise
     */
    public boolean removeUser(User user) {
        return users.remove(user.getId(), user);
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return a list of all users in the repository
     */
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        for (User user : users.values()) {
            result.add(castToAppropriateType(user));
        }
        return result;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to be retrieved
     * @return an optional containing the user if found, or an empty optional if not found
     */
    public Optional<User> getUserById(String id) {
        return Optional.ofNullable(castToAppropriateType(users.get(id)));
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to be retrieved
     * @return an optional containing the user if found, or an empty optional if not found
     */
    public Optional<User> getUserByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .map(UserRepository::castToAppropriateType)
                .findFirst();
    }

    /**
     * Casts a user to the appropriate type based on their role.
     *
     * @param user the user to be cast
     * @return the user cast to the appropriate type
     */
    private static User castToAppropriateType(User user) {
        if (user == null) {
            return null;
        }
        return switch (user.getRole()) {
            case UserRole.PATIENT -> (Patient) user;
            case UserRole.DOCTOR -> (Doctor) user;
            case UserRole.PHARMACIST -> (Pharmacist) user;
            case UserRole.ADMINISTRATOR -> (Administrator) user;
            default -> user;
        };
    }
}
