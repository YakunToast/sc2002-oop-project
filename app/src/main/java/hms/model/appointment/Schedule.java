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

    public Appointment addAppointment(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Appointment appointment = new Appointment(this.doctor, startDateTime, endDateTime);
        appointments.add(appointment);
        RepositoryManager.getInstance().getAppointmentRepository().addAppointment(appointment);
        return appointment;
    }

    // addAppointments add appointments in one hour windows
    public List<Appointment> addAppointmentHourly(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Appointment> appointments = new ArrayList<>();
        while (startDateTime.isBefore(endDateTime)) {
            LocalDateTime nextHour = startDateTime.plusHours(1);
            Appointment appointment = addAppointment(startDateTime, endDateTime);
            appointments.add(appointment);
            startDateTime = nextHour;
        }
        return appointments;
    }

    // Method to add availability timeslot in 1-hour windows over a date range
    public List<Appointment> addMultipleAppointmentDays(
            LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        LocalDate currentDate = startDate;
        List<Appointment> appointments = new ArrayList<>();
        while (!currentDate.isAfter(endDate)) {
            LocalDateTime startDateTime = LocalDateTime.of(currentDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(currentDate, endTime);
            appointments.addAll(addAppointmentHourly(startDateTime, endDateTime));
            currentDate = currentDate.plusDays(1);
        }
        return appointments;
    }

    private void removeAppointment(Appointment ap) {
        appointments.remove(ap);
        RepositoryManager.getInstance().getAppointmentRepository().removeAppointment(ap);
    }
}
