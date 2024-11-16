package hms.model.medication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Medication> medications;

    public Inventory() {
        medications = new HashMap<>();
    }

    public void addMedication(Medication medication) {
        medications.put(medication.getName(), medication);
    }

    public void removeMedication(Medication medication) {
        medications.remove(medication.getName());
    }

    public List<Medication> getMedications() {
        return List.copyOf(this.medications.values());
    }
}
