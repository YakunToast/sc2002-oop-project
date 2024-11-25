package hms.model.appointment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import hms.model.user.Doctor;
import hms.repository.RepositoryManager;

/** Represents a schedule for a doctor, containing a list of appointments. */
public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Doctor doctor;
    private final List<Appointment> appointments;

    /**
     * Creates a new schedule for the specified doctor.
     *
     * @param doctor the doctor for whom the schedule is created
     */
    public Schedule(Doctor doctor) {
        this.appointments = new ArrayList<>();
        this.doctor = doctor;
    }

    /**
     * Retrieves the doctor for whom this schedule is created.
     *
     * @return the doctor associated with this schedule
     */
    public Doctor getDoctor() {
        return this.doctor;
    }

    /**
     * Retrieves a string representation of the schedule, listing all appointments.
     *
     * @return a string representation of the schedule
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Appointment appointment : appointments) {
            sb.append(appointment).append("\n");
        }
        return sb.toString();
    }

    /**
     * Retrieves the list of appointments scheduled for this doctor.
     *
     * @return a list of appointments
     */
    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    /**
     * Adds a new appointment to the schedule with the specified start and end times.
     *
     * @param startDateTime the start time of the appointment
     * @param endDateTime the end time of the appointment
     * @return the newly created appointment
     * @throws IllegalArgumentException if the start time is after the end time
     */
    public Appointment addAppointment(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        Appointment appointment = new Appointment(this.doctor, startDateTime, endDateTime);
        appointments.add(appointment);
        RepositoryManager.getInstance().getAppointmentRepository().addAppointment(appointment);
        return appointment;
    }

    /**
     * Adds appointments in one-hour intervals between the specified start and end times.
     *
     * @param startDateTime the start time of the appointment window
     * @param endDateTime the end time of the appointment window
     * @return a list of newly created appointments
     * @throws IllegalArgumentException if the start time is after the end time
     */
    public List<Appointment> addAppointmentHourly(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        List<Appointment> hourlyAppointments = new ArrayList<>();
        while (startDateTime.plusHours(1).isBefore(endDateTime)) {
            LocalDateTime nextHour = startDateTime.plusHours(1);
            Appointment appointment = addAppointment(startDateTime, nextHour);
            hourlyAppointments.add(appointment);
            startDateTime = nextHour;
        }
        // Add the last appointment to cover the remaining time
        if (startDateTime.isBefore(endDateTime)) {
            Appointment appointment = addAppointment(startDateTime, endDateTime);
            hourlyAppointments.add(appointment);
        }
        return hourlyAppointments;
    }

    /**
     * Adds availability time slots in one-hour windows over a specified date range.
     *
     * @param startDate the start date of the availability period
     * @param endDate the end date of the availability period
     * @param startTime the start time of the availability period
     * @param endTime the end time of the availability period
     * @return a list of newly created appointments representing the availability slots
     * @throws IllegalArgumentException if the start time is after the end time
     */
    public List<Appointment> addMultipleAppointmentDays(
            LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        List<Appointment> availabilitySlots = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            LocalDateTime startDateTime = LocalDateTime.of(currentDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(currentDate, endTime);
            availabilitySlots.addAll(addAppointmentHourly(startDateTime, endDateTime));
            currentDate = currentDate.plusDays(1);
        }
        return availabilitySlots;
    }

    /**
     * Removes the specified appointment from the schedule.
     *
     * @param ap the appointment to be removed
     */
    private void removeAppointment(Appointment ap) {
        appointments.remove(ap);
        RepositoryManager.getInstance().getAppointmentRepository().removeAppointment(ap);
    }
}
