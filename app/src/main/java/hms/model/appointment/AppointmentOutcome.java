package hms.model.appointment;

import java.io.Serializable;
import java.util.Optional;

import hms.model.medication.Prescription;

/** Represents the outcome of an appointment within the HMS system. */
public class AppointmentOutcome implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Appointment appointment;
    private final String description;
    private final Prescription prescription;

    /**
     * Constructs a new instance of AppointmentOutcome.
     *
     * @param ap the appointment for which the outcome is being recorded
     * @param d a description of the appointment outcome
     * @param pr a prescription associated with the appointment, if applicable
     */
    public AppointmentOutcome(Appointment ap, String d, Prescription pr) {
        this.appointment = ap;
        this.description = d;
        this.prescription = pr;
    }

    /**
     * Retrieves the appointment associated with this outcome.
     *
     * @return the appointment instance
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     * Retrieves the description of this appointment outcome.
     *
     * @return the description as a string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the prescription associated with this appointment outcome, if applicable.
     *
     * @return an Optional containing the prescription, or an empty Optional if no prescription was
     *     provided
     */
    public Optional<Prescription> getPrescription() {
        return Optional.ofNullable(this.prescription);
    }
}
