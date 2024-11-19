package hms.controller;

import java.util.Optional;

import hms.model.user.User;
import hms.repository.RepositoryManager;
import hms.repository.UserRepository;
/**
 * Manages user authentication and role-based access control in the HMS.
 * Handles login validation, password management, and user session control.
 * 
 * @author AMOS NG ZHENG JIE
 * @author GILBERT ADRIEL TANTOSO
 * @author KUO EUGENE
 * @author RESWARA ANARGYA DZAKIRULLAH
 * @author THEODORE AMADEO ARGASETYA ATMADJA
 * @version 1.0
 * @since 2024-11-19
 */
public class UserController {
    private final UserRepository userRepository;
    private User user;

    public UserController() {
        this.userRepository = RepositoryManager.getInstance().getUserRepository();
    }
    /**
     * Authenticates a user with their hospital ID and password.
     * For first-time login, users must use the default password "password".
     *
     * @param idOrUsername
     * @param password
     * @return Optional<User>
     * @throws IllegalArgumentException
     */
    public Optional<User> login(String idOrUsername, String password)
            throws IllegalArgumentException {
        if (idOrUsername == null || password == null) {
            throw new IllegalArgumentException();
        }

        Optional<User> optUser =
                userRepository
                        .getUserById(idOrUsername)
                        .or(() -> userRepository.getUserByUsername(idOrUsername))
                        .filter(u -> u.verifyPassword(password));
        if (optUser.isPresent()) {
            this.user = optUser.get();
        }

        return optUser;
    }

    
    /** 
     * The methods to set the user attiribute
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Changes a user's password. Required after first login with default password.
     *
     * @param oldPassword
     * @param newPassword
     * @return boolean
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        if (user == null) {
            return false;
        }
        if (!user.verifyPassword(oldPassword)) {
            return false;
        }
        user.setPassword(newPassword);
        return true;
    }
}
