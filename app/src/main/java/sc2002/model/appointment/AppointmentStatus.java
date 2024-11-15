package sc2002.model.appointment;

import java.time.LocalDateTime;

public enum AppointmentStatus {
    PENDING,
    CONFIRMED,
    CANCELLED,
    COMPLETED
}


// Appointment class to manage doctor appointments
class Appointment {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private LocalDateTime dateTime;
    private String status; // confirmed, canceled, completed
    private String outcome;

    public Appointment(int appointmentId, int patientId, int doctorId, LocalDateTime dateTime) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.status = "pending";
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    // Getters
    public String getAppointmentId() { return appointmentId; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getStatus() { return status; }
    public String getOutcome() { return outcome; }
}
