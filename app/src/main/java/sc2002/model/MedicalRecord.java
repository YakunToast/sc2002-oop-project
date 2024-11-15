package sc2002.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sc2002.model.role.Patient;

public class MedicalRecord implements Serializable {
    private Patient patient;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String contactPhone;
    private String emailAddress;
    private String bloodType;
    private List<String> pastDiagnoses;
    private List<String> treatments;

    public MedicalRecord(Patient patient) {
        this.patient = patient;
        this.pastDiagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
    }

    public MedicalRecord(Patient patient, String dateOfBirth, String gender, String bloodType, String contactPhone) {
        this(patient);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.contactPhone = contactPhone;
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
        sb.append("Date of Birth: ").append(dateOfBirth).append("\n");
        sb.append("Gender: ").append(gender).append("\n");
        sb.append("Blood Type: ").append(bloodType).append("\n");
        sb.append("Contact Phone: ").append(contactPhone).append("\n");
        sb.append("Email Address: ").append(emailAddress).append("\n");
        sb.append("Doctor: ").append(doctor != null ? doctor.getName() : "None").append("\n");
        sb.append("Past Diagnoses: ").append(pastDiagnoses.isEmpty() ? "None" : String.join(", ", pastDiagnoses)).append("\n");
        sb.append("Treatments: ").append(treatments.isEmpty() ? "None" : String.join(", ", treatments)).append("\n");
        return sb.toString();
    }

    // Getters for all fields (medical information is read-only)
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
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
}
