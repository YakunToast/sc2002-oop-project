package hms.model.medication;

import java.io.Serializable;
import java.time.LocalDateTime;

import hms.model.user.Pharmacist;

public class ReplenishmentRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Medication medication;
    private final int qty;
    private ReplenishmentRequestStatus status;
    private final LocalDateTime datetime;
    private final Pharmacist pharmacist;

    public ReplenishmentRequest(
            Medication medication, int qty, LocalDateTime datetime, Pharmacist pharmacist) {
        this.medication = medication;
        this.qty = qty;
        this.datetime = datetime;
        this.pharmacist = pharmacist;
        this.status = ReplenishmentRequestStatus.PENDING;
    }

    /**
     * @return Pharmacist
     */
    public Pharmacist getPharmacist() {
        return this.pharmacist;
    }

    public void setPending() {
        this.status = ReplenishmentRequestStatus.PENDING;
    }

    
    /** 
     * @return boolean
     */
    public boolean isPending() {
        return this.status == ReplenishmentRequestStatus.PENDING;
    }

    public void setApproved() {
        this.status = ReplenishmentRequestStatus.APPROVED;
    }

    
    /** 
     * @return boolean
     */
    public boolean isApproved() {
        return this.status == ReplenishmentRequestStatus.APPROVED;
    }

    
    /** 
     * @return Medication
     */
    public Medication getMedication() {
        return this.medication;
    }

    
    /** 
     * @return int
     */
    public int getRequestedQuantity() {
        return this.qty;
    }
}
