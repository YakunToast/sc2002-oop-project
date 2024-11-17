package hms.model.appointment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import hms.model.user.Doctor;
import hms.repository.RepositoryManager;

public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Doctor doctor;
    private List<Appointment> appointments;

    public Schedule(Doctor doctor) {
        this.appointments = new ArrayList<>();
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Appointment appointment : appointments) {
            sb.append(appointment).append("\n");
        }
        return sb.toString();
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    // addAppointments add appointments in one hour windows
    public void addAppointmentDay(LocalDate date, LocalTime start, LocalTime end) {
        LocalDateTime startDateTime = LocalDateTime.of(date, start);
        LocalDateTime endDateTime = LocalDateTime.of(date, end);

        while (startDateTime.isBefore(endDateTime)) {
            LocalDateTime nextHour = startDateTime.plusHours(1);

            Appointment appointment = new Appointment(this.doctor, startDateTime, nextHour);

            appointments.add(appointment);
            RepositoryManager.getInstance().getAppointmentRepository().addAppointment(appointment);

            startDateTime = nextHour;
        }
    }

    // Method to add availability timeslot in 1-hour windows over a date range
    public void addMultipleAppointmentDays(
            LocalDate startDate, LocalDate endDate, LocalTime start, LocalTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            addAppointmentDay(currentDate, start, end);
            currentDate = currentDate.plusDays(1);
        }
    }

    private void removeAppointment(Appointment ap) {
        appointments.remove(ap);
        RepositoryManager.getInstance().getAppointmentRepository().removeAppointment(ap);
    }

    // Method to remove availability timeslot by matching slots within a date and
    // time range
    public void removeAppointments(
            LocalDate startDate, LocalDate endDate, LocalTime start, LocalTime end) {
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            removeAppointment(currentDate, start, end);
            currentDate = currentDate.plusDays(1);
        }
    }
}
