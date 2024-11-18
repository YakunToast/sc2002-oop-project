package hms.model.medication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Prescription implements Serializable {
    private static final long serialVersionUID = 1L;

    private PrescriptionStatus status;
    private Map<Medication, Integer> medications;

    public Prescription(Medication... medications) {
        Map<Medication, Integer> medicationMap = new HashMap<>();
        for (Medication medication : medications) {
            medicationMap.put(
                    medication, 1); // Assuming a default quantity of 1 for each medication
        }
        this.medications = medicationMap;
        this.status = PrescriptionStatus.PENDING;
    }

    public Prescription(Map<Medication, Integer> medications) {
        this.medications = medications;
        this.status = PrescriptionStatus.PENDING;
    }

    public Prescription(Map<Medication, Integer> medications, PrescriptionStatus status) {
        this(medications);
        this.status = status;
    }

    /**
     * @return PrescriptionStatus
     */
    public PrescriptionStatus getPrescriptionStatus() {
        return this.status;
    }

    public void setPending() {
        this.status = PrescriptionStatus.PENDING;
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
    }

    public boolean isCancelled() {
        return this.status == PrescriptionStatus.CANCELLED;
    }

    public Map<Medication, Integer> getMedications() {
        return this.medications;
    }

    public void setMedications(Map<Medication, Integer> medication) {
        this.medications = medication;
    }

    public void printMedication() {
        System.out.println("Prescribed medication: ");
        for (int i = 0; i < this.medications.size(); i++) {
            System.out.println(this.medications.get(i));
        }
    }
}
