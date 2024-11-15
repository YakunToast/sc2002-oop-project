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

    // Only allow updating contact information
    public void updateContactInformation(String phone, String email) {
        this.contactPhone = phone;
        this.emailAddress = email;
    }

    // Getters for all fields (medical information is read-only)
    public Patient getPatient() {
        return patient;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getBloodType() {
        return bloodType;
    }

    public List<String> getPastDiagnoses() {
        return Collections.unmodifiableList(pastDiagnoses);
    }

    public List<String> getTreatments() {
        return Collections.unmodifiableList(treatments);
    }
}
