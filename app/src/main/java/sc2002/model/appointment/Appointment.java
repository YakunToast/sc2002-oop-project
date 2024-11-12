package sc2002.model.appointment;

// Appointment class to manage doctor appointments
class Appointment {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDateTime dateTime;
    private String status; // confirmed, canceled, completed
    private String outcome;

    public Appointment(String appointmentId, String patientId, String doctorId, LocalDateTime dateTime) {
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
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getStatus() { return status; }
    public String getOutcome() { return outcome; }
}
