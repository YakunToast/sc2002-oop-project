package hms.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import hms.controller.Database;
import hms.model.appointment.Appointment;
import hms.model.appointment.Appointment.AppointmentStatus;
import hms.model.user.Doctor;
import hms.model.user.Patient;

public class DoctorView {
    private Doctor doctor;

    public DoctorView(Doctor doctor) {
        this.doctor = doctor;
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
                case 1:
                    patient = getPatientChoice(sc);
                    if (patient != null) {
                        viewPatientMedicalRecord(sc, patient);
                    }
                    break;
                case 2:
                    patient = getPatientChoice(sc);
                    if (patient != null) {
                        updatePatientMedicalRecord(sc, patient);
                    }
                    break;
                case 3:
                    viewSchedule(sc);
                    break;
                case 4:
                    setAppointmentAvailability(sc);
                    break;
                case 5:
                    acceptOrDeclineAppointments(sc);
                    break;
                case 6:
                    viewUpcomingAppointments(sc);
                    break;
                case 7:
                    recordAppointmentOutcome(sc);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    void printPatients() {
        List<Patient> patients = Database.getPatientsOfDoctor(this.doctor);
        System.out.println("Patients");
        System.out.println("========");
        for (Patient patient : patients) {
            System.out.println("ID: " + patient.getId() +
                    ", First Name: " + patient.getFirstName() +
                    ", Last Name: " + patient.getLastName());
        }
        System.out.println("========");
    }

    Patient getPatientChoice(Scanner sc) {
        printPatients();
        System.out.print("Choose a patient ID: ");
        String patientId = sc.nextLine();
        Patient patient = Database.getPatient(patientId);
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
                case 1:
                    System.out.print("Enter new date of birth (YYYY-MM-DD): ");
                    String newDateOfBirth = sc.nextLine();
                    patient.setDateOfBirth(newDateOfBirth);
                    break;
                case 2:
                    System.out.print("Enter new gender (M/F/Other): ");
                    String newGender = sc.nextLine();
                    patient.setGender(newGender);
                    break;
                case 3:
                    System.out.print("Enter new blood type: ");
                    String newBloodType = sc.nextLine();
                    patient.setBloodType(newBloodType);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    void viewSchedule(Scanner sc) {
        System.out.println("Doctor's Schedule:");
        doctor.printAppointments();
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
                doctor.getSchedule().add(startDate, endDate, start, end);
                System.out.println("Availability added: " + startDate + " to " + endDate + " from " + start + " to " + end);

            } catch (DateTimeParseException | IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
        System.out.println("All availabilities updated successfully.");
    }

    void acceptOrDeclineAppointments(Scanner sc) {
        // Assume we have a method that can fetch appointment requests
        List<Appointment> requests = Database.getAppointmentsOfDoctor(doctor);
        for (Appointment request : requests) {
            System.out.println("Appointment Request:");
            System.out.println(request);
            System.out.print("Accept (A) or Decline (D): ");
            String decision = sc.nextLine();
            if (decision.equalsIgnoreCase("A")) {
                request.setStatus(AppointmentStatus.CONFIRMED);
                System.out.println("Appointment accepted.");
            } else if (decision.equalsIgnoreCase("D")) {
                request.setStatus(AppointmentStatus.CANCELLED);
                System.out.println("Appointment declined.");
            } else {
                System.out.println("Invalid choice. Skipping request.");
            }
        }
    }

    void viewUpcomingAppointments(Scanner sc) {
        List<Appointment> appointments = Database.getAppointmentsOfDoctor(doctor);
        System.out.println("Upcoming Appointments:");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    void recordAppointmentOutcome(Scanner sc) {
        List<Appointment> pastAppointments = Database.getAppointmentsOfDoctor(doctor);
        for (Appointment appointment : pastAppointments) {
            if (appointment.getStatus() == AppointmentStatus.CONFIRMED && !appointment.getOutcome().equals("")) {
                System.out.println("Appointment Details:");
                System.out.println(appointment);
                System.out.print("Enter outcome of the appointment: ");
                String outcome = sc.nextLine();

                System.out.println("Outcome recorded successfully.");
            }
        }
    }
}
