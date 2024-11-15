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
            System.out.println("2. View Appointments details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consume the newline character
        }
    }
}
