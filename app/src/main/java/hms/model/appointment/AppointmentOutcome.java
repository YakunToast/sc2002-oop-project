package hms.model.appointment;

import java.io.Serializable;
import java.util.Optional;

import hms.model.medication.Prescription;

public class AppointmentOutcome implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Appointment appointment;
    private final String description;
    private final Prescription prescription;

    public AppointmentOutcome(Appointment ap, String d, Prescription pr) {
        this.appointment = ap;
        this.description = d;
        this.prescription = pr;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public String getDescription() {
        return description;
    }

    public Optional<Prescription> getPrescription() {
        return Optional.of(this.prescription);
    }
}
