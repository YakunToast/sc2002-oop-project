package hms.model.appointment.state;

import hms.model.appointment.Appointment;

public interface IConfirmableAppointment {
    void confirm(Appointment appointment);
}
