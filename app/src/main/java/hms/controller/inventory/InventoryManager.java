package hms.controller.inventory;

import java.util.List;

import hms.model.medication.Medication;
import hms.model.medication.ReplenishmentRequest;

public interface InventoryManager extends InventoryBase {
    boolean approveReplenishmentRequest(ReplenishmentRequest replenishmentRequest);

    List<ReplenishmentRequest> getPendingReplenishmentRequests();

    int getMedicationStock(Medication medication);

    boolean addMedication(Medication medication);

    boolean addMedicationStock(Medication medication, int qty);

    boolean removeMedication(Medication medication);

    boolean removeMedicationStock(Medication medication, int qty);

    boolean setMedicationStockAlert(Medication medication, int alertQty);

    int getMedicationStockAlert(Medication medication);
}
