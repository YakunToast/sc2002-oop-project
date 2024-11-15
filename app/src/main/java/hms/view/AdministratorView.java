package hms.view;

import java.util.Scanner;

import hms.model.user.Administrator;

public class AdministratorView {
    private Administrator administrator;

    public AdministratorView(Administrator administrator) {
        this.administrator = administrator;
    }

    void start(Scanner sc) {
        while (true) {
            System.out.println("What would you like to do next?");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consume

            switch (option) {
                case 1 -> viewAndManageHospitalStaff(sc);
                case 2 -> viewAppointmentDetails(sc);
                case 3 -> viewAndManageMedicationInventory(sc);
                case 4 -> approveReplenishmentRequests(sc);
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void viewAndManageHospitalStaff(Scanner sc) {
        // Implementation for viewing and managing hospital staff
        System.out.println("View and Manage Hospital Staff");
    }

    private void viewAppointmentDetails(Scanner sc) {
        // Implementation for viewing appointment details
        System.out.println("View Appointments Details");
    }

    private void viewAndManageMedicationInventory(Scanner sc) {
        // Implementation for viewing and managing medication inventory
        System.out.println("View and Manage Medication Inventory");
    }

    private void approveReplenishmentRequests(Scanner sc) {
        // Implementation for approving replenishment requests
        System.out.println("Approve Replenishment Requests");
    }
}
