package hms.controller.inventory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import hms.model.medication.Inventory;
import hms.model.medication.Medication;

public interface InventoryBase {
    List<Medication> getMedications();

    Optional<Medication> getMedicationByName(String name);

    Optional<Medication> getMedicationByUUID(UUID uuid);

    Optional<Medication> getMedicationByUUID(String uuid);

    Inventory getInventory();
}
