package hms.model.appointment;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import hms.model.schedule.TimeSlot;
import hms.model.user.Doctor;
import hms.model.user.Patient;

// Appointment class to manage doctor appointments
public class Appointment implements Serializable {
    private UUID appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime dateTime;
    private List<TimeSlot> timeSlots;
    private AppointmentStatus status; // confirmed, canceled, completed
    private String outcome;

    public enum AppointmentStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }

    public Appointment(UUID appointmentId, Patient patient, Doctor doctor, LocalDateTime dateTime) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.PENDING;
    }

    public Appointment(Patient patient, Doctor doctor, LocalDateTime dateTime) {
        this(UUID.randomUUID(), patient, doctor, dateTime);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Appointment Details\n");
        sb.append("-------------------\n");
        sb.append("Appointment ID: ").append(appointmentId).append("\n");
        sb.append("Patient Name: ")
                .append(patient != null ? patient.getName() : "None")
                .append("\n");
        sb.append("Doctor Name: ").append(doctor != null ? doctor.getName() : "None").append("\n");
        sb.append("Date and Time: ")
                .append(dateTime != null ? dateTime.toString() : "Not Scheduled")
                .append("\n");
        sb.append("Status: ").append(status != null ? status.toString() : "Unknown").append("\n");
        sb.append("Outcome: ").append(outcome != null ? outcome : "Not Determined").append("\n");
        return sb.toString();
    }

    public String toTerse() {
        return String.format(
                "Appointment [%s] - Patient: %s, Doctor: %s, DateTime: %s, Status: %s, Outcome: %s",
                appointmentId != null ? appointmentId.toString() : "N/A",
                patient != null ? patient.getName() : "None",
                doctor != null ? doctor.getName() : "None",
                dateTime != null ? dateTime.toString() : "Not Scheduled",
                status != null ? status.toString() : "Unknown",
                outcome != null ? outcome : "Not Determined");
    }

    public void setTimeslots(List<TimeSlot> ts) {
        this.timeSlots = ts;
    }

    public List<TimeSlot> getTimeSlots() {
        return Collections.unmodifiableList(this.timeSlots);
    }

    public void setStatus(AppointmentStatus newStatus) {
        this.status = newStatus;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getOutcome() {
        return outcome;
    }

    public boolean isPending() {
        return this.status == AppointmentStatus.PENDING;
    }

    public boolean isConfirmed() {
        return this.status == AppointmentStatus.CONFIRMED;
    }

    public boolean isCancelled() {
        return this.status == AppointmentStatus.CANCELLED;
    }

    public boolean isCompleted() {
        return this.status == AppointmentStatus.COMPLETED;
    }

    // Getters
    public UUID getId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
