package hms.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import hms.model.appointment.Appointment;

public class AppointmentRepository extends BaseRepository {

    private Map<UUID, Appointment> appointments;

    public AppointmentRepository() {
        appointments = new HashMap<>();
    }

    public void addAppointment(Appointment appointment) {
        appointments.put(appointment.getId(), appointment);
    }

    public boolean removeAppointment(Appointment appointment) {
        return appointments.remove(appointment.getId(), appointment);
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }

    public Optional<Appointment> getAppointmentById(UUID id) {
        return Optional.ofNullable(appointments.get(id));
    }
}
