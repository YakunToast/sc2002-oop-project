package hms.controller.inventory;

import java.util.List;

import hms.model.medication.Inventory;
import hms.model.medication.Medication;

public interface InventoryBase {
    List<Medication> getMedications();

    Inventory getInventory();
}
