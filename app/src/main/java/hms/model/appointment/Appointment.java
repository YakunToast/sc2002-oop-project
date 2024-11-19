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

/** Class to manage doctor appointments. */
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Doctor doctor;
    private LocalDateTime start;
    private LocalDateTime end;
    private Patient patient;
    private AppointmentOutcome outcome;
    private IAppointmentState state;

    /**
     * Constructs an appointment with a doctor, start time, and end time.
     *
     * @param doctor The doctor associated with the appointment.
     * @param start The start time of the appointment.
     * @param end The end time of the appointment.
     */
    public Appointment(Doctor doctor, LocalDateTime start, LocalDateTime end) {
        this.doctor = doctor;
        this.start = start;
        this.end = end;
        this.state = new FreeState();
    }

    /**
     * Constructs an appointment with a doctor, start time, end time, and patient.
     *
     * @param doctor The doctor associated with the appointment.
     * @param start The start time of the appointment.
     * @param end The end time of the appointment.
     * @param patient The patient associated with the appointment.
     */
    public Appointment(Doctor doctor, LocalDateTime start, LocalDateTime end, Patient patient) {
        this(doctor, start, end);
        this.patient = patient;
        this.state = new PendingState();
    }

    /**
     * Retrieves a string representation of the appointment details.
     *
     * @return A string representation of the appointment.
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
     * Retrieves a concise string representation of the appointment details.
     *
     * @return A concise string representation of the appointment.
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

    /**
     * Confirms the appointment if it is in a confirmable state.
     *
     * @throws IllegalStateException if the appointment cannot be confirmed in the current state.
     */
    public void confirm() {
        if (state instanceof IConfirmableAppointment) {
            ((IConfirmableAppointment) state).confirm(this);
        } else {
            throw new IllegalStateException(
                    "Cannot confirm appointment in current state: " + state.getStatus());
        }
    }

    /**
     * Cancels the appointment if it is in a cancellable state.
     *
     * @throws IllegalStateException if the appointment cannot be cancelled in the current state.
     */
    public void cancel() {
        if (state instanceof ICancellableAppointment) {
            ((ICancellableAppointment) state).cancel(this);
        } else {
            throw new IllegalStateException(
                    "Cannot cancel appointment in current state: " + state.getStatus());
        }
    }

    /**
     * Completes the appointment if it is in a completable state.
     *
     * @throws IllegalStateException if the appointment cannot be completed in the current state.
     */
    public void complete() {
        if (state instanceof ICompletableAppointment) {
            ((ICompletableAppointment) state).complete(this);
        } else {
            throw new IllegalStateException(
                    "Cannot complete appointment in current state: " + state.getStatus());
        }
    }

    /**
     * Frees the appointment if it is in a freeable state.
     *
     * @throws IllegalStateException if the appointment cannot be freed in the current state.
     */
    public void free() {
        if (state instanceof IFreeableAppointment) {
            ((IFreeableAppointment) state).free(this);
        } else {
            throw new IllegalStateException(
                    "Cannot free appointment in current state: " + state.getStatus());
        }
    }

    /**
     * Puts the appointment in a pending state if it is in a pendable state.
     *
     * @throws IllegalStateException if the appointment cannot be pending in the current state.
     */
    public void pending() {
        if (state instanceof IPendableAppointment) {
            ((IPendableAppointment) state).pending(this);
        } else {
            throw new IllegalStateException(
                    "Cannot pend appointment in current state: " + state.getStatus());
        }
    }

    /**
     * Retrieves the current status of the appointment.
     *
     * @return The current status of the appointment.
     */
    public AppointmentStatus getStatus() {
        return ((IAppointmentState) state).getStatus();
    }

    /**
     * Retrieves the current state of the appointment.
     *
     * @return The current state of the appointment.
     */
    public IAppointmentState getState() {
        return this.state;
    }

    /**
     * Sets the outcome of the appointment.
     *
     * @param outcome The outcome of the appointment.
     */
    public void setOutcome(AppointmentOutcome outcome) {
        this.outcome = outcome;
    }

    /**
     * Retrieves the outcome of the appointment.
     *
     * @return The outcome of the appointment.
     */
    public AppointmentOutcome getOutcome() {
        return outcome;
    }

    /**
     * Sets the unique identifier for the appointment.
     *
     * @param id The unique identifier for the appointment.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the unique identifier for the appointment.
     *
     * @return The unique identifier for the appointment.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the patient associated with the appointment.
     *
     * @param patient The patient associated with the appointment.
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Retrieves the patient associated with the appointment.
     *
     * @return The patient associated with the appointment.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Sets the doctor associated with the appointment.
     *
     * @param doctor The doctor associated with the appointment.
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * Retrieves the doctor associated with the appointment.
     *
     * @return The doctor associated with the appointment.
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Sets the start time of the appointment.
     *
     * @param start The start time of the appointment.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Retrieves the start time of the appointment.
     *
     * @return The start time of the appointment.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets the end time of the appointment.
     *
     * @param end The end time of the appointment.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Retrieves the end time of the appointment.
     *
     * @return The end time of the appointment.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Checks if the appointment is in a free state.
     *
     * @return True if the appointment is in a free state, false otherwise.
     */
    public boolean isFree() {
        return this.state instanceof FreeState;
    }

    /**
     * Checks if the appointment is in a pending state.
     *
     * @return True if the appointment is in a pending state, false otherwise.
     */
    public boolean isPending() {
        return this.state instanceof PendingState;
    }

    /**
     * Checks if the appointment is in a confirmed state.
     *
     * @return True if the appointment is in a confirmed state, false otherwise.
     */
    public boolean isConfirmed() {
        return this.state instanceof ConfirmedState;
    }

    /**
     * Checks if the appointment is in a cancelled state.
     *
     * @return True if the appointment is in a cancelled state, false otherwise.
     */
    public boolean isCancelled() {
        return this.state instanceof CancelledState;
    }

    /**
     * Checks if the appointment is in a completed state.
     *
     * @return True if the appointment is in a completed state, false otherwise.
     */
    public boolean isCompleted() {
        return this.state instanceof CompletedState;
    }

    /**
     * Sets the state of the appointment.
     *
     * @param state The new state of the appointment.
     */
    public void setState(IAppointmentState state) {
        this.state = state;
    }
}
