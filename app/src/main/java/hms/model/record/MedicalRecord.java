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

    /**
     * @param phone
     * @param email
     */
    // Only allow updating contact information
    public void updateContactInformation(String phone, String email) {
        this.contactPhone = phone;
        this.emailAddress = email;
    }

    /**
     * @return String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Medical Record\n");
        sb.append("---------------\n");
        sb.append("Patient Name: ").append(patient.getName()).append("\n");
        sb.append("Contact Phone: ").append(contactPhone).append("\n");
        sb.append("Email Address: ").append(emailAddress).append("\n");
        sb.append("Doctor: ").append(doctor != null ? doctor.getName() : "None").append("\n");
        sb.append("Past Diagnoses and Treatments:\n");
        for (int i = 0; i < pastDiagnoses.size(); i++) {
            sb.append(
                    String.format(
                            "%d. Diagnosis: %s - Treatment Plan: %s%n",
                            (i + 1),
                            pastDiagnoses.isEmpty() ? "None" : pastDiagnoses.get(i),
                            treatments.isEmpty() ? "None" : treatments.get(i)));
        }

        return sb.toString();
    }

    /**
     * @return Patient
     */
    // Getters for all fields (medical information is read-only)
    public Patient getPatient() {
        return patient;
    }

    /**
     * @param patient
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * @return String
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * @param contactPhone
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * @return String
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return List<String>
     */
    public List<String> getPastDiagnoses() {
        return Collections.unmodifiableList(pastDiagnoses);
    }

    /**
     * @param diagnosis
     */
    public void addDiagnosis(String diagnosis) {
        this.pastDiagnoses.add(diagnosis);
    }

    /**
     * @return List<String>
     */
    public List<String> getTreatments() {
        return Collections.unmodifiableList(treatments);
    }

    /**
     * @param treatment
     */
    public void addTreatment(String treatment) {
        this.treatments.add(treatment);
    }

    /**
     * @return Doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * @param doctor
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
