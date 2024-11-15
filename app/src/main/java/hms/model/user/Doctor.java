package hms.model.user;

import hms.model.schedule.Schedule;

public class Doctor extends Staff {
    private Schedule schedule;

    public Doctor(String id, String username, String firstName, String lastName, String password, String email, String phoneNumber) {
        super(id, username, firstName, lastName, password, email, phoneNumber, UserRole.DOCTOR);

        this.schedule = new Schedule();
    }

    public Schedule getSchedule() {
        return schedule;
    }
}
