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

    
    /** 
     * @param appointmentOutcome
     */
    public void addAppointmentOutcome(AppointmentOutcome appointmentOutcome) {
        appointmentOutcomes.put(appointmentOutcome.getAppointment().getId(), appointmentOutcome);
    }

    
    /** 
     * @param appointmentOutcome
     * @return boolean
     */
    public boolean removeAppointmentOutcome(AppointmentOutcome appointmentOutcome) {
        return appointmentOutcomes.remove(
                appointmentOutcome.getAppointment().getId(), appointmentOutcome);
    }

    
    /** 
     * @return List<AppointmentOutcome>
     */
    public List<AppointmentOutcome> getAllAppointmentOutcomes() {
        return new ArrayList<>(appointmentOutcomes.values());
    }

    
    /** 
     * @param id
     * @return Optional<AppointmentOutcome>
     */
    public Optional<AppointmentOutcome> getAppointmentOutcomeById(UUID id) {
        return Optional.ofNullable(appointmentOutcomes.get(id));
    }
}
