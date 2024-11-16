package hms.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import hms.controller.DoctorController;
import hms.model.appointment.Appointment;
import hms.model.appointment.Appointment.AppointmentStatus;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.repository.RepositoryManager;

public class DoctorView {
    private final DoctorController dc;

    public DoctorView(Doctor doctor) {
        this.dc = new DoctorController(doctor);
    }

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

    void viewPatientMedicalRecord(Scanner sc, Patient patient) {
        System.out.println(patient.getMedicalRecord());
    }

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

    void viewSchedule(Scanner sc) {
        System.out.println("Doctor's Schedule:");
        System.out.println("==================");
        for (Appointment a : dc.getAppointments()) {
            System.out.println(a);
        }
    }

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
                dc.addSlots(startDate, endDate, start, end);
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

    void viewUpcomingAppointments(Scanner sc) {
        List<Appointment> appointments = dc.getConfirmedAppointments();
        System.out.println("Upcoming Appointments:");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

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
                if (appointment.getOutcome().equals("")) {
                    System.out.print("Enter outcome of the appointment: ");
                    String outcome = sc.nextLine();
                    // TODO: Handle!
                    // appointment.setOutcome(AppointmentOutcome);;
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
