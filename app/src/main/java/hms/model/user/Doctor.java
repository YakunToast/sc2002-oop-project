package hms.model.user;

import java.util.List;

import hms.controller.Database;
import hms.model.appointment.Appointment;
import hms.model.schedule.Schedule;

public class Doctor extends Staff {
    private Schedule schedule;

    public Doctor(String id, String username, String firstName, String lastName, String password, String email, String phoneNumber) {
        super(id, username, firstName, lastName, password, email, phoneNumber, UserRole.DOCTOR);

        this.schedule = new Schedule();
    }

    public void printAppointments() {
        List<Appointment> appointments = Database.getAppointmentsOfDoctor(this);
        for (Appointment appointment : appointments) {
            System.out.println(appointment.toTerse());
        }
    }

    public Schedule getSchedule() {
        return schedule;
    }
}
