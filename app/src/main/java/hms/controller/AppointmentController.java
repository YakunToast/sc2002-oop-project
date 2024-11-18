package hms.controller;

import java.util.List;
import java.util.stream.Collectors;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.model.appointment.AppointmentStatus;
import hms.repository.AppointmentRepository;
import hms.repository.RepositoryManager;

public class AppointmentController {
    private final Appointment appointment;

    private final AppointmentRepository appointmentRepository;

    public AppointmentController(Appointment appointment) {
        this.appointment = appointment;
        appointmentRepository = RepositoryManager.getInstance().getAppointmentRepository();
    }

    public void cancelAppointment() {
        // Mark as cancelled
        appointment.setStatus(AppointmentStatus.CANCELLED);
    }

    /**
     * @return boolean
     */
    public boolean accept() {
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return true;
    }

    /**
     * @return boolean
     */
    public boolean decline() {
        appointment.setStatus(AppointmentStatus.CANCELLED);
        return true;
    }

    /**
     * @return List<AppointmentOutcome>
     */
    public static List<AppointmentOutcome> getAppointmentOutcomes() {
        return RepositoryManager.getInstance()
                .getAppointmentRepository()
                .getAllAppointments()
                .stream()
                .filter(ap -> ap.isCompleted())
                .map(ap -> ap.getOutcome())
                .collect(Collectors.toList());
    }
}
