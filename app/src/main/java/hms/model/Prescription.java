package hms.model;

import java.io.Serializable;

import hms.model.medication.Medication;

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

    public Medication[] getMedication() {
        return this.medication;
    }

    public void setPending(PrescriptionStatus status) {
        this.status = PrescriptionStatus.PENDING;
    }

    public void setDispensed(PrescriptionStatus status) {
        this.status = PrescriptionStatus.DISPENSED;
    }

    public void setCancelled(PrescriptionStatus status) {
        this.status = PrescriptionStatus.CANCELLED;
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
