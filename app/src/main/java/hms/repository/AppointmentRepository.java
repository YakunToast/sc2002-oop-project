package hms.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;

public class AppointmentRepository extends BaseRepository {

    private Map<UUID, Appointment> appointments;
    private Map<UUID, AppointmentOutcome> appointmentOutcomes;

    public AppointmentRepository() {
        appointments = new HashMap<>();
        appointmentOutcomes = new HashMap<>();
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

    public void addAppointmentOutcome(AppointmentOutcome appointmentOutcome) {
        appointmentOutcomes.put(appointmentOutcome.getAppointment().getId(), appointmentOutcome);
    }

    public boolean removeAppointmentOutcome(AppointmentOutcome appointmentOutcome) {
        return appointmentOutcomes.remove(
                appointmentOutcome.getAppointment().getId(), appointmentOutcome);
    }

    public List<AppointmentOutcome> getAllAppointmentOutcomes() {
        return new ArrayList<>(appointmentOutcomes.values());
    }

    public Optional<AppointmentOutcome> getAppointmentOutcomeById(UUID id) {
        return Optional.ofNullable(appointmentOutcomes.get(id));
    }
}
