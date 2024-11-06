package sc2002.model;

class MedicalRecord {
    private String patientId;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String contactPhone;
    private String emailAddress;
    private String bloodType;
    private List<String> pastDiagnoses;
    private List<String> treatments;

    public MedicalRecord(String patientId, String name, String dateOfBirth, String gender) {
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.pastDiagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
    }

    // Only allow updating contact information
    public void updateContactInformation(String phone, String email) {
        this.contactPhone = phone;
        this.emailAddress = email;
    }

    // Getters for all fields (medical information is read-only)
    public String getPatientId() { return patientId; }
    public String getName() { return name; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getContactPhone() { return contactPhone; }
    public String getEmailAddress() { return emailAddress; }
    public String getBloodType() { return bloodType; }
    public List<String> getPastDiagnoses() { 
        return Collections.unmodifiableList(pastDiagnoses); 
    }
    public List<String> getTreatments() { 
        return Collections.unmodifiableList(treatments); 
    }
}