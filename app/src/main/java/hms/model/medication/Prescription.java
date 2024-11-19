package hms.model.medication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a prescription containing a list of medications and their quantities along with the
 * prescription status.
 */
public class Prescription implements Serializable {
    private static final long serialVersionUID = 1L;
    private PrescriptionStatus status;
    private Map<Medication, Integer> medications;

    /**
     * Default constructor that initializes the prescription with a PENDING status and no
     * medications.
     */
    public Prescription() {
        this.medications = null;
        this.status = PrescriptionStatus.PENDING;
    }

    /**
     * Constructor that initializes the prescription with the given medications and a default
     * quantity of 1 for each medication, and a PENDING status.
     *
     * @param medications varargs of Medication objects to be included in the prescription.
     */
    public Prescription(Medication... medications) {
        Map<Medication, Integer> medicationMap = new HashMap<>();
        for (Medication medication : medications) {
            medicationMap.put(
                    medication, 1); // Assuming a default quantity of 1 for each medication
        }
        this.medications = medicationMap;
        this.status = PrescriptionStatus.PENDING;
    }

    /**
     * Constructor that initializes the prescription with the given map of medications and their
     * respective quantities, and a PENDING status.
     *
     * @param medications map containing Medication objects and their respective quantities.
     */
    public Prescription(Map<Medication, Integer> medications) {
        this.medications = medications;
        this.status = PrescriptionStatus.PENDING;
    }

    /**
     * Constructor that initializes the prescription with the given map of medications and their
     * respective quantities, and the given prescription status.
     *
     * @param medications map containing Medication objects and their respective quantities.
     * @param status prescription status.
     */
    public Prescription(Map<Medication, Integer> medications, PrescriptionStatus status) {
        this(medications);
        this.status = status;
    }

    /**
     * Retrieves the current status of the prescription.
     *
     * @return PrescriptionStatus representing the current status of the prescription.
     */
    public PrescriptionStatus getPrescriptionStatus() {
        return this.status;
    }

    /** Sets the prescription status to PENDING. */
    public void setPending() {
        this.status = PrescriptionStatus.PENDING;
    }

    /**
     * Checks if the prescription status is PENDING.
     *
     * @return boolean value indicating whether the prescription status is PENDING.
     */
    public boolean isPending() {
        return this.status == PrescriptionStatus.PENDING;
    }

    /** Sets the prescription status to DISPENSED. */
    public void setDispensed() {
        this.status = PrescriptionStatus.DISPENSED;
    }

    /**
     * Checks if the prescription status is DISPENSED.
     *
     * @return boolean value indicating whether the prescription status is DISPENSED.
     */
    public boolean isDispensed() {
        return this.status == PrescriptionStatus.DISPENSED;
    }

    /** Sets the prescription status to CANCELLED. */
    public void setCancelled() {
        this.status = PrescriptionStatus.CANCELLED;
    }

    /**
     * Checks if the prescription status is CANCELLED.
     *
     * @return boolean value indicating whether the prescription status is CANCELLED.
     */
    public boolean isCancelled() {
        return this.status == PrescriptionStatus.CANCELLED;
    }

    /**
     * Retrieves the map of medications and their respective quantities.
     *
     * @return Map containing Medication objects and their respective quantities.
     */
    public Map<Medication, Integer> getMedications() {
        return this.medications;
    }

    /**
     * Sets the medications and their respective quantities for the prescription.
     *
     * @param medications map containing Medication objects and their respective quantities.
     */
    public void setMedications(Map<Medication, Integer> medications) {
        this.medications = medications;
    }
}
