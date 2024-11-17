package hms.model.appointment.state;

import hms.model.appointment.Appointment;

public interface ICompletableAppointment {
    void complete(Appointment appointment);
}
