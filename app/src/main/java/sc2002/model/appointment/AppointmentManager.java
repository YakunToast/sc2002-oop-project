package sc2002.model.appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import sc2002.model.role.Doctor;

// AppointmentManager class to handle appointment-related operations
class AppointmentManager {
    private Map<String, List<LocalDateTime>> doctorAvailability;
    private List<Appointment> appointments;

    public AppointmentManager() {
        this.doctorAvailability = new HashMap<>();
        this.appointments = new ArrayList<>();
    }

    public List<LocalDateTime> getAvailableSlots(String doctorId) {
        return doctorAvailability.getOrDefault(doctorId, new ArrayList<>());
    }

    public Appointment scheduleAppointment(String patientId, String doctorId, LocalDateTime dateTime) {
        if (!isSlotAvailable(doctorId, dateTime)) {
            throw new IllegalArgumentException("Selected time slot is not available");
        }

        String appointmentId = generateAppointmentId();
        Appointment appointment = new Appointment(appointmentId, patientId, doctorId, dateTime);
        appointments.add(appointment);
        removeAvailableSlot(doctorId, dateTime);
        return appointment;
    }

    public void rescheduleAppointment(String appointmentId, LocalDateTime newDateTime) {
        Appointment appointment = findAppointment(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment not found");
        }

        Doctor doctor = appointment.getDoctor()

        if (!isSlotAvailable(doctor.getId(), newDateTime)) {
            throw new IllegalArgumentException("New time slot is not available");
        }

        // Add old slot back to availability
        addAvailableSlot(doctor.getId(), appointment.getDateTime());
        // Remove new slot from availability
        removeAvailableSlot(doctor.getId(), newDateTime);
        // Update appointment
        appointment = new Appointment(appointmentId, appointment.getPatientId(), 
                                   doctor.getId(), newDateTime);
    }

    public void cancelAppointment(String appointmentId) {
        Appointment appointment = findAppointment(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment not found");
        }

        appointment.updateStatus("canceled");
        addAvailableSlot(doctor.getId(), appointment.getDateTime());
    }

    private Appointment findAppointment(String appointmentId) {
        return appointments.stream()
                .filter(a -> a.getAppointmentId().equals(appointmentId))
                .findFirst()
                .orElse(null);
    }

    private boolean isSlotAvailable(String doctorId, LocalDateTime dateTime) {
        List<LocalDateTime> availableSlots = doctorAvailability.get(doctorId);
        return availableSlots != null && availableSlots.contains(dateTime);
    }

    private void removeAvailableSlot(String doctorId, LocalDateTime dateTime) {
        doctorAvailability.get(doctorId).remove(dateTime);
    }

    private void addAvailableSlot(String doctorId, LocalDateTime dateTime) {
        doctorAvailability.computeIfAbsent(doctorId, k -> new ArrayList<>()).add(dateTime);
    }

    private String generateAppointmentId() {
        return UUID.randomUUID().toString();
    }
}
