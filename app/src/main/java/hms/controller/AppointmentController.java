package hms.controller;

import java.util.List;
import java.util.stream.Collectors;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.repository.AppointmentRepository;
import hms.repository.RepositoryManager;

/**
 * Manages patient appointments, including scheduling, rescheduling, and cancellation. Implements
 * appointment status tracking and validation of scheduling constraints.
 *
 * @author AMOS NG ZHENG JIE
 * @author GILBERT ADRIEL TANTOSO
 * @author KUO EUGENE
 * @author RESWARA ANARGYA DZAKIRULLAH
 * @author THEODORE AMADEO ARGASETYA ATMADJA
 * @version 1.0
 * @since 2024-11-19
 */
public class AppointmentController {
    private final Appointment appointment;

    private final AppointmentRepository appointmentRepository;

    public AppointmentController(Appointment appointment) {
        this.appointment = appointment;
        appointmentRepository = RepositoryManager.getInstance().getAppointmentRepository();
    }

    /** The methods to cancel an appointment Mark an appointment as cancelled */
    public void cancelAppointment() {
        // Mark as cancelled
        appointment.cancel();
    }

    /**
     * The methods to accept an appointment
     *
     * @return boolean
     */
    public boolean accept() {
        appointment.confirm();
        return true;
    }

    /**
     * The methods to decline an appointment
     *
     * @return boolean
     */
    public boolean decline() {
        appointment.cancel();
        return true;
    }

    /**
     * The methods to get appointment outcomes
     *
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
