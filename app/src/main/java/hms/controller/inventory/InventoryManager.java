package hms.controller.inventory;

import java.util.List;

import hms.model.medication.Medication;
import hms.model.medication.ReplenishmentRequest;

public interface InventoryManager extends InventoryBase {
    boolean approveReplenishmentRequest(ReplenishmentRequest replenishmentRequest);

    List<ReplenishmentRequest> getPendingReplenishmentRequests();

    public int getMedicationStock(Medication medication);

    public boolean addMedication(Medication medication);

    public boolean addMedicationStock(Medication medication, int qty);

    public boolean removeMedication(Medication medication);

    public boolean removeMedicationStock(Medication medication, int qty);

    public boolean setMedicationStockAlert(Medication medication, int alertQty);
}
