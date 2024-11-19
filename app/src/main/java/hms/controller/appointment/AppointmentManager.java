package hms.controller.appointment;

import java.util.List;
import java.util.Optional;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentStatus;

public interface AppointmentManager extends AppointmentBase {
    List<Appointment> getAllAppointments();

    List<Appointment> getAllAppointmentsByStatus(AppointmentStatus as);

    Optional<Appointment> getAppointmentById(int id);
}
