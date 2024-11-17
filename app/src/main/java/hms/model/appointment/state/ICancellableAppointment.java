package hms.model.appointment.state;

import hms.model.appointment.Appointment;

public interface ICancellableAppointment {
    void cancel(Appointment appointment);
}
