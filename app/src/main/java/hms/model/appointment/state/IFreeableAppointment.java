package hms.model.appointment.state;

import hms.model.appointment.Appointment;

public interface IFreeableAppointment {
    void free(Appointment appointment);
}
