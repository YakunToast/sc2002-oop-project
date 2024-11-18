package hms.view;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import hms.controller.PatientController;
import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.model.record.MedicalRecord;
import hms.model.user.Doctor;
import hms.model.user.Patient;

public class PatientView {
    private final PatientController pc;

    public PatientView(Patient patient) {
        this.pc = new PatientController(patient);
    }

    /**
     * @param sc
     */
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
        System.out.printf(
                "Name: %s %s%n", pc.getPatient().getFirstName(), pc.getPatient().getLastName());
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
                case 1 -> {
                    System.out.print("Email: ");
                    String newEmail = sc.nextLine();
                    pc.setEmail(newEmail);
                }
                case 2 -> {
                    System.out.print("Contact: ");
                    String newPhoneNumber = sc.nextLine();
                    pc.setPhoneNumber(newPhoneNumber);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    // Unnecessary function?
    void viewAvailableSlots(Scanner sc) {
        // Build indexed list of available appointments
        HashMap<Doctor, List<Appointment>> availableSlotsByDoctor =
                pc.getAvailableAppointmentSlotsByDoctors();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        int index = 1;
        for (Doctor doctor : availableSlotsByDoctor.keySet()) {
            for (Appointment timeslot : availableSlotsByDoctor.get(doctor)) {
                System.out.printf(
                        "%d. Doctor: %s, Time: %s%n",
                        index, doctor.getName(), timeslot.getStart().format(formatter));
                index++;
            }
        }
    }

    Appointment chooseAppointment(Scanner sc) {
        int index = 1;
        HashMap<Integer, Appointment> availableSlotsMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Build indexed list of available appointments
        HashMap<Doctor, List<Appointment>> availableSlotsByDoctor =
                pc.getAvailableAppointmentSlotsByDoctors();
        for (Doctor doctor : availableSlotsByDoctor.keySet()) {
            for (Appointment timeslot : availableSlotsByDoctor.get(doctor)) {
                availableSlotsMap.put(index, timeslot);
                System.out.printf(
                        "%d. Doctor: %s, Time: %s%n",
                        index, doctor.getName(), timeslot.getStart().format(formatter));
                index++;
            }
        }

        if (availableSlotsMap.isEmpty()) {
            System.out.println(
                    "No available appointment slots at the moment. Please try again later.");
            return null;
        }

        while (true) {
            System.out.print("Choose a appointment slot by entering the corresponding index: ");
            int chosenIndex = sc.nextInt();
            sc.nextLine(); // Consume newline left-over

            if (availableSlotsMap.containsKey(chosenIndex)) {
                Appointment selectedSlot = availableSlotsMap.get(chosenIndex);
                return selectedSlot;
            } else {
                System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    void scheduleAppointment(Scanner sc) {
        Appointment selectedAppointment = chooseAppointment(sc);
        pc.scheduleAppointment(selectedAppointment);
        System.out.println("Appointment scheduled successfully. Please wait for confirmation.");
    }

    void rescheduleAppointment(Scanner sc) {
        List<Appointment> appts = pc.getPersonalAppointments();

        for (int i = 0; i < appts.size(); i++) {
            System.out.println(i + 1 + ". " + appts.get(i).toString());
        }

        while (true) {
            System.out.println("Choose an appointment to reschedule or 0 to return:");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 0) {
                break;
            } else if (choice >= 1 && choice <= appts.size()) {
                Appointment selectedAppointment = chooseAppointment(sc);
                pc.rescheduleAppointment(appts.get(choice - 1), selectedAppointment);
            }
        }
    }

    void cancelAppointment(Scanner sc) {
        List<Appointment> appts = pc.getPersonalAppointments();

        for (int i = 0; i < appts.size(); i++) {
            System.out.println(i + 1 + ". " + appts.get(i).toString());
        }

        while (true) {
            System.out.println("Choose an appointment to cancel or 0 to return:");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 0) {
                break;
            } else if (choice >= 1 && choice <= appts.size()) {
                pc.cancelAppointment(appts.get(choice - 1));
            }
        }
    }

    void viewScheduledAppointments(Scanner sc) {
        List<Appointment> appts = pc.getPersonalAppointments();

        for (int i = 0; i < appts.size(); i++) {
            System.out.println(i + 1 + ". " + appts.get(i).toString());
        }
    }

    void viewPastAppointmentOutcomes(Scanner sc) {
        List<AppointmentOutcome> apptOutcomes = pc.getPastAppointmentOutcomes();

        for (int i = 0; i < apptOutcomes.size(); i++) {
            System.out.println(i + 1 + ". " + apptOutcomes.get(i).getDescription()
                                            + apptOutcomes.get(i).getPrescription());
        }
    }
}
