package sc2002.model;

import java.util.UUID;

/**
 * Represents a Medication entity in the hospital management application.
 */
public class Medication {

    // Unique identifier for the medication
    private UUID medicationId;

    // Name of the medication
    private String medicationName;

    // Description of the medication (e.g., purpose, dosage form)
    private String description;

    // Dosage instructions (e.g., frequency, amount)
    private String dosageInstructions;

    // Possible side effects
    private String[] sideEffects;

    // Status (e.g., active, discontinued, recalled)
    private MedicationStatus status;

    /**
     * Enum for medication status
     */
    public enum MedicationStatus {
        ACTIVE, DISCONTINUED, RECALLED
    }

    /**
     * Constructor for creating a new Medication instance
     * 
     * @param medicationName
     *            Name of the medication
     * @param description
     *            Description of the medication
     * @param dosageInstructions
     *            Dosage instructions
     * @param sideEffects
     *            Possible side effects
     * @param status
     *            Initial status of the medication
     */
    public Medication(String medicationName, String description, String dosageInstructions, String[] sideEffects,
            MedicationStatus status) {
        this.medicationName = medicationName;
        this.description = description;
        this.dosageInstructions = dosageInstructions;
        this.sideEffects = sideEffects;
        this.status = status;
    }

    /**
     * Getters and Setters for Medication properties
     */

    public UUID getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(UUID medicationId) {
        this.medicationId = medicationId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDosageInstructions() {
        return dosageInstructions;
    }

    public void setDosageInstructions(String dosageInstructions) {
        this.dosageInstructions = dosageInstructions;
    }

    public String[] getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String[] sideEffects) {
        this.sideEffects = sideEffects;
    }

    public MedicationStatus getStatus() {
        return status;
    }

    public void setStatus(MedicationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Medication [medicationId=" + medicationId + ", medicationName=" + medicationName + ", description="
                + description
                + ", dosageInstructions=" + dosageInstructions + ", sideEffects="
                + java.util.Arrays.toString(sideEffects)
                + ", status=" + status + "]";
    }
}
