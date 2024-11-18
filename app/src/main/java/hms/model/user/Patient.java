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

    /**
     * @return MedicalRecord
     */
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    
    /** 
     * @param phone
     * @param email
     */
    public void updateContactInformation(String phone, String email) {
        medicalRecord.updateContactInformation(phone, email);
    }

    
    /** 
     * @return List<Appointment>
     */
    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    
    /** 
     * @param appointment
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    
    /** 
     * @return String
     */
    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    
    /** 
     * @param dateOfBirth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    
    /** 
     * @return Gender
     */
    public Gender getGender() {
        return this.gender;
    }

    
    /** 
     * @param gender
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
     * @return String
     */
    public String getBloodType() {
        return this.bloodType;
    }

    
    /** 
     * @param bloodType
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    
    /** 
     * @return List<Appointment>
     */
    public List<Appointment> getPastAppointments() {
        return appointments.stream()
                .filter(a -> a.getStart().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
}
