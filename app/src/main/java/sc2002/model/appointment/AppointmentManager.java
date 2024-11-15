package sc2002.model.appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import sc2002.model.role.Doctor;
import sc2002.model.role.Patient;

// AppointmentManager class to handle appointment-related operations
public class AppointmentManager {
    private Map<Doctor, List<LocalDateTime>> doctorAvailability;
    private List<Appointment> appointments;

    public AppointmentManager() {
        this.doctorAvailability = new HashMap<>();
        this.appointments = new ArrayList<>();
    }

    public List<LocalDateTime> getAvailableSlots(Doctor doctor) {
        return doctorAvailability.getOrDefault(doctor, new ArrayList<>());
    }

    public Appointment scheduleAppointment(Patient patient, Doctor doctor, LocalDateTime dateTime) {
        if (!isSlotAvailable(doctor, dateTime)) {
            throw new IllegalArgumentException("Selected time slot is not available");
        }

        UUID appointmentId = UUID.randomUUID();
        Appointment appointment = new Appointment(appointmentId, patient, doctor, dateTime);
        appointments.add(appointment);
        removeAvailableSlot(doctor, dateTime);
        return appointment;
    }

    public void rescheduleAppointment(UUID appointmentId, LocalDateTime newDateTime) {
        Appointment appointment = findAppointment(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment not found");
        }

        Doctor doctor = appointment.getDoctor();

        if (!isSlotAvailable(doctor, newDateTime)) {
            throw new IllegalArgumentException("New time slot is not available");
        }

        // Add old slot back to availability
        addAvailableSlot(doctor, appointment.getDateTime());
        // Remove new slot from availability
        removeAvailableSlot(doctor, newDateTime);
        // Update appointment
        appointment = new Appointment(appointmentId, appointment.getPatient(),
                doctor, newDateTime);
    }

    public void cancelAppointment(UUID appointmentId) {
        Appointment appointment = findAppointment(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment not found");
        }

        Doctor doctor = appointment.getDoctor();

        appointment.updateStatus(AppointmentStatus.CANCELLED);
        addAvailableSlot(doctor, appointment.getDateTime());
    }

    private Appointment findAppointment(UUID appointmentId) {
        return appointments.stream()
                .filter(a -> a.getAppointmentId().equals(appointmentId))
                .findFirst()
                .orElse(null);
    }

    private boolean isSlotAvailable(Doctor doctor, LocalDateTime dateTime) {
        List<LocalDateTime> availableSlots = doctorAvailability.get(doctor);
        return availableSlots != null && availableSlots.contains(dateTime);
    }

    private void removeAvailableSlot(Doctor doctor, LocalDateTime dateTime) {
        doctorAvailability.get(doctor).remove(dateTime);
    }

    private void addAvailableSlot(Doctor doctor, LocalDateTime dateTime) {
        doctorAvailability.computeIfAbsent(doctor, k -> new ArrayList<>()).add(dateTime);
    }
}
