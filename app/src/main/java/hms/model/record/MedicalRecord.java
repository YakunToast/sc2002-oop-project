package hms.model.record;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hms.model.user.Doctor;
import hms.model.user.Patient;

/**
 * Represents a medical record for a patient, containing personal contact information, past
 * diagnoses, treatments, and the responsible doctor.
 */
public class MedicalRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Patient patient;
    private String contactPhone;
    private String emailAddress;
    private final List<String> pastDiagnoses;
    private final List<String> treatments;
    private Doctor doctor;

    /** Enum representing the possible genders of a patient. */
    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    /**
     * Constructs a new medical record for the specified patient.
     *
     * @param patient the patient for whom the medical record is created
     */
    public MedicalRecord(Patient patient) {
        this.patient = patient;
        this.pastDiagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
    }

    /**
     * Updates the patient's contact information with the specified phone number and email address.
     *
     * @param phone the new contact phone number
     * @param email the new email address
     */
    public void updateContactInformation(String phone, String email) {
        this.contactPhone = phone;
        this.emailAddress = email;
    }

    /**
     * Returns a string representation of the medical record.
     *
     * @return a string representation of the medical record
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Medical Record\n");
        sb.append("---------------\n");
        sb.append("Patient Name: ").append(patient.getName()).append("\n");
        sb.append("Contact Phone: ").append(patient.getPhoneNumber()).append("\n");
        sb.append("Email Address: ").append(patient.getEmail()).append("\n");
        sb.append("Last Doctor: ").append(doctor).append("\n");
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
     * Returns the patient associated with this medical record.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the contact phone number of the patient.
     *
     * @return the contact phone number
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * Sets the contact phone number of the patient.
     *
     * @param contactPhone the contact phone number to be set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * Returns the email address of the patient.
     *
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email address of the patient.
     *
     * @param emailAddress the email address to be set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Returns an unmodifiable list of past diagnoses recorded in the medical record.
     *
     * @return an unmodifiable list of past diagnoses
     */
    public List<String> getPastDiagnoses() {
        return Collections.unmodifiableList(pastDiagnoses);
    }

    /**
     * Adds a new diagnosis to the medical record.
     *
     * @param diagnosis the diagnosis to be added
     */
    public void addDiagnosis(String diagnosis) {
        this.pastDiagnoses.add(diagnosis);
    }

    /**
     * Returns an unmodifiable list of treatments recorded in the medical record.
     *
     * @return an unmodifiable list of treatments
     */
    public List<String> getTreatments() {
        return Collections.unmodifiableList(treatments);
    }

    /**
     * Adds a new treatment to the medical record.
     *
     * @param treatment the treatment to be added
     */
    public void addTreatment(String treatment) {
        this.treatments.add(treatment);
    }

    /**
     * Returns the doctor responsible for this medical record.
     *
     * @return the doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Sets the doctor responsible for this medical record.
     *
     * @param doctor the doctor to be set
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
