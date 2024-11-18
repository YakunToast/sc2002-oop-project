package hms.model.medication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Medication> medications;
    private Map<String, Integer> alert;
    private Map<String, Integer> stock;

    public Inventory() {
        this.medications = new HashMap<>();
        this.stock = new HashMap<>();
        this.alert = new HashMap<>();
    }

    /**
     * @param medication
     * @return boolean
     */
    public boolean addMedication(Medication medication) {
        // Add medication
        if (medications.containsKey(medication.getName())) {
            return false;
        }
        medications.put(medication.getName(), medication);

        // Update stock as well
        if (!stock.containsKey(medication.getName())) {
            stock.put(medication.getName(), 0);
        }

        return true;
    }

    
    /** 
     * @param medication
     * @param qty
     * @return boolean
     */
    public boolean addMedicationStock(Medication medication, int qty) {
        if (qty < 0) {
            return false;
        }
        stock.merge(medication.getName(), qty, Integer::sum);
        return true;
    }

    
    /** 
     * @param medication
     * @param qty
     * @return boolean
     */
    public boolean setMedicationStock(Medication medication, int qty) {
        if (qty < 0) {
            return false;
        }
        stock.put(medication.getName(), qty);
        return true;
    }

    
    /** 
     * @param medication
     * @return boolean
     */
    public boolean removeMedication(Medication medication) {
        // Remove medication
        if (!medications.containsKey(medication.getName())) {
            return false;
        }
        medications.remove(medication.getName());

        // Remove stock as well
        if (stock.containsKey(medication.getName())) {
            stock.remove(medication.getName());
        }
        return true;
    }

    
    /** 
     * @param medication
     * @param qty
     * @return boolean
     */
    public boolean removeMedicationStock(Medication medication, int qty) {
        if (qty > 0) {
            return false;
        }

        // Make sure we cannot remove more than what's left
        int currentStock = stock.get(medication.getName());
        int delta = Integer.min(currentStock, qty);

        stock.merge(medication.getName(), -delta, Integer::sum);
        return true;
    }

    
    /** 
     * @param medication
     * @param qty
     * @return boolean
     */
    public boolean setMedicationStockAlert(Medication medication, int qty) {
        if (qty <= 0) {
            return false;
        }
        this.alert.put(medication.getName(), qty);
        return true;
    }

    
    /** 
     * @param medication
     * @return int
     */
    public int getMedicationStockAlert(Medication medication) {
        return this.alert.get(medication.getName());
    }

    
    /** 
     * @return List<Medication>
     */
    public List<Medication> getMedications() {
        return List.copyOf(this.medications.values());
    }

    
    /** 
     * @param name
     * @return Optional<Medication>
     */
    public Optional<Medication> getMedicationByName(String name) {
        return Optional.ofNullable(this.medications.get(name));
    }

    
    /** 
     * @param id
     * @return Optional<Medication>
     */
    public Optional<Medication> getMedicationByUUID(UUID id) {
        return this.medications.values().stream().filter(m -> m.getId() == id).findFirst();
    }

    
    /** 
     * @param medication
     * @return int
     */
    public int getMedicationStock(Medication medication) {
        return this.stock.get(medication.getName());
    }
}
