package hms.model.medication;

import java.io.Serializable;
import java.util.List;

public class Prescription implements Serializable {
    private static final long serialVersionUID = 1L;

    private PrescriptionStatus status;
    private List<Medication> medications;

    public Prescription(Medication... medications) {
        this.medications = List.of(medications);
        this.status = PrescriptionStatus.PENDING;
    }

    public Prescription(List<Medication> medications) {
        this.medications = medications;
        this.status = PrescriptionStatus.PENDING;
    }

    public Prescription(List<Medication> medications, PrescriptionStatus status) {
        this(medications);
        this.status = status;
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

    public List<Medication> getMedications() {
        return this.medications;
    }

    public void setMedications(List<Medication> medication) {
        this.medications = medication;
    }

    public void printMedication() {
        System.out.println("Prescribed medication: ");
        for (int i = 0; i < this.medications.size(); i++) {
            System.out.println(this.medications.get(i));
        }
    }
}
