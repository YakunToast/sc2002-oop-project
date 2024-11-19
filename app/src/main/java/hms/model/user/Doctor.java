package hms.model.user;

import hms.model.appointment.Schedule;

public class Doctor extends Staff {
    /** Represents the schedule associated with the doctor. */
    private Schedule schedule;

    /**
     * Constructs a new Doctor object with the specified attributes.
     *
     * @param id the unique identifier for the doctor
     * @param username the username for the doctor's account
     * @param firstName the first name of the doctor
     * @param lastName the last name of the doctor
     * @param password the password for the doctor's account
     * @param email the email address of the doctor
     * @param phoneNumber the phone number of the doctor
     */
    public Doctor(
            String id,
            String username,
            String firstName,
            String lastName,
            String password,
            String email,
            String phoneNumber) {
        super(id, username, firstName, lastName, password, email, phoneNumber, UserRole.DOCTOR);

        this.schedule = new Schedule(this);
    }

    /**
     * Returns the schedule associated with the doctor.
     *
     * @return the schedule object for the doctor
     */
    public Schedule getSchedule() {
        return schedule;
    }
}
