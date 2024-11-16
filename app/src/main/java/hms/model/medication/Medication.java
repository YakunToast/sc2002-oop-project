package hms.model.medication;

import java.io.Serializable;
import java.util.UUID;

/** Represents a Medication entity in the hospital management application. */
public class Medication implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private String description;
    private String dosageInstructions;

    private MedicationSideEffect[] sideEffects;

    // Status (e.g., active, discontinued, recalled)
    private MedicationStatus status;

    /** Enum for medication status */
    public enum MedicationStatus {
        ACTIVE,
        DISCONTINUED,
        RECALLED
    }

    /**
     * Constructor for creating a new Medication instance
     *
     * @param medicationName Name of the medication
     * @param description Description of the medication
     * @param dosageInstructions Dosage instructions
     * @param sideEffects Possible side effects
     * @param status Initial status of the medication
     */
    public Medication(
            String medicationName,
            String description,
            String dosageInstructions,
            MedicationSideEffect[] sideEffects,
            MedicationStatus status) {
        this.name = medicationName;
        this.description = description;
        this.dosageInstructions = dosageInstructions;
        this.sideEffects = sideEffects;
        this.status = status;
    }

    /** Getters and Setters for Medication properties */
    public UUID getId() {
        return id;
    }

    public void setId(UUID medicationId) {
        this.id = medicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String medicationName) {
        this.name = medicationName;
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

    public MedicationSideEffect[] getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(MedicationSideEffect[] sideEffects) {
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
        return "Medication [medicationId="
                + id
                + ", medicationName="
                + name
                + ", description="
                + description
                + ", dosageInstructions="
                + dosageInstructions
                + ", sideEffects="
                + java.util.Arrays.toString(sideEffects)
                + ", status="
                + status
                + "]";
    }
}
