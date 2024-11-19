package hms.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import hms.controller.appointment.AppointmentDoctor;
import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.model.appointment.Schedule;
import hms.model.medication.Prescription;
import hms.model.record.MedicalRecord;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.UserRole;
import hms.repository.RepositoryManager;

/**
 * This class serves as a controller for a doctor, handling various operations related to
 * appointments, medical records, and personal schedules.
 */
public class DoctorController implements AppointmentDoctor {
    private final Doctor doctor;

    /**
     * Constructs a new DoctorController with a specific doctor.
     *
     * @param doctor The doctor associated with this controller.
     */
    public DoctorController(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * Retrieves a list of all patients in the system.
     *
     * @return A list of all patients.
     */
    public List<Patient> getPatients() {
        return RepositoryManager.getInstance().getUserRepository().getAllUsers().stream()
                .filter(u -> u.getRole() == UserRole.PATIENT)
                .map(u -> (Patient) u)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of all appointments associated with the doctor.
     *
     * @return A list of all appointments.
     */
    public List<Appointment> getAppointments() {
        return RepositoryManager.getInstance()
                .getAppointmentRepository()
                .getAllAppointments()
                .stream()
                .filter(a -> a.getDoctor() == doctor)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of all free appointments associated with the doctor.
     *
     * @return A list of free appointments.
     */
    public List<Appointment> getFreeAppointments() {
        return this.getAppointments().stream()
                .filter(Appointment::isFree)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of all pending appointments associated with the doctor.
     *
     * @return A list of pending appointments.
     */
    public List<Appointment> getPendingAppointments() {
        return this.getAppointments().stream()
                .filter(Appointment::isPending)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of all confirmed appointments associated with the doctor.
     *
     * @return A list of confirmed appointments.
     */
    public List<Appointment> getConfirmedAppointments() {
        return this.getAppointments().stream()
                .filter(Appointment::isConfirmed)
                .collect(Collectors.toList());
    }

    /**
     * Adds a single appointment to the doctor's schedule.
     *
     * @param startDateTime The start date and time of the appointment.
     * @param endDateTime The end date and time of the appointment.
     * @return The newly created appointment.
     */
    public Appointment addAppointment(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return doctor.getSchedule().addAppointment(startDateTime, endDateTime);
    }

    /**
     * Adds hourly appointments to the doctor's schedule within a specified time range.
     *
     * @param startDateTime The start date and time of the first appointment.
     * @param endDateTime The end date and time of the last appointment.
     * @return A list of newly created appointments.
     */
    public List<Appointment> addAppointmentHourly(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return doctor.getSchedule().addAppointmentHourly(startDateTime, endDateTime);
    }

    /**
     * Adds multiple appointments to the doctor's schedule on a daily basis within a specified date
     * range and time range.
     *
     * @param startDate The start date for adding appointments.
     * @param endDate The end date for adding appointments.
     * @param startTime The start time for each appointment.
     * @param endTime The end time for each appointment.
     * @return A list of newly created appointments.
     */
    public List<Appointment> addMultipleAppointmentDays(
            LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return doctor.getSchedule()
                .addMultipleAppointmentDays(startDate, endDate, startTime, endTime);
    }

    /**
     * Retrieves the medical record for a specific patient.
     *
     * @param patient The patient whose medical record is to be retrieved.
     * @return The medical record of the patient.
     * @throws IllegalArgumentException if the patient is not associated with the doctor.
     */
    public MedicalRecord getPatientMedicalRecord(Patient patient) {
        // TODO: Allow doctors to get patient records they are not apart of?
        // if (patient.getAppointments().stream().allMatch(ap -> ap.getDoctor() != this.doctor)) {
        //     throw new IllegalArgumentException("Patient is not associated with this doctor.");
        // }
        return patient.getMedicalRecord();
    }

    /**
     * Updates the medical record of a specific patient with a new diagnosis and treatment.
     *
     * @param patient The patient whose medical record is to be updated.
     * @param diagnosis The new diagnosis to be added to the medical record.
     * @param treatment The new treatment to be added to the medical record.
     * @return true if the medical record was updated successfully, false otherwise.
     * @throws IllegalArgumentException if the patient is not associated with the doctor.
     */
    public boolean updatePatientMedicalRecord(Patient patient, String diagnosis, String treatment) {
        MedicalRecord mr = this.getPatientMedicalRecord(patient);
        if (mr == null) {
            return false;
        }

        mr.addDiagnosis(diagnosis);
        mr.addTreatment(treatment);
        return true;
    }

    /**
     * Retrieves the doctor's personal schedule.
     *
     * @return The doctor's schedule.
     */
    public Schedule getPersonalSchedule() {
        return this.doctor.getSchedule();
    }

    /**
     * Accepts an appointment on behalf of the doctor.
     *
     * @param ap The appointment to be accepted.
     * @return true if the appointment was accepted successfully, false otherwise.
     */
    @Override
    public boolean acceptAppointment(Appointment ap) {
        // Check doctor is appointment doctor
        if (ap.getDoctor() != doctor) {
            return false;
        }
        // Accept appointment
        return new AppointmentController(ap).accept();
    }

    /**
     * Declines an appointment on behalf of the doctor.
     *
     * @param ap The appointment to be declined.
     * @return true if the appointment was declined successfully, false otherwise.
     */
    @Override
    public boolean declineAppointment(Appointment ap) {
        // Check doctor is appointment doctor
        if (ap.getDoctor() != doctor) {
            return false;
        }
        // Cancel appointment
        return new AppointmentController(ap).decline();
    }

    /**
     * Adds an outcome to an appointment, including a description and a prescription.
     *
     * @param ap The appointment to which the outcome is to be added.
     * @param description The description of the appointment outcome.
     * @param pr The prescription associated with the appointment outcome.
     * @return true if the outcome was added successfully, false otherwise.
     */
    @Override
    public boolean addAppointmentOutcome(Appointment ap, String description, Prescription pr) {
        // Check doctor is appointment doctor
        if (ap.getDoctor() != doctor) {
            return false;
        }

        // Save appointment outcome
        AppointmentOutcome ao = new AppointmentOutcome(ap, description, pr);
        ap.setOutcome(ao);
        ap.complete();
        ap.getPatient().getMedicalRecord().setDoctor(doctor);
        return true;
    }

    /**
     * Retrieves the outcome of an appointment.
     *
     * @param ap The appointment whose outcome is to be retrieved.
     * @return The outcome of the appointment, or null if no outcome is present.
     */
    @Override
    public AppointmentOutcome getAppointmentOutcome(Appointment ap) {
        // Check doctor is appointment doctor
        if (ap.getDoctor() != doctor) {
            return null;
        }

        // Return outcome
        return ap.getOutcome();
    }
}
