package hms.model.medication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Inventory implements Serializable {
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
}
