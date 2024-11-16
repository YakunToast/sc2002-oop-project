package hms.model.medication;

import java.io.Serializable;

public class Prescription implements Serializable {
    private static final long serialVersionUID = 1L;

    private PrescriptionStatus status;
    private Medication[] medication;

    public Prescription(PrescriptionStatus status, Medication[] medication) {
        this.status = status;
        this.medication = medication;
    }

    public PrescriptionStatus getPrescriptionStatus() {
        return this.status;
    }

    public void setPending() {
        this.status = PrescriptionStatus.PENDING;
        // TODO: Remove from inventory
    }

    public boolean isPending() {
        return this.status == PrescriptionStatus.PENDING;
    }

    public void setDispensed() {
        this.status = PrescriptionStatus.DISPENSED;
    }

    public boolean isDispensed() {
        return this.status == PrescriptionStatus.DISPENSED;
    }

    public void setCancelled() {
        this.status = PrescriptionStatus.CANCELLED;
        // TODO: Add back to inventory
    }

    public boolean isCancelled() {
        return this.status == PrescriptionStatus.CANCELLED;
    }

    public Medication[] getMedication() {
        return this.medication;
    }

    public void setMedication(Medication[] medication) {
        this.medication = medication;
    }

    public void printMedication() {
        System.out.println("Prescribed medication: ");
        for (int i = 0; i < this.medication.length; i++) {
            System.out.println(this.medication[i]);
        }
    }
}
