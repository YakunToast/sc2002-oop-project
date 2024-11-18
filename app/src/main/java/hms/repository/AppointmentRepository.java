package hms.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import hms.model.appointment.Appointment;

public class AppointmentRepository extends BaseRepository {
    private Map<Integer, Appointment> appointments;

    public AppointmentRepository() {
        appointments = new HashMap<>();
    }

    public int getNextId() {
        return appointments.isEmpty()
                ? 1
                : (int) appointments.keySet().stream().max(Integer::compareTo).get() + 1;
    }

    /**
     * @param appointment
     */
    public void addAppointment(Appointment appointment) {
        int idx = getNextId();
        appointment.setId(idx);
        appointments.put(getNextId(), appointment);
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
    public Optional<Appointment> getAppointmentById(int id) {
        return Optional.ofNullable(appointments.get(id));
    }
}
