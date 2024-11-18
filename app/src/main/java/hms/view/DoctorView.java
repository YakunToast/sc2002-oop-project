package hms.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import hms.controller.DoctorController;
import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.model.appointment.AppointmentStatus;
import hms.model.medication.Medication;
import hms.model.medication.Prescription;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.repository.RepositoryManager;

public class DoctorView {
    private final DoctorController dc;

    public DoctorView(Doctor doctor) {
        this.dc = new DoctorController(doctor);
    }

    /**
     * @param sc
     */
    void start(Scanner sc) {
        while (true) {
            System.out.println("What would you like to do next?");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline left-over

            Patient patient;
            switch (option) {
                case 1 -> {
                    patient = getPatientChoice(sc);
                    if (patient != null) {
                        viewPatientMedicalRecord(sc, patient);
                    }
                }
                case 2 -> {
                    patient = getPatientChoice(sc);
                    if (patient != null) {
                        updatePatientMedicalRecord(sc, patient);
                    }
                }
                case 3 -> viewSchedule(sc);
                case 4 -> setAppointmentAvailability(sc);
                case 5 -> acceptOrDeclineAppointments(sc);
                case 6 -> viewUpcomingAppointments(sc);
                case 7 -> recordAppointmentOutcome(sc);
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    void printPatients() {
        List<Patient> patients = dc.getPatients();
        System.out.println("Patients");
        System.out.println("========");
        for (Patient patient : patients) {
            System.out.println(
                    "ID: "
                            + patient.getId()
                            + ", First Name: "
                            + patient.getFirstName()
                            + ", Last Name: "
                            + patient.getLastName());
        }
        System.out.println("========");
    }

    /**
     * @param sc
     * @return Patient
     */
    Patient getPatientChoice(Scanner sc) {
        printPatients();
        System.out.print("Choose a patient ID: ");
        String patientId = sc.nextLine();
        Patient patient =
                (Patient)
                        RepositoryManager.getInstance()
                                .getUserRepository()
                                .getUserById(patientId)
                                .orElseThrow();
        if (patient == null) {
            System.out.println("Invalid! Try again!");
        }
        return patient;
    }

    /**
     * @param sc
     * @param patient
     */
    void viewPatientMedicalRecord(Scanner sc, Patient patient) {
        System.out.println(patient.getMedicalRecord());
    }

    /**
     * @param sc
     * @param patient
     */
    void updatePatientMedicalRecord(Scanner sc, Patient patient) {
        while (true) {
            System.out.println("What would you like to do next?");
            System.out.println("1. Update date of birth");
            System.out.println("2. Update gender");
            System.out.println("3. Update blood type");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline left-over

            switch (option) {
                case 1 -> {
                    System.out.print("Enter new date of birth (YYYY-MM-DD): ");
                    String newDateOfBirth = sc.nextLine();
                    patient.setDateOfBirth(newDateOfBirth);
                }
                case 2 -> {
                    System.out.print("Enter new gender (M/F/Other): ");
                    String newGender = sc.nextLine();
                    patient.setGender(newGender);
                }
                case 3 -> {
                    System.out.print("Enter new blood type: ");
                    String newBloodType = sc.nextLine();
                    patient.setBloodType(newBloodType);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    /**
     * @param sc
     */
    void viewSchedule(Scanner sc) {
        System.out.println("Doctor's Schedule:");
        System.out.println("==================");
        for (Appointment a : dc.getAppointments()) {
            System.out.println(a);
        }
    }

    /**
     * @param sc
     */
    void setAppointmentAvailability(Scanner sc) {
        System.out.println("Enter available date ranges and times in the format: ");
        System.out.println("YYYY-MM-DD & HH:MM (24-hour format)");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:MM");

        while (true) {
            try {
                // Start date
                System.out.println("Enter start date or type 'done' to finish: ");
                System.out.print("Start Date: ");
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("done")) {
                    break;
                }
                LocalDate startDate = LocalDate.parse(input.trim(), dateFormatter);

                // End date
                System.out.println("Enter end date or type 'done' to finish: ");
                System.out.print("End Date: ");
                input = sc.nextLine();
                if (input.equalsIgnoreCase("done")) {
                    break;
                }
                LocalDate endDate = LocalDate.parse(input.trim(), dateFormatter);

                // Sanity check
                if (!startDate.isBefore(endDate) && !startDate.isEqual(endDate)) {
                    throw new IllegalArgumentException("Start date must be before end date.");
                }

                // Start time
                System.out.println("Enter start time or type 'done' to finish: ");
                System.out.print("Start Time: ");
                input = sc.nextLine();
                if (input.equalsIgnoreCase("done")) {
                    break;
                }
                LocalTime start = LocalTime.parse(input.trim(), timeFormatter);

                // End time
                System.out.println("Enter End time or type 'done' to finish: ");
                System.out.print("End Time: ");
                input = sc.nextLine();
                if (input.equalsIgnoreCase("done")) {
                    break;
                }
                LocalTime end = LocalTime.parse(input.trim(), timeFormatter);

                // Sanity check
                if (!start.isBefore(end)) {
                    throw new IllegalArgumentException("Start time must be before end time.");
                }

                // Add availability
                dc.addMultipleAppointmentDays(startDate, endDate, start, end);
                System.out.println(
                        "Availability added: "
                                + startDate
                                + " to "
                                + endDate
                                + " from "
                                + start
                                + " to "
                                + end);

            } catch (DateTimeParseException | IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
        System.out.println("All availabilities updated successfully.");
    }

    /**
     * @param sc
     */
    void acceptOrDeclineAppointments(Scanner sc) {
        List<Appointment> requests = dc.getPendingAppointments();
        if (requests.isEmpty()) {
            System.out.println("No pending appointment requests.");
            return;
        }

        System.out.println("Pending Appointment Requests:");
        for (int i = 0; i < requests.size(); i++) {
            System.out.println(i + 1 + ". " + requests.get(i));
        }
        System.out.println("0. Return");

        while (true) {
            System.out.print("Choose an appointment to handle or 0 to return: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline left-over

            if (choice == 0) {
                break;
            } else if (choice >= 1 && choice <= requests.size()) {
                Appointment request = requests.get(choice - 1);
                System.out.print("Accept (A) or Decline (D): ");
                String decision = sc.nextLine();
                if (decision.equalsIgnoreCase("A")) {
                    request.setStatus(AppointmentStatus.CONFIRMED);
                    System.out.println("Appointment accepted.");
                } else if (decision.equalsIgnoreCase("D")) {
                    request.setStatus(AppointmentStatus.CANCELLED);
                    System.out.println("Appointment declined.");
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * @param sc
     */
    void viewUpcomingAppointments(Scanner sc) {
        List<Appointment> appointments = dc.getConfirmedAppointments();
        System.out.println("Upcoming Appointments:");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    /**
     * @param sc
     */
    void recordAppointmentOutcome(Scanner sc) {
        List<Appointment> pastAppointments = dc.getConfirmedAppointments();

        if (pastAppointments.isEmpty()) {
            System.out.println("No confirmed appointments to record outcomes for.");
            return;
        }

        System.out.println("Confirmed Appointments:");
        for (int i = 0; i < pastAppointments.size(); i++) {
            System.out.println(i + 1 + ". " + pastAppointments.get(i));
        }
        System.out.println("0. Return");

        while (true) {
            System.out.print("Choose an appointment to handle or 0 to return: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline left-over

            if (choice == 0) {
                break;
            } else if (choice >= 1 && choice <= pastAppointments.size()) {
                Appointment appointment = pastAppointments.get(choice - 1);
                if (appointment.getOutcome() == null) {
                    System.out.print("Enter description of appointment outcome: ");
                    String desc = sc.nextLine();

                    System.out.println(
                            "Enter 0 if no medication required, otherwise press any key to"
                                + " continue.");
                    String needMedication = sc.nextLine();

                    Map<Medication, Integer> medications = new HashMap<>();
                    AppointmentOutcome outcome;

                    if (!needMedication.equals("0")) {
                        while (true) {
                            System.out.println("Enter medication name: ");
                            String medName = sc.nextLine();
                            System.out.println("Enter medication description: ");
                            String medDesc = sc.nextLine();
                            System.out.println("Enter dosage instructions: ");
                            String medDosage = sc.nextLine();
                            System.out.println("Enter amount to be prescribed: ");
                            int dosageQuantity = sc.nextInt();
                            sc.nextLine();

                            Medication newMed = new Medication(medName, medDesc, medDosage);
                            medications.put(newMed, dosageQuantity);

                            System.out.println(
                                    "Enter 0 to return, press any key to prescribe more.");
                            needMedication = sc.nextLine();

                            if (needMedication.equals("0")) {
                                break;
                            }
                        }

                        Prescription pres = new Prescription(medications);
                        dc.addAppointmentOutcome(appointment, desc, pres);
                    } else {
                        dc.addAppointmentOutcome(appointment, desc, null);
                    }

                    System.out.println("Outcome recorded successfully.");
                } else {
                    System.out.println("Outcome for this appointment has already been recorded.");
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
