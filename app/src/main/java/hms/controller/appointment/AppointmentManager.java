package hms.controller.appointment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentStatus;

public interface AppointmentManager extends AppointmentBase {
    List<Appointment> getAppointmentsByStatus(AppointmentStatus status);

    Optional<Appointment> getAppointmentById(UUID id);

    List<Appointment> getAllAppointments();
}
