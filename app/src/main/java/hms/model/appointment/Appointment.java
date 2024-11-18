package hms.model.appointment;

import java.io.Serializable;
import java.time.LocalDateTime;

import hms.model.appointment.state.FreeState;
import hms.model.appointment.state.IAppointmentState;
import hms.model.appointment.state.ICancellableAppointment;
import hms.model.appointment.state.ICompletableAppointment;
import hms.model.appointment.state.IConfirmableAppointment;
import hms.model.appointment.state.IFreeableAppointment;
import hms.model.appointment.state.IPendableAppointment;
import hms.model.user.Doctor;
import hms.model.user.Patient;

// Appointment class to manage doctor appointments
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Doctor doctor;
    private LocalDateTime start;
    private LocalDateTime end;
    private Patient patient;
    private AppointmentStatus status; // confirmed, canceled, completed
    private AppointmentOutcome outcome;
    private IAppointmentState state;

    public Appointment(Doctor doctor, LocalDateTime start, LocalDateTime end) {
        this.doctor = doctor;
        this.start = start;
        this.end = end;
        this.status = AppointmentStatus.FREE;
        this.state = new FreeState();
    }

    public Appointment(Doctor doctor, LocalDateTime start, LocalDateTime end, Patient patient) {
        this(doctor, start, end);
        this.patient = patient;
        this.status = AppointmentStatus.PENDING;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Appointment Details\n")
                .append("-------------------\n")
                .append("Appointment ID: ")
                .append(id)
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

    
    /** 
     * @return String
     */
    public String toTerse() {
        return String.format(
                "Appointment [%s] - Patient: %s, Doctor: %s, DateTime: %s, Status: %s, Outcome: %s",
                id,
                patient != null ? patient.getName() : "None",
                doctor != null ? doctor.getName() : "None",
                start != null ? start.toString() : "Not Scheduled",
                end != null ? end.toString() : "Not Scheduled",
                status != null ? status.toString() : "Unknown",
                outcome != null ? outcome : "Not Determined");
    }

    public void confirm() {
        if (state instanceof IConfirmableAppointment) {
            ((IConfirmableAppointment) state).confirm(this);
        } else {
            throw new IllegalStateException(
                    "Cannot confirm appointment in current state: " + state.getStatus());
        }
    }

    public void cancel() {
        if (state instanceof ICancellableAppointment) {
            ((ICancellableAppointment) state).cancel(this);
        } else {
            throw new IllegalStateException(
                    "Cannot cancel appointment in current state: " + state.getStatus());
        }
    }

    public void complete() {
        if (state instanceof ICompletableAppointment) {
            ((ICompletableAppointment) state).complete(this);
        } else {
            throw new IllegalStateException(
                    "Cannot complete appointment in current state: " + state.getStatus());
        }
    }

    public void free() {
        if (state instanceof IFreeableAppointment) {
            ((IFreeableAppointment) state).free(this);
        } else {
            throw new IllegalStateException(
                    "Cannot free appointment in current state: " + state.getStatus());
        }
    }

    public void pending() {
        if (state instanceof IPendableAppointment) {
            ((IPendableAppointment) state).pending(this);
        } else {
            throw new IllegalStateException(
                    "Cannot pend appointment in current state: " + state.getStatus());
        }
    }

    
    /** 
     * @param newStatus
     */
    public void setStatus(AppointmentStatus newStatus) {
        this.status = newStatus;
    }

    
    /** 
     * @return AppointmentStatus
     */
    public AppointmentStatus getStatus() {
        return status;
    }

    
    /** 
     * @param outcome
     */
    public void setOutcome(AppointmentOutcome outcome) {
        this.outcome = outcome;
    }

    
    /** 
     * @return AppointmentOutcome
     */
    public AppointmentOutcome getOutcome() {
        return outcome;
    }

    /**
     * @return void
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return int
     */
    public int getId() {
        return id;
    }

    
    /** 
     * @param patient
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    
    /** 
     * @return Patient
     */
    public Patient getPatient() {
        return patient;
    }

    
    /** 
     * @param doctor
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    
    /** 
     * @return Doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    
    /** 
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    
    /** 
     * @return LocalDateTime
     */
    public LocalDateTime getStart() {
        return start;
    }

    
    /** 
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    
    /** 
     * @return LocalDateTime
     */
    public LocalDateTime getEnd() {
        return end;
    }

    
    /** 
     * @return boolean
     */
    public boolean isFree() {
        return this.status == AppointmentStatus.FREE;
    }

    
    /** 
     * @return boolean
     */
    public boolean isPending() {
        return this.status == AppointmentStatus.PENDING;
    }

    
    /** 
     * @return boolean
     */
    public boolean isConfirmed() {
        return this.status == AppointmentStatus.CONFIRMED;
    }

    
    /** 
     * @return boolean
     */
    public boolean isCancelled() {
        return this.status == AppointmentStatus.CANCELLED;
    }

    
    /** 
     * @return boolean
     */
    public boolean isCompleted() {
        return this.status == AppointmentStatus.COMPLETED;
    }

    
    /** 
     * @param state
     */
    public void setState(IAppointmentState state) {
        this.state = state;
    }
}
