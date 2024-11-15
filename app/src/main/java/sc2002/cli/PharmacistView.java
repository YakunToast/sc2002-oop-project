package sc2002.cli;

import java.util.Scanner;

import sc2002.model.role.Pharmacist;

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
        }
    }
}
