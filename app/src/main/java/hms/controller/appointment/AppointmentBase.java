package hms.controller.appointment;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;

public interface AppointmentBase {
    AppointmentOutcome getAppointmentOutcome(Appointment ap);
}
