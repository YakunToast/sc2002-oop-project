package hms.controller;

import java.util.List;
import java.util.stream.Collectors;

import hms.model.appointment.Appointment;
import hms.model.appointment.Appointment.AppointmentStatus;
import hms.model.appointment.AppointmentOutcome;
import hms.model.schedule.TimeSlot;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.repository.RepositoryManager;

public class AppointmentController {
    private final Appointment appointment;

    public AppointmentController(Appointment ap) {
        this.appointment = ap;
    }

    public void cancelAppointment() {
        // Mark all slots appointment as free
        appointment.getTimeSlots().stream().forEach(ts -> ts.setAvailable());

        // Mark as cancelled
        appointment.setStatus(AppointmentStatus.CANCELLED);

        // Remove from database
        RepositoryManager.getInstance().getAppointmentRepository().removeAppointment(appointment);
    }

    public static Appointment createAppointment(Doctor d, Patient p, List<TimeSlot> ts) {
        // Sanity check that all timeslots belong to doctor
        if (ts.stream().anyMatch(t -> t.getOwner() != d)) {
            throw new IllegalArgumentException();
        }

        // Sort the timeslots by start time
        ts.sort((ts1, ts2) -> ts1.getStart().compareTo(ts2.getStart()));

        // Get the timeslot with the earliest start time
        TimeSlot earliestTimeSlot = ts.get(0);

        // Set the status of the earliest timeslot to PENDING
        earliestTimeSlot.setPending();

        // Create a new appointment with the earliest timeslot
        Appointment ap = new Appointment(p, d, earliestTimeSlot.getStart());
        ap.setTimeslots(ts);

        // Add to database
        RepositoryManager.getInstance().getAppointmentRepository().addAppointment(ap);

        // Return the newly created appointment
        return ap;
    }

    public boolean accept() {
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return true;
    }

    public boolean decline() {
        appointment.setStatus(AppointmentStatus.CANCELLED);
        return true;
    }

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
