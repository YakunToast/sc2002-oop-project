package sc2002.model;

public class Prescription {
    private PrescriptionStatus status;
    private String[] medication;

    public Prescription(PrescriptionStatus status, String[] medication) {
        this.status = status;
        this.medication = medication;
    }

    public PrescriptionStatus getPrescriptionStatus() {
        return this.status;
    }

    public String[] getMedication() {
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

    public void setMedication(String[] medication) {
        this.medication = medication;
    }

    public void printMedication() {
        System.out.println("Prescribed medication: ");
        for (int i = 0; i < this.medication.length; i++) {
            System.out.println(this.medication[i]);
        }
    }
}
