package sc2002.cli;

import sc2002.model.role.Patient;

public class PatientView {
    private Patient patient;

    public PatientView(Patient patient) {
        this.patient = patient;
    }

    void getMedicalRecord() {
        // // Retrieve the patient's medical record
        // MedicalRecord medicalRecord = patient.getMedicalRecord();

        // // Display the patient's medical record with required details
        // System.out.println("Patient's Medical Record:");
        // System.out.println("---------------------------");
        // System.out.printf("Patient ID: %s%n", patient.getPatientId());
        // System.out.printf("Name: %s %s%n", patient.getFirstName(),
        // patient.getLastName());
        // System.out.printf("Date of Birth: %s%n", patient.getDateOfBirth());
        // System.out.printf("Gender: %s%n", patient.getGender());
        // System.out.println("Contact Information:");
        // System.out.printf(" Phone: %s%n", patient.getPhoneNumber());
        // System.out.printf(" Email: %s%n", patient.getEmail());
        // System.out.printf("Blood Type: %s%n", medicalRecord.getBloodType());
        // System.out.println("Past Diagnoses and Treatments:");
        // for (int i = 0; i < medicalRecord.getPastDiagnosesAndTreatments().size();
        // i++) {
        // System.out.printf(" %d. %s - %s%n",
        // (i + 1),
        // medicalRecord.getPastDiagnosesAndTreatments().get(i).getDiagnosis(),
        // medicalRecord.getPastDiagnosesAndTreatments().get(i).getTreatment());
        // }
        // System.out.println("---------------------------");
    }
}
