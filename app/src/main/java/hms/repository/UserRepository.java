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

public class UserRepository extends BaseRepository {

    private Map<String, User> users;

    public UserRepository() {
        users = new HashMap<>();
    }

    /**
     * @param User
     */
    public void addUser(User User) {
        users.put(User.getId(), User);
    }

    
    /** 
     * @param user
     * @return boolean
     */
    public boolean removeUser(User user) {
        return users.remove(user.getId(), user);
    }

    
    /** 
     * @return List<User>
     */
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        for (User user : users.values()) {
            result.add(castToAppropriateType(user));
        }
        return result;
    }

    
    /** 
     * @param id
     * @return Optional<User>
     */
    public Optional<User> getUserById(String id) {
        return Optional.ofNullable(castToAppropriateType(users.get(id)));
    }

    
    /** 
     * @param username
     * @return Optional<User>
     */
    public Optional<User> getUserByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .map(UserRepository::castToAppropriateType)
                .findFirst();
    }

    
    /** 
     * @param user
     * @return User
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
