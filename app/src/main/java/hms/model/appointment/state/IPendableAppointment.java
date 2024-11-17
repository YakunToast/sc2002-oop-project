package hms.model.appointment.state;

import hms.model.appointment.Appointment;

public interface IPendableAppointment {
    void pending(Appointment appointment);
}
