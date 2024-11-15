package sc2002.cli;

import java.util.Scanner;

import sc2002.model.role.Doctor;

public class DoctorView {
    private Doctor doctor;

    public DoctorView(Doctor doctor) {
        this.doctor = doctor;
    }

    void start(Scanner sc) {
        while (true) {
            System.out.println("What would you like to do next?");
            System.out.println("1. View Patients Record");
            System.out.println("2. Update Personal Particulars");
            System.out.println("3. Schedule Appointment");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline left-over

            switch (option) {
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
            }
        }
    }
}
