package hms.model.appointment;

import java.io.Serializable;

public class AppointmentOutcome implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Appointment appointment;

    public AppointmentOutcome(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
