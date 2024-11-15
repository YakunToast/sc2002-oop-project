package sc2002.cli;

import java.util.Scanner;

import sc2002.controller.Database;
import sc2002.model.role.Patient;
import sc2002.model.role.User;
import sc2002.model.role.UserRole;

public class MainView {
    public void start() {
        // Create a Scanner to read user input
        Scanner sc = new Scanner(System.in);

        // Loop forever
        while (true) {
            // Display login prompt
            System.out.println("Login");
            System.out.println("-----");
            System.out.print("Username: ");
            String username = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            // Get user
            User user = Database.getUserById(username);
            if (user == null) {
                user = Database.getUserByUsername(username);
                if (user == null) {
                    System.out.println("No such user!");
                    continue;
                }
            }

            // Authenticate user
            if (user.verifyPassword(password)) {
                UserRole ur = user.getRole();

                // TODO: use firstname lastname?
                System.out.println("Login successful! Welcome, " + ur + ": " + username + "!");

                // Open correct role
                switch (ur) {
                    case PATIENT:
                        // Assuming Patient is a subclass of User
                        if (user instanceof Patient) {
                            Patient patient = (Patient) user;
                            new PatientView(patient).start(sc);
                        } else {
                            System.out.println("Error: User role does not match any known views.");
                        }
                        break;
                    case ADMINISTRATOR:
                        break;
                    case PHARMACIST:
                        break;
                    case DOCTOR:
                        break;
                    default:
                        System.out.println("Error: User role does not match any known views.");

                }
            }
        }
    }
}
