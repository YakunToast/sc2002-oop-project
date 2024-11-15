package sc2002.cli;

import java.util.Scanner;

import sc2002.model.MedicalRecord;
import sc2002.model.role.Patient;

public class PatientView {
    private Patient patient;

    public PatientView(Patient patient) {
        this.patient = patient;
    }

    void start(Scanner sc) {
        while (true) {
            System.out.println("What would you like to do next?");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Particulars");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    getMedicalRecord();
                    break;
                case 2:
                    updatePersonalParticulars(sc);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
            }
        }
    }

    void getMedicalRecord() {
        // Retrieve the patient's medical record
        MedicalRecord mr = patient.getMedicalRecord();

        // Display the patient's medical record with required details
        System.out.println("Patient's Medical Record:");
        System.out.println("---------------------------");
        System.out.printf("Patient ID: %s%n", patient.getId());
        System.out.printf("Name: %s %s%n", patient.getFirstName(), patient.getLastName());
        System.out.printf("Date of Birth: %s%n", mr.getDateOfBirth());
        System.out.printf("Gender: %s%n", mr.getGender());
        System.out.println("Contact Information:");
        System.out.printf(" Phone: %s%n", patient.getPhoneNumber());
        System.out.printf(" Email: %s%n", patient.getEmail());
        System.out.printf("Blood Type: %s%n", mr.getBloodType());
        System.out.println("Past Diagnoses and Treatments:");
        // TODO:
        // for (int i = 0; i < mr.getPastDiagnoses().size(); i++) {
        // System.out.printf(" %d. %s - %s%n", (i + 1),
        // mr.getPastDiagnoses().get(i).getDiagnosis(),
        // mr.getPastDiagnoses().get(i).getTreatment());
        // }
        System.out.println("---------------------------");
    }

    void updatePersonalParticulars(Scanner sc) {
        while (true) {
            System.out.println("What would you like to do next?");
            System.out.println("1. Update email address");
            System.out.println("2. Update contact number");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    System.out.print("Email: ");
                    String newEmail = sc.nextLine();
                    patient.setEmail(newEmail);
                    break;
                case 2:
                    System.out.print("Contact: ");
                    String newPhoneNumber = sc.nextLine();
                    patient.setPhoneNumber(newPhoneNumber);
                    break;
                case 0:
                    return;
                default:
            }
        }
    }
}
