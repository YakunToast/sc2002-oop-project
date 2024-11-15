package hms.view;

import java.util.Scanner;

import hms.controller.Database;
import hms.model.user.Administrator;
import hms.model.user.BaseUser;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.Pharmacist;
import hms.model.user.UserRole;

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
            BaseUser user = Database.getUserById(username);
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
                        if (user instanceof Patient) {
                            Patient patient = (Patient) user;
                            new PatientView(patient).start(sc);
                        } else {
                            System.out.println("Error: User role does not match any known views.");
                        }
                        break;
                    case DOCTOR:
                        if (user instanceof Doctor) {
                            Doctor doctor = (Doctor) user;
                            new DoctorView(doctor).start(sc);
                        } else {
                            System.out.println("Error: User role does not match any known views.");
                        }
                        break;
                    case PHARMACIST:
                        if (user instanceof Pharmacist) {
                            Pharmacist pharmacist = (Pharmacist) user;
                            new PharmacistView(pharmacist).start(sc);
                        } else {
                            System.out.println("Error: User role does not match any known views.");
                        }
                        break;
                    case ADMINISTRATOR:
                        if (user instanceof Doctor) {
                            Administrator administrator = (Administrator) user;
                            new AdministratorView(administrator).start(sc);
                        } else {
                            System.out.println("Error: User role does not match any known views.");
                        }
                        break;
                    default:
                        System.out.println("Error: User role does not match any known views.");

                }
            }
        }
    }
}
