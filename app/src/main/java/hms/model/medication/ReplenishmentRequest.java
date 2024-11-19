package hms.model.medication;

import java.io.Serializable;
import java.time.LocalDateTime;

import hms.model.user.Pharmacist;

/**
 * Represents a replenishment request for a specific medication. This class encapsulates the details
 * of a request, including the medication, requested quantity, status, date and time of the request,
 * and the pharmacist who created the request.
 */
public class ReplenishmentRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Medication medication;
    private final int qty;
    private ReplenishmentRequestStatus status;
    private final LocalDateTime datetime;
    private final Pharmacist pharmacist;

    /**
     * Constructs a new instance of ReplenishmentRequest with the specified medication, quantity,
     * date and time, and pharmacist.
     *
     * @param medication the medication for which the replenishment is requested
     * @param qty the quantity of medication requested
     * @param datetime the date and time when the request was made
     * @param pharmacist the pharmacist who created the request
     */
    public ReplenishmentRequest(
            Medication medication, int qty, LocalDateTime datetime, Pharmacist pharmacist) {
        this.medication = medication;
        this.qty = qty;
        this.datetime = datetime;
        this.pharmacist = pharmacist;
        this.status = ReplenishmentRequestStatus.PENDING;
    }

    /**
     * Returns the pharmacist who created the replenishment request.
     *
     * @return the pharmacist who created the request
     */
    public Pharmacist getPharmacist() {
        return this.pharmacist;
    }

    /** Sets the status of the replenishment request to PENDING. */
    public void setPending() {
        this.status = ReplenishmentRequestStatus.PENDING;
    }

    /**
     * Checks if the status of the replenishment request is PENDING.
     *
     * @return true if the request is pending, false otherwise
     */
    public boolean isPending() {
        return this.status == ReplenishmentRequestStatus.PENDING;
    }

    /** Sets the status of the replenishment request to APPROVED. */
    public void setApproved() {
        this.status = ReplenishmentRequestStatus.APPROVED;
    }

    /**
     * Checks if the status of the replenishment request is APPROVED.
     *
     * @return true if the request is approved, false otherwise
     */
    public boolean isApproved() {
        return this.status == ReplenishmentRequestStatus.APPROVED;
    }

    /**
     * Returns the medication for which the replenishment request was made.
     *
     * @return the medication for which the request was made
     */
    public Medication getMedication() {
        return this.medication;
    }

    /**
     * Returns the quantity of medication requested in the replenishment request.
     *
     * @return the quantity of medication requested
     */
    public int getRequestedQuantity() {
        return this.qty;
    }
}
