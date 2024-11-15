package sc2002.model.role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import sc2002.model.MedicalRecord;
import sc2002.model.appointment.Appointment;

// Patient class to manage patient operations
public class Patient extends User {
    private MedicalRecord medicalRecord;
    private List<Appointment> appointments;

    public Patient(String id, String username, String firstName, String lastName, String password, String email, String phoneNumber) {
        super(id, username, firstName, lastName, password, email, phoneNumber, UserRole.PATIENT);
        this.medicalRecord = new MedicalRecord(this);
        this.appointments = new ArrayList<>();
    }

    public Patient(String id, String username, String firstName, String lastName, String password, String email, String phoneNumber, String dateOfBirth, String gender, String bloodType) {
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
        return medicalRecord.getDateOfBirth();
    }

    public void setDateOfBirth(String dateOfBirth) {
        medicalRecord.setDateOfBirth(dateOfBirth);
    }

    public String getGender() {
        return medicalRecord.getGender();
    }

    public void setGender(String gender) {
        medicalRecord.setGender(gender);
    }

    public Doctor getDoctor() {
        return medicalRecord.getDoctor();
    }

    public void setDoctor(Doctor doctor) {
        medicalRecord.setDoctor(doctor);
    }

    public String getBloodType() {
        return medicalRecord.getBloodType();
    }

    public void setBloodType(String bloodType) {
        medicalRecord.setBloodType(bloodType);
    }

    public List<Appointment> getPastAppointments() {
        return appointments.stream()
                .filter(a -> a.getDateTime().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
}
