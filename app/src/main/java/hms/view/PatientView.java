package hms.view;

import java.util.HashMap;
import java.util.Scanner;

import hms.controller.PatientController;
import hms.model.record.MedicalRecord;
import hms.model.schedule.TimeSlot;
import hms.model.user.Doctor;
import hms.model.user.Patient;

public class PatientView {
    private final PatientController pc;

    public PatientView(Patient patient) {
        this.pc = new PatientController(patient);
    }

    void start(Scanner sc) {
        while (true) {
            System.out.println("What would you like to do next?");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline left-over

            switch (option) {
                case 1 -> viewMedicalRecord();
                case 2 -> updatePersonalInformation(sc);
                case 3 -> viewAvailableSlots(sc);
                case 4 -> scheduleAppointment(sc);
                case 5 -> rescheduleAppointment(sc);
                case 6 -> cancelAppointment(sc);
                case 7 -> viewScheduledAppointments(sc);
                case 8 -> viewPastAppointmentOutcomes(sc);
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    void viewMedicalRecord() {
        // Retrieve the patient's medical record
        MedicalRecord mr = pc.getMedicalRecord();

        // Display the patient's medical record with required details
        System.out.println("Patient's Medical Record:");
        System.out.println("---------------------------");
        System.out.printf("Patient ID: %s%n", pc.getPatient().getId());
        System.out.printf("Name: %s %s%n", pc.getPatient().getFirstName(), pc.getPatient().getLastName());
        System.out.printf("Date of Birth: %s%n", pc.getPatient().getDateOfBirth());
        System.out.printf("Gender: %s%n", pc.getPatient().getGender());
        System.out.println("Contact Information:");
        System.out.printf(" Phone: %s%n", pc.getPatient().getPhoneNumber());
        System.out.printf(" Email: %s%n", pc.getPatient().getEmail());
        System.out.printf("Blood Type: %s%n", pc.getPatient().getBloodType());
        System.out.println("Past Diagnoses and Treatments:");
        // TODO:
        // for (int i = 0; i < mr.getPastDiagnoses().size(); i++) {
        // System.out.printf(" %d. %s - %s%n", (i + 1),
        // mr.getPastDiagnoses().get(i).getDiagnosis(),
        // mr.getPastDiagnoses().get(i).getTreatment());
        // }
        System.out.println("---------------------------");
    }

    void updatePersonalInformation(Scanner sc) {
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
                    pc.setEmail(newEmail);
                    break;
                case 2:
                    System.out.print("Contact: ");
                    String newPhoneNumber = sc.nextLine();
                    pc.setPhoneNumber(newPhoneNumber);
                    break;
                case 0:
                    return;
                default:
            }
        }
    }

    void viewAvailableSlots(Scanner sc) {
        for (Doctor doctor : pc.getDoctors()) {
            System.out.println("Doctor: " + doctor.getName() + " " + doctor.getSchedule());
        }
    }

    void scheduleAppointment(Scanner sc) {
        int index = 1;
        HashMap<Integer, TimeSlot> availableSlotsMap = new HashMap<>();

        // Build indexed list of available timeslots
        for (Doctor doctor : pc.getDoctors()) {
            for (TimeSlot timeslot : doctor.getSchedule().getSlots()) {
                if (timeslot.isAvailable()) { // Check if the timeslot is available
                    availableSlotsMap.put(index, timeslot);
                    System.out.printf("%d. Doctor: %s, Time: %s%n", index, doctor.getName(), timeslot.getStart());
                    index++;
                }
            }
        }

        if (availableSlotsMap.isEmpty()) {
            System.out.println("No available timeslots at the moment. Please try again later.");
            return;
        }

        System.out.print("Choose a timeslot by entering the corresponding index: ");
        int chosenIndex = sc.nextInt();
        sc.nextLine(); // Consume newline left-over

        if (availableSlotsMap.containsKey(chosenIndex)) {
            TimeSlot selectedSlot = availableSlotsMap.get(chosenIndex);
            selectedSlot.setPending();
            System.out.println("Appointment scheduled successfully. Please wait for confirmation.");
        } else {
            System.out.println("Invalid selection. Please try again.");
        }
    }

    void rescheduleAppointment(Scanner sc) {

    }

    void cancelAppointment(Scanner sc) {

    }

    void viewScheduledAppointments(Scanner sc) {

    }

    void viewPastAppointmentOutcomes(Scanner sc) {

    }
}
