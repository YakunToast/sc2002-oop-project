package hms.model.appointment;

/**
 * This enum represents the possible statuses of an appointment within the Health Management System
 * (HMS).
 */
public enum AppointmentStatus {
    /** The appointment is currently free and available to be scheduled. */
    FREE,
    /** The appointment request has been made but has not yet been confirmed. */
    PENDING,
    /** The appointment has been confirmed and is scheduled to take place. */
    CONFIRMED,
    /** The appointment has been cancelled and will not take place. */
    CANCELLED,
    /** The appointment has been completed and has taken place. */
    COMPLETED
}
