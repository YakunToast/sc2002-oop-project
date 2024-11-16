package hms.controller.appointment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import hms.model.appointment.Appointment;

public interface AppointmentManager extends AppointmentBase {
    List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status);

    Optional<Appointment> getAppointmentById(UUID id);
}
