package hms.model.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import hms.model.appointment.Appointment;
import hms.model.record.MedicalRecord;

/**
 * Represents a patient in the hospital management system. Manages patient operations, appointments,
 * and medical records.
 */
public class Patient extends User {
    private MedicalRecord medicalRecord;
    private List<Appointment> appointments;

    private String dateOfBirth;
    private Gender gender;
    private String bloodType;

    /** Enumeration representing the gender of the patient. */
    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    /**
     * Constructs a new Patient object.
     *
     * @param id the unique identifier of the patient
     * @param username the username of the patient
     * @param firstName the first name of the patient
     * @param lastName the last name of the patient
     * @param password the password of the patient
     * @param email the email address of the patient
     * @param phoneNumber the phone number of the patient
     */
    public Patient(
            String id,
            String username,
            String firstName,
            String lastName,
            String password,
            String email,
            String phoneNumber) {
        super(id, username, firstName, lastName, password, email, phoneNumber, UserRole.PATIENT);

        this.medicalRecord = new MedicalRecord(this);
        this.appointments = new ArrayList<>();
    }

    /**
     * Constructs a new Patient object with additional personal information.
     *
     * @param id the unique identifier of the patient
     * @param username the username of the patient
     * @param firstName the first name of the patient
     * @param lastName the last name of the patient
     * @param password the password of the patient
     * @param email the email address of the patient
     * @param phoneNumber the phone number of the patient
     * @param dateOfBirth the date of birth of the patient
     * @param gender the gender of the patient (M, F, or other)
     * @param bloodType the blood type of the patient
     */
    public Patient(
            String id,
            String username,
            String firstName,
            String lastName,
            String password,
            String email,
            String phoneNumber,
            String dateOfBirth,
            String gender,
            String bloodType) {
        this(id, username, firstName, lastName, password, email, phoneNumber);

        this.setDateOfBirth(dateOfBirth);
        this.setGender(gender);
        this.setBloodType(bloodType);
    }

    /**
     * Retrieves the medical record of the patient.
     *
     * @return the medical record of the patient
     */
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    /**
     * Updates the contact information of the patient.
     *
     * @param phone the new phone number of the patient
     * @param email the new email address of the patient
     */
    public void updateContactInformation(String phone, String email) {
        medicalRecord.updateContactInformation(phone, email);
    }

    /**
     * Retrieves the list of appointments of the patient.
     *
     * @return an unmodifiable list of appointments of the patient
     */
    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    /**
     * Adds a new appointment for the patient.
     *
     * @param appointment the appointment to be added
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Retrieves the date of birth of the patient.
     *
     * @return the date of birth of the patient
     */
    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    /**
     * Sets the date of birth of the patient.
     *
     * @param dateOfBirth the new date of birth of the patient
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Retrieves the gender of the patient.
     *
     * @return the gender of the patient
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * Sets the gender of the patient.
     *
     * @param gender the new gender of the patient (M, F, or other)
     */
    public void setGender(String gender) {
        if ("M".equals(gender)) {
            this.gender = Gender.MALE;
        } else if ("F".equals(gender)) {
            this.gender = Gender.FEMALE;
        } else {
            this.gender = Gender.OTHER;
        }
    }

    /**
     * Retrieves the blood type of the patient.
     *
     * @return the blood type of the patient
     */
    public String getBloodType() {
        return this.bloodType;
    }

    /**
     * Sets the blood type of the patient.
     *
     * @param bloodType the new blood type of the patient
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * Retrieves the list of past appointments of the patient.
     *
     * @return a list of past appointments of the patient
     */
    public List<Appointment> getPastAppointments() {
        return appointments.stream()
                .filter(a -> a.getStart().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
}
