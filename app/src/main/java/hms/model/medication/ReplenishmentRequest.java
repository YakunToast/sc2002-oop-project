package hms.model.medication;

import java.io.Serializable;

public class ReplenishmentRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Medication medication;
    private final int qty;
    private ReplenishmentRequestStatus status;

    public ReplenishmentRequest(Medication medication, int qty) {
        this.medication = medication;
        this.qty = qty;
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
