package hms.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import hms.model.medication.Inventory;
import hms.model.medication.Medication;
import hms.model.medication.ReplenishmentRequest;
import hms.repository.RepositoryManager;

public class InventoryController {
    private final Inventory inventory;

    public InventoryController() {
        this.inventory = RepositoryManager.getInstance().getInventoryRepository().getInventory();
    }

    /** 
     * @return Inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    
    /** 
     * @return List<Medication>
     */
    public List<Medication> getMedications() {
        return inventory.getMedications();
    }

    
    /** 
     * @param name
     * @return Optional<Medication>
     */
    public Optional<Medication> getMedicationByName(String name) {
        return inventory.getMedicationByName(name);
    }

    
    /** 
     * @param uuid
     * @return Optional<Medication>
     */
    public Optional<Medication> getMedicationByUUID(UUID uuid) {
        return inventory.getMedicationByUUID(uuid);
    }

    
    /** 
     * @param uuid
     * @return Optional<Medication>
     */
    public Optional<Medication> getMedicationByUUID(String uuid) {
        return inventory.getMedicationByUUID(UUID.fromString(uuid));
    }

    
    /** 
     * @param rr
     * @return boolean
     */
    public boolean addReplenishmentRequest(ReplenishmentRequest rr) {
        RepositoryManager.getInstance().getInventoryRepository().addReplenishmentRequest(rr);
        return true;
    }

    
    /** 
     * @param medication
     * @return int
     */
    public int getMedicationStock(Medication medication) {
        return this.inventory.getMedicationStock(medication);
    }

    
    /** 
     * @param medication
     * @return boolean
     */
    public boolean addMedication(Medication medication) {
        return this.inventory.addMedication(medication);
    }

    
    /** 
     * @param medication
     * @param qty
     * @return boolean
     */
    public boolean addMedicationStock(Medication medication, int qty) {
        return this.inventory.addMedicationStock(medication, qty);
    }

    
    /** 
     * @param medication
     * @param qty
     * @return boolean
     */
    public boolean setMedicationStock(Medication medication, int qty) {
        return this.inventory.setMedicationStock(medication, qty);
    }

    
    /** 
     * @param medication
     * @return boolean
     */
    public boolean removeMedication(Medication medication) {
        return this.inventory.removeMedication(medication);
    }

    
    /** 
     * @param medication
     * @param qty
     * @return boolean
     */
    public boolean removeMedicationStock(Medication medication, int qty) {
        return this.inventory.removeMedicationStock(medication, qty);
    }

    
    /** 
     * @param medication
     * @param alertQty
     * @return boolean
     */
    public boolean setMedicationStockAlert(Medication medication, int alertQty) {
        return this.inventory.setMedicationStockAlert(medication, alertQty);
    }

    
    /** 
     * @param medication
     * @return int
     */
    public int getMedicationStockAlert(Medication medication) {
        return this.inventory.getMedicationStockAlert(medication);
    }
}
