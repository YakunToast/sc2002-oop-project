package hms.model.record;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hms.model.user.Doctor;
import hms.model.user.Patient;

public class MedicalRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private Patient patient;
    private String contactPhone;
    private String emailAddress;
    private List<String> pastDiagnoses;
    private List<String> treatments;

    private Doctor doctor;

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    public MedicalRecord(Patient patient) {
        this.patient = patient;
        this.pastDiagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
    }

    // Only allow updating contact information
    public void updateContactInformation(String phone, String email) {
        this.contactPhone = phone;
        this.emailAddress = email;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Medical Record\n");
        sb.append("---------------\n");
        sb.append("Patient Name: ").append(patient.getName()).append("\n");
        sb.append("Contact Phone: ").append(contactPhone).append("\n");
        sb.append("Email Address: ").append(emailAddress).append("\n");
        sb.append("Doctor: ").append(doctor != null ? doctor.getName() : "None").append("\n");
        sb.append("Past Diagnoses: ")
                .append(pastDiagnoses.isEmpty() ? "None" : String.join(", ", pastDiagnoses))
                .append("\n");
        sb.append("Treatments: ")
                .append(treatments.isEmpty() ? "None" : String.join(", ", treatments))
                .append("\n");
        return sb.toString();
    }

    // Getters for all fields (medical information is read-only)
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<String> getPastDiagnoses() {
        return Collections.unmodifiableList(pastDiagnoses);
    }

    public List<String> getTreatments() {
        return Collections.unmodifiableList(treatments);
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
