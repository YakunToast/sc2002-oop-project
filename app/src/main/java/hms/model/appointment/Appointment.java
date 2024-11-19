package hms.model.appointment;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import hms.model.appointment.state.CancelledState;
import hms.model.appointment.state.CompletedState;
import hms.model.appointment.state.ConfirmedState;
import hms.model.appointment.state.FreeState;
import hms.model.appointment.state.IAppointmentState;
import hms.model.appointment.state.ICancellableAppointment;
import hms.model.appointment.state.ICompletableAppointment;
import hms.model.appointment.state.IConfirmableAppointment;
import hms.model.appointment.state.IFreeableAppointment;
import hms.model.appointment.state.IPendableAppointment;
import hms.model.appointment.state.PendingState;
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
    private AppointmentOutcome outcome;
    private IAppointmentState state;

    public Appointment(Doctor doctor, LocalDateTime start, LocalDateTime end) {
        this.doctor = doctor;
        this.start = start;
        this.end = end;
        this.state = new FreeState();
    }

    public Appointment(Doctor doctor, LocalDateTime start, LocalDateTime end, Patient patient) {
        this(doctor, start, end);
        this.patient = patient;
        this.state = new PendingState();
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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
                .append(start != null ? start.format(formatter) : "Not Scheduled")
                .append("\n")
                .append("End Time: ")
                .append(end != null ? end.format(formatter) : "Not Scheduled")
                .append("\n")
                .append("Status: ")
                .append(state != null ? state.toString() : "Unknown")
                .append("\n")
                .append("Outcome: ")
                .append(outcome != null ? outcome.getDescription() : "Not Determined")
                .append("\n");
        return sb.toString();
    }

    /**
     * @return String
     */
    public String toTerse() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format(
                "Appointment [%s] - Patient: %s, Doctor: %s, DateTime: %s, Status: %s, Outcome: %s",
                id,
                patient != null ? patient.getName() : "None",
                doctor != null ? doctor.getName() : "None",
                start != null ? start.format(formatter) : "Not Scheduled",
                end != null ? end.format(formatter) : "Not Scheduled",
                state,
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
     * @return IAppointmentState
     */
    public IAppointmentState getState() {
        return this.state;
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
        return this.state instanceof FreeState;
    }

    /**
     * @return boolean
     */
    public boolean isPending() {
        return this.state instanceof PendingState;
    }

    /**
     * @return boolean
     */
    public boolean isConfirmed() {
        return this.state instanceof ConfirmedState;
    }

    /**
     * @return boolean
     */
    public boolean isCancelled() {
        return this.state instanceof CancelledState;
    }

    /**
     * @return boolean
     */
    public boolean isCompleted() {
        return this.state instanceof CompletedState;
    }

    /**
     * @param state
     */
    public void setState(IAppointmentState state) {
        this.state = state;
    }
}
