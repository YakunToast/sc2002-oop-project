package hms.view;

import java.util.Scanner;

import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.Pharmacist;
import hms.model.user.User;
import hms.model.user.UserRole;
import hms.repository.RepositoryManager;

public class MainView {
    private static final String DEFAULT_PASSWORD = "password";
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    public void start() {
        // Create a Scanner to read user input
        Scanner sc = new Scanner(System.in);

        // Loop forever
        while (true) {
            displayWelcomeBanner();

            // Handle login
            User user = handleLogin(sc);
            if (user == null) {
                continue;
            }

            // Handle first-time login password change
            if (user.verifyPassword(DEFAULT_PASSWORD)) {
                handlePasswordChange(sc, user);
            }

            // Display role-specific menu
            handleUserMenu(sc, user);
        }
    }

    private void displayWelcomeBanner() {
        System.out.println("\n========================================");
        System.out.println("   Hospital Management System (HMS)");
        System.out.println("========================================");
        System.out.println("Please log in to continue\n");
    }

    /**
     * @param sc
     * @return User
     */
    private User handleLogin(Scanner sc) {
        int attempts = 0;
        while (attempts < MAX_LOGIN_ATTEMPTS) {
            System.out.print("Username: ");
            String username = sc.nextLine().trim();
            System.out.print("Password: ");
            String password = sc.nextLine().trim();

            // First try to find user by ID, then by username
            User user =
                    RepositoryManager.getInstance()
                            .getUserRepository()
                            .getUserById(username)
                            .orElse(null);

            if (user == null) {
                user =
                        RepositoryManager.getInstance()
                                .getUserRepository()
                                .getUserByUsername(username)
                                .orElse(null);
            }

            if (user != null && user.verifyPassword(password)) {
                return user;
            }

            attempts++;
            System.out.println(
                    "\nInvalid credentials! "
                            + (MAX_LOGIN_ATTEMPTS - attempts)
                            + " attempts remaining.");

            if (attempts < MAX_LOGIN_ATTEMPTS) {
                System.out.println("Please try again.\n");
            }
        }

        System.out.println("\nMaximum login attempts exceeded. Please try again later.");
        return null;
    }

    /**
     * @param sc
     * @param user
     */
    private void handlePasswordChange(Scanner sc, User user) {
        System.out.println("\nThis appears to be your first login. You must change your password.");

        while (true) {
            System.out.print("Enter new password: ");
            String newPassword = sc.nextLine().trim();
            System.out.print("Confirm new password: ");
            String confirmPassword = sc.nextLine().trim();

            if (newPassword.equals(confirmPassword)) {
                if (isValidPassword(newPassword)) {
                    user.setPassword(newPassword);
                    System.out.println("Password successfully changed!\n");
                    break;
                } else {
                    System.out.println(
                            "\n"
                                + "Password must be at least 8 characters long and contain at least"
                                + " one uppercase letter, one lowercase letter, and one number.");
                }
            } else {
                System.out.println("\nPasswords do not match. Please try again.");
            }
        }
    }

    /**
     * @param password
     * @return boolean
     */
    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least
        // one uppercase letter, one lowercase letter, and one number
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*\\d.*");
    }

    /**
     * @param sc
     * @param user
     */
    private void handleUserMenu(Scanner sc, User user) {
        UserRole role = user.getRole();
        System.out.println(
                "\nWelcome, " + role + " " + user.getFirstName() + " " + user.getLastName() + "!");

        try {
            switch (role) {
                case PATIENT -> {
                    if (user instanceof Patient) {
                        new PatientView((Patient) user).start(sc);
                    }
                }
                case DOCTOR -> {
                    if (user instanceof Doctor) {
                        new DoctorView((Doctor) user).start(sc);
                    }
                }
                case PHARMACIST -> {
                    if (user instanceof Pharmacist) {
                        new PharmacistView((Pharmacist) user).start(sc);
                    }
                }
                case ADMINISTRATOR -> {
                    if (user instanceof Administrator) {
                        new AdministratorView((Administrator) user).start(sc);
                    }
                }
                default -> throw new IllegalStateException("Unknown user role: " + role);
            }
        } catch (ClassCastException e) {
            System.out.println("Error: User role does not match the expected type.");
            System.out.println("Please contact system administrator for assistance.");
        }
    }
}
