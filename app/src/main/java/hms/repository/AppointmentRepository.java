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

    /**
     * @param appointment
     */
    public void addAppointment(Appointment appointment) {
        appointments.put(appointment.getId(), appointment);
    }

    
    /** 
     * @param appointment
     * @return boolean
     */
    public boolean removeAppointment(Appointment appointment) {
        return appointments.remove(appointment.getId(), appointment);
    }

    
    /** 
     * @return List<Appointment>
     */
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }

    
    /** 
     * @param id
     * @return Optional<Appointment>
     */
    public Optional<Appointment> getAppointmentById(UUID id) {
        return Optional.ofNullable(appointments.get(id));
    }
}
