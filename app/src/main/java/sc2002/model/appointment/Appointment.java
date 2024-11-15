package sc2002.model.appointment;

import java.time.LocalDateTime;

import sc2002.model.role.Doctor;
import sc2002.model.role.Patient;

// Appointment class to manage doctor appointments
class Appointment {
    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime dateTime;
    private AppointmentStatus status; // confirmed, canceled, completed
    private String outcome;

    public Appointment(int appointmentId, Patient patient, Doctor doctor, LocalDateTime dateTime) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.PENDING;
    }

    public void updateStatus(AppointmentStatus newStatus) {
        this.status = newStatus;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    // Getters
    public int getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public String getOutcome() {
        return outcome;
    }
}
