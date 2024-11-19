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
 */
public class AppointmentController {
    private final Appointment appointment;
    private final AppointmentRepository appointmentRepository;

    /**
     * Constructs an instance of the {@code AppointmentController} class.
     *
     * @param appointment The appointment to be managed.
     */
    public AppointmentController(Appointment appointment) {
        this.appointment = appointment;
        this.appointmentRepository = RepositoryManager.getInstance().getAppointmentRepository();
    }

    /**
     * Cancels an appointment.
     *
     * @return {@code true} if the appointment is successfully cancelled, {@code false} otherwise.
     */
    public boolean decline() {
        appointment.cancel();
        return true;
    }

    /**
     * Confirms an appointment.
     *
     * @return {@code true} if the appointment is successfully confirmed, {@code false} otherwise.
     */
    public boolean accept() {
        appointment.confirm();
        return true;
    }

    /**
     * Frees an appointment.
     *
     * @return {@code true} if the appointment is successfully freed, {@code false} otherwise.
     */
    public boolean free() {
        appointment.free();
        return true;
    }

    /**
     * Retrieves a list of outcomes from all completed appointments.
     *
     * @return A list of {@code AppointmentOutcome} objects representing the outcomes of completed
     *     appointments.
     */
    public static List<AppointmentOutcome> getAppointmentOutcomes() {
        return RepositoryManager.getInstance()
                .getAppointmentRepository()
                .getAllAppointments()
                .stream()
                .filter(Appointment::isCompleted)
                .map(Appointment::getOutcome)
                .collect(Collectors.toList());
    }
}
