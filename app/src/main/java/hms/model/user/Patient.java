package hms.model.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import hms.model.appointment.Appointment;
import hms.model.record.MedicalRecord;

// Patient class to manage patient operations
public class Patient extends User {
    private MedicalRecord medicalRecord;
    private List<Appointment> appointments;

    private String dateOfBirth;
    private Gender gender;
    private String bloodType;

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

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

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void updateContactInformation(String phone, String email) {
        medicalRecord.updateContactInformation(phone, email);
    }

    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        if ("M".equals(gender)) {
            this.gender = Gender.MALE;
        } else if ("F".equals(gender)) {
            this.gender = Gender.FEMALE;
        } else {
            this.gender = Gender.OTHER;
        }
    }

    public String getBloodType() {
        return this.bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public List<Appointment> getPastAppointments() {
        return appointments.stream()
                .filter(a -> a.getStart().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
}
