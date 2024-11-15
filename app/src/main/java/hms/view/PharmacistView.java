package hms.view;

import java.util.Scanner;

import hms.model.user.Pharmacist;

public class PharmacistView {
    private Pharmacist pharmacist;

    public PharmacistView(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }

    void start(Scanner sc) {
        while (true) {
            System.out.println("What would you like to do next?");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consume

            switch (option) {
                case 1 -> viewAppointmentOutcomeRecords(sc);
                case 2 -> updatePrescriptionStatus(sc);
                case 3 -> viewMedicationInventory(sc);
                case 4 -> submitReplenishmentRequest(sc);
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    void viewAppointmentOutcomeRecords(Scanner sc) {

    }

    void updatePrescriptionStatus(Scanner sc) {

    }

    void viewMedicationInventory(Scanner sc) {

    }

    void submitReplenishmentRequest(Scanner sc) {

    }
}
