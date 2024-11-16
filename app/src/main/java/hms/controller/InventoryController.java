package hms.controller;

import java.util.List;

import hms.model.medication.Inventory;
import hms.model.medication.Medication;
import hms.model.medication.ReplenishmentRequest;
import hms.repository.RepositoryManager;

public class InventoryController {
    private final Inventory inventory;

    public InventoryController(Inventory i) {
        inventory = i;
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
}
