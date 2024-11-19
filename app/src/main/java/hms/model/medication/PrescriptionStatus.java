package hms.model.medication;

/**
 * Represents the status of a prescription within a hospital management system. The status
 * indicates the current state of the prescription in the workflow, such as whether it is pending,
 * dispensed, or cancelled.
 */
public enum PrescriptionStatus {
    /** Indicates that the prescription has been created but not yet dispensed. */
    PENDING,

    /** Indicates that the prescription has been dispensed to the patient. */
    DISPENSED,

    /** Indicates that the prescription has been cancelled and is no longer valid. */
    CANCELLED
}
