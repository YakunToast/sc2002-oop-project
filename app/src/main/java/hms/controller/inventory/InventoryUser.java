package hms.controller.inventory;

import hms.model.medication.Medication;
import hms.model.medication.ReplenishmentRequest;

public interface InventoryUser extends InventoryBase {
    ReplenishmentRequest createReplenishmentRequest(Medication m, int qty);
}
