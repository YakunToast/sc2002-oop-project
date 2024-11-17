package hms.controller;

import java.util.Optional;

import hms.model.user.User;
import hms.repository.RepositoryManager;
import hms.repository.UserRepository;

public class UserController {
    private final UserRepository userRepository;
    private User user;

    public UserController() {
        this.userRepository = RepositoryManager.getInstance().getUserRepository();
    }

    public Optional<User> login(String idOrUsername, String password) {
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

    public void setUser(User user) {
        this.user = user;
    }

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
