package hms.controller;

import java.util.Optional;

import hms.model.user.User;
import hms.repository.RepositoryManager;
import hms.repository.UserRepository;

/**
 * Manages user authentication and role-based access control in the HMS. Handles login validation,
 * password management, and user session control.
 */
public class UserController {
    private final UserRepository userRepository;
    private User user;

    /**
     * Constructs a new UserController instance, initializing the UserRepository through the
     * RepositoryManager.
     */
    public UserController() {
        this.userRepository = RepositoryManager.getInstance().getUserRepository();
    }

    /**
     * Authenticates a user with their hospital ID or username and password. For first-time login,
     * users must use the default password "password".
     *
     * @param idOrUsername the hospital ID or username of the user
     * @param password the password of the user
     * @return an Optional containing the User object if authentication is successful, or an empty
     *     Optional otherwise
     * @throws IllegalArgumentException if the idOrUsername or password is null
     */
    public Optional<User> login(String idOrUsername, String password)
            throws IllegalArgumentException {
        if (idOrUsername == null || password == null) {
            throw new IllegalArgumentException("ID/Username and password must not be null.");
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
     * Sets the user attribute to the provided User object.
     *
     * @param user the User object to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Changes the user's password. This method should be called after the first login with the
     * default password.
     *
     * @param oldPassword the current password of the user
     * @param newPassword the new password to be set
     * @return true if the password change is successful, false otherwise
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
