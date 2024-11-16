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
    }

    public void setPending() {
        this.status = ReplenishmentRequestStatus.PENDING;
    }

    public boolean isPending() {
        return this.status == ReplenishmentRequestStatus.PENDING;
    }

    public void setApproved() {
        this.status = ReplenishmentRequestStatus.APPROVED;
    }

    public boolean isApproved() {
        return this.status == ReplenishmentRequestStatus.APPROVED;
    }

    public Medication getMedication() {
        return this.medication;
    }

    public int getRequestedQuantity() {
        return this.qty;
    }
}
