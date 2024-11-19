package hms.view;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import hms.controller.PatientController;
import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.model.medication.Medication;
import hms.model.record.MedicalRecord;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.repository.RepositoryManager;

/**
 * The PatientView class interacts with the user interface for a patient, providing methods to
 * perform operations such as viewing medical records, scheduling appointments, etc., using the
 * PatientController.
 */
public class PatientView {
    private final PatientController pc;

    /**
     * Initializes a new instance of the PatientView class for the specified patient.
     *
     * @param patient the specified patient to view
     */
    public PatientView(Patient patient) {
        this.pc = new PatientController(patient);
    }

    /**
     * Starts the view for a patient, providing a menu for user interactions.
     *
     * @param sc the scanner object for user input
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

            // Save after every option return
            RepositoryManager.getInstance().save();
        }
    }

    /** Displays the patient's medical record including personal details and past diagnoses. */
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

        for (int i = 0; i < mr.getPastDiagnoses().size(); i++) {
            System.out.printf(
                    " %d. Diagnosis: %s - Treatment Plan: %s%n",
                    (i + 1), mr.getPastDiagnoses().get(i), mr.getTreatments().get(i));
        }
        System.out.println("---------------------------");
    }

    /**
     * Updates the personal information of the patient such as email or phone number.
     *
     * @param sc the scanner object for user input
     */
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

    /**
     * Displays the available appointment slots for the patient by doctor.
     *
     * @param sc the scanner object for user input
     */
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

    /**
     * Allows the patient to select an appointment slot from the available ones.
     *
     * @param sc the scanner object for user input
     * @return the selected appointment
     */
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

    /**
     * Schedules an appointment for the patient.
     *
     * @param sc the scanner object for user input
     */
    void scheduleAppointment(Scanner sc) {
        Appointment selectedAppointment = chooseAppointment(sc);
        pc.scheduleAppointment(selectedAppointment);
        System.out.println("Appointment scheduled successfully. Please wait for confirmation.");
    }

    /**
     * Reschedules an existing appointment for the patient.
     *
     * @param sc the scanner object for user input
     */
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

    /**
     * Cancels an existing appointment for the patient.
     *
     * @param sc the scanner object for user input
     */
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

    /**
     * Displays the scheduled appointments for the patient.
     *
     * @param sc the scanner object for user input
     */
    void viewScheduledAppointments(Scanner sc) {
        List<Appointment> appts = pc.getPersonalAppointments();

        for (int i = 0; i < appts.size(); i++) {
            System.out.println(i + 1 + ". " + appts.get(i).toString());
        }
    }

    /**
     * Displays the past appointment outcomes for the patient, including descriptions and
     * prescriptions.
     *
     * @param sc the scanner object for user input
     */
    void viewPastAppointmentOutcomes(Scanner sc) {
        List<AppointmentOutcome> apptOutcomes = pc.getPastAppointmentOutcomes();
        String desc;
        Map<Medication, Integer> medications;
        for (int i = 0; i < apptOutcomes.size(); i++) {
            desc = apptOutcomes.get(i).getDescription();
            medications = apptOutcomes.get(i).getPrescription().get().getMedications();
            System.out.println("Appointment Outcome " + (i + 1) + ": ");
            System.out.println("Description: " + desc);
            if (medications != null) {
                System.out.println("Prescription:");
                for (Map.Entry<Medication, Integer> e : medications.entrySet()) {
                    System.out.println(
                            e.getKey().getName()
                                    + ", "
                                    + e.getKey().getDescription()
                                    + ", "
                                    + e.getKey().getDosageInstructions()
                                    + ", "
                                    + "Amount given: "
                                    + e.getValue());
                }
            }
        }
    }
}
