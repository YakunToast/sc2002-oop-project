package hms.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import hms.model.appointment.Appointment;

/** Repository class for managing appointments. */
public class AppointmentRepository extends BaseRepository {
    private Map<Integer, Appointment> appointments;

    /** Constructs a new AppointmentRepository with an empty map of appointments. */
    public AppointmentRepository() {
        appointments = new HashMap<>();
    }

    /**
     * Returns the next ID to be used for a new appointment.
     *
     * @return The next ID.
     */
    public int getNextId() {
        return appointments.isEmpty()
                ? 1
                : (int) appointments.keySet().stream().max(Integer::compareTo).get() + 1;
    }

    /**
     * Adds a new appointment to the repository.
     *
     * @param appointment The appointment to add.
     */
    public void addAppointment(Appointment appointment) {
        int idx = getNextId();
        appointment.setId(idx);
        appointments.put(idx, appointment);
    }

    /**
     * Removes an existing appointment from the repository.
     *
     * @param appointment The appointment to remove.
     * @return {@code true} if the appointment was removed, {@code false} otherwise.
     */
    public boolean removeAppointment(Appointment appointment) {
        return appointments.remove(appointment.getId(), appointment);
    }

    /**
     * Returns a list of all appointments.
     *
     * @return A list of all appointments.
     */
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id The ID of the appointment to retrieve.
     * @return An Optional containing the appointment if found, or an empty Optional if not found.
     */
    public Optional<Appointment> getAppointmentById(int id) {
        return Optional.ofNullable(appointments.get(id));
    }
}
