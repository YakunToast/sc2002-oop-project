package hms.controller.inventory;

import java.util.List;

import hms.model.medication.ReplenishmentRequest;

public interface InventoryManager extends InventoryBase {
    boolean approveReplenishmentRequest(ReplenishmentRequest replenishmentRequest);

    List<ReplenishmentRequest> getPendingReplenishmentRequests();
}
