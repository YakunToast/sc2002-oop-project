package hms.model.appointment;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import hms.model.user.Doctor;
import hms.model.user.Patient;

// TODO: ISP LSP it by creating multiple classes like FreeAppointment, PendingAppointment, etc...
// and interfaces like IBookableAppointment, IFreeableAppointment, etc....
// Appointment class to manage doctor appointments
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID uuid;
    private Doctor doctor;
    private LocalDateTime start;
    private LocalDateTime end;
    private Patient patient;
    private AppointmentStatus status; // confirmed, canceled, completed
    private AppointmentOutcome outcome;

    public enum AppointmentStatus {
        FREE,
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }

    public Appointment(Doctor doctor, LocalDateTime start, LocalDateTime end) {
        this.uuid = UUID.randomUUID();
        this.doctor = doctor;
        this.start = start;
        this.end = end;
        this.status = AppointmentStatus.FREE;
    }

    public Appointment(UUID uuid, Doctor doctor, LocalDateTime start, LocalDateTime end) {
        this(doctor, start, end);
        this.uuid = uuid;
    }

    public Appointment(Doctor doctor, LocalDateTime start, LocalDateTime end, Patient patient) {
        this(doctor, start, end);
        this.patient = patient;
        this.status = AppointmentStatus.PENDING;
    }

    public Appointment(
            UUID uuid, Doctor doctor, LocalDateTime start, LocalDateTime end, Patient patient) {
        this(doctor, start, end, patient);
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Appointment Details\n")
                .append("-------------------\n")
                .append("Appointment ID: ")
                .append(uuid)
                .append("\n")
                .append("Patient Name: ")
                .append(patient != null ? patient.getName() : "None")
                .append("\n")
                .append("Doctor Name: ")
                .append(doctor != null ? doctor.getName() : "None")
                .append("\n")
                .append("Start Time: ")
                .append(start != null ? start.toString() : "Not Scheduled")
                .append("\n")
                .append("End Time: ")
                .append(end != null ? end.toString() : "Not Scheduled")
                .append("\n")
                .append("Status: ")
                .append(status != null ? status.toString() : "Unknown")
                .append("\n")
                .append("Outcome: ")
                .append(outcome != null ? outcome : "Not Determined")
                .append("\n");
        return sb.toString();
    }

    public String toTerse() {
        return String.format(
                "Appointment [%s] - Patient: %s, Doctor: %s, DateTime: %s, Status: %s, Outcome: %s",
                uuid != null ? uuid.toString() : "N/A",
                patient != null ? patient.getName() : "None",
                doctor != null ? doctor.getName() : "None",
                start != null ? start.toString() : "Not Scheduled",
                end != null ? end.toString() : "Not Scheduled",
                status != null ? status.toString() : "Unknown",
                outcome != null ? outcome : "Not Determined");
    }

    public void setStatus(AppointmentStatus newStatus) {
        this.status = newStatus;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setOutcome(AppointmentOutcome outcome) {
        this.outcome = outcome;
    }

    public AppointmentOutcome getOutcome() {
        return outcome;
    }

    public UUID getId() {
        return uuid;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean isFree() {
        return this.status == AppointmentStatus.FREE;
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
}
