package hms.view;

import java.util.Scanner;

import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.Pharmacist;
import hms.model.user.User;
import hms.model.user.UserRole;
import hms.repository.RepositoryManager;

/**
 * The MainView class provides the main user interface for the Hospital Management System (HMS). It
 * handles user authentication, password management, and role-specific menu navigation.
 */
public class MainView {
    private static final String DEFAULT_PASSWORD = "password";
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    /**
     * Starts the main user interface loop. It displays a welcome banner, handles user choice,
     * processes login and registration, and navigates to the appropriate role-specific menu.
     */
    public void start() {
        // Create a Scanner to read user input
        Scanner sc = new Scanner(System.in);

        // Loop forever
        while (true) {
            displayWelcomeBanner();

            // Handle user choice (login or register)
            int choice = handleUserChoice(sc);
            switch (choice) {
                case 1 -> {
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
                case 2 -> {
                    // Handle patient registration
                    registerPatient(sc);
                }
                default -> System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    /** Displays the welcome banner for the Hospital Management System. */
    private void displayWelcomeBanner() {
        System.out.println("\n========================================");
        System.out.println("   Hospital Management System (HMS)");
        System.out.println("========================================");
        System.out.println("Please choose an option:\n");
    }

    /**
     * Handles user choice between login and registration.
     *
     * @param sc the Scanner object for reading user input
     * @return the user's choice (1 for login, 2 for registration)
     */
    private int handleUserChoice(Scanner sc) {
        System.out.println("1. Log in");
        System.out.println("2. Register as a patient");
        System.out.print("Enter your choice (1 or 2): ");
        return sc.nextInt();
    }

    /**
     * Handles the user login process.
     *
     * @param sc the Scanner object for reading user input
     * @return the authenticated User object, or null if login fails
     */
    private User handleLogin(Scanner sc) {
        sc.nextLine(); // Consume newline character left by nextInt()

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
     * Handles the password change for a user on their first login.
     *
     * @param sc the Scanner object for reading user input
     * @param user the User object for whom the password change is being handled
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

        // Save after credential change
        RepositoryManager.getInstance().save();
    }

    /**
     * Validates the provided password to ensure it meets the system requirements.
     *
     * @param password the password string to validate
     * @return true if the password is valid, false otherwise
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
     * Displays the role-specific menu for the authenticated user.
     *
     * @param sc the Scanner object for reading user input
     * @param user the authenticated User object
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

    /**
     * Handles the patient registration process.
     *
     * @param sc the Scanner object for reading user input
     */
    private void registerPatient(Scanner sc) {
        System.out.println("\nRegister as a new patient:");

        System.out.print("Enter your first name: ");
        String firstName = sc.nextLine().trim();
        System.out.print("Enter your last name: ");
        String lastName = sc.nextLine().trim();
        System.out.print("Enter a username: ");
        String username = sc.nextLine().trim();
        System.out.print("Enter your email address: ");
        String email = sc.nextLine().trim();
        System.out.print("Enter your phone number: ");
        String phoneNumber = sc.nextLine().trim();

        // Check if username already exists
        if (RepositoryManager.getInstance()
                .getUserRepository()
                .getUserByUsername(username)
                .isPresent()) {
            System.out.println("\nUsername already exists. Please try a different username.");
            return;
        }

        while (true) {
            System.out.print("Enter a password: ");
            String password = sc.nextLine().trim();
            System.out.print("Confirm password: ");
            String confirmPassword = sc.nextLine().trim();

            if (password.equals(confirmPassword)) {
                if (isValidPassword(password)) {
                    User newPatient =
                            new Patient(
                                    username,
                                    username,
                                    firstName,
                                    lastName,
                                    password,
                                    email,
                                    phoneNumber);
                    RepositoryManager.getInstance().getUserRepository().addUser(newPatient);
                    System.out.println("Registration successful. Please log in to continue.\n");
                    RepositoryManager.getInstance().save();
                    return;
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
}
