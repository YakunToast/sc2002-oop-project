package hms.controller;

import java.util.List;

import hms.model.medication.Inventory;
import hms.model.medication.Medication;
import hms.model.medication.ReplenishmentRequest;
import hms.repository.RepositoryManager;

public class InventoryController {
    private final Inventory inventory;

    public InventoryController() {
        this.inventory = RepositoryManager.getInstance().getInventoryRepository().getInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Medication> getMedications() {
        return inventory.getMedications();
    }

    public boolean addReplenishmentRequest(ReplenishmentRequest rr) {
        RepositoryManager.getInstance().getInventoryRepository().addReplenishmentRequest(rr);
        return true;
    }

    public int getMedicationStock(Medication medication) {
        return this.inventory.getMedicationStock(medication);
    }

    public boolean addMedication(Medication medication) {
        return this.inventory.addMedication(medication);
    }

    public boolean addMedicationStock(Medication medication, int qty) {
        return this.inventory.addMedicationStock(medication, qty);
    }

    public boolean removeMedication(Medication medication) {
        return this.inventory.removeMedication(medication);
    }

    public boolean removeMedicationStock(Medication medication, int qty) {
        return this.inventory.removeMedicationStock(medication, qty);
    }

    public boolean setMedicationStockAlert(Medication medication, int alertQty) {
        return this.inventory.setMedicationStockAlert(medication, alertQty);
    }
}
