package hms.controller.appointment;

import java.util.List;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;

public interface AppointmentBase {
    List<Appointment> getAppointments();

    AppointmentOutcome getAppointmentOutcome(Appointment ap);
}
