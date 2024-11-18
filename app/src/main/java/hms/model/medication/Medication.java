package hms.model.medication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Represents a Medication entity in the hospital management application. */
public class Medication implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private String description;
    private String dosageInstructions;

    private List<MedicationSideEffect> sideEffects;

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
     */
    public Medication(String medicationName, String description, String dosageInstructions) {
        this.name = medicationName;
        this.description = description;
        this.dosageInstructions = dosageInstructions;
        this.sideEffects = new ArrayList<>(null);
    }

    /**
     * Constructor for creating a new Medication instance
     *
     * @param medicationName Name of the medication
     * @param description Description of the medication
     * @param dosageInstructions Dosage instructions
     * @param sideEffects Possible side effects
     */
    public Medication(
            String medicationName,
            String description,
            String dosageInstructions,
            List<MedicationSideEffect> sideEffects) {
        this.name = medicationName;
        this.description = description;
        this.dosageInstructions = dosageInstructions;
        this.sideEffects = sideEffects;
    }

    /**
     * Constructor for creating a new Medication instance
     *
     * @param medicationName Name of the medication
     * @param description Description of the medication
     * @param dosageInstructions Dosage instructions
     * @param sideEffects Possible side effects
     */
    public Medication(
            String medicationName,
            String description,
            String dosageInstructions,
            MedicationSideEffect... sideEffects) {
        this(medicationName, description, dosageInstructions, List.of(sideEffects));
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
            List<MedicationSideEffect> sideEffects,
            MedicationStatus status) {
        this(medicationName, description, dosageInstructions, sideEffects);
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

    public List<MedicationSideEffect> getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(List<MedicationSideEffect> sideEffects) {
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
                + sideEffects
                + ", status="
                + status
                + "]";
    }
}
