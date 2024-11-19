package hms.model.medication;

import java.io.Serializable;
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

    /** Enum for medication status. */
    public enum MedicationStatus {
        ACTIVE,
        DISCONTINUED,
        RECALLED
    }

    /**
     * Constructor for creating a new Medication instance.
     *
     * @param medicationName Name of the medication.
     * @param description Description of the medication.
     * @param dosageInstructions Dosage instructions for the medication.
     */
    public Medication(String medicationName, String description, String dosageInstructions) {
        this.name = medicationName;
        this.description = description;
        this.dosageInstructions = dosageInstructions;
    }

    /**
     * Constructor for creating a new Medication instance.
     *
     * @param medicationName Name of the medication.
     * @param description Description of the medication.
     * @param dosageInstructions Dosage instructions for the medication.
     * @param sideEffects Possible side effects of the medication.
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
     * Constructor for creating a new Medication instance.
     *
     * @param medicationName Name of the medication.
     * @param description Description of the medication.
     * @param dosageInstructions Dosage instructions for the medication.
     * @param sideEffects Possible side effects of the medication.
     */
    public Medication(
            String medicationName,
            String description,
            String dosageInstructions,
            MedicationSideEffect... sideEffects) {
        this(medicationName, description, dosageInstructions, List.of(sideEffects));
    }

    /**
     * Constructor for creating a new Medication instance.
     *
     * @param medicationName Name of the medication.
     * @param description Description of the medication.
     * @param dosageInstructions Dosage instructions for the medication.
     * @param sideEffects Possible side effects of the medication.
     * @param status Initial status of the medication.
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

    /**
     * Retrieves the unique identifier for the medication.
     *
     * @return The unique identifier of the medication.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the medication.
     *
     * @param medicationId The UUID to set for the medication.
     */
    public void setId(UUID medicationId) {
        this.id = medicationId;
    }

    /**
     * Retrieves the name of the medication.
     *
     * @return The name of the medication.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the medication.
     *
     * @param medicationName The name to set for the medication.
     */
    public void setName(String medicationName) {
        this.name = medicationName;
    }

    /**
     * Retrieves the description of the medication.
     *
     * @return The description of the medication.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the medication.
     *
     * @param description The description to set for the medication.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the dosage instructions for the medication.
     *
     * @return The dosage instructions for the medication.
     */
    public String getDosageInstructions() {
        return dosageInstructions;
    }

    /**
     * Sets the dosage instructions for the medication.
     *
     * @param dosageInstructions The dosage instructions to set for the medication.
     */
    public void setDosageInstructions(String dosageInstructions) {
        this.dosageInstructions = dosageInstructions;
    }

    /**
     * Retrieves the list of possible side effects for the medication.
     *
     * @return The list of side effects for the medication.
     */
    public List<MedicationSideEffect> getSideEffects() {
        return sideEffects;
    }

    /**
     * Sets the list of possible side effects for the medication.
     *
     * @param sideEffects The list of side effects to set for the medication.
     */
    public void setSideEffects(List<MedicationSideEffect> sideEffects) {
        this.sideEffects = sideEffects;
    }

    /**
     * Retrieves the current status of the medication.
     *
     * @return The current status of the medication.
     */
    public MedicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the current status of the medication.
     *
     * @param status The status to set for the medication.
     */
    public void setStatus(MedicationStatus status) {
        this.status = status;
    }

    /**
     * Retrieves a string representation of the medication.
     *
     * @return A string representation of the medication.
     */
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
