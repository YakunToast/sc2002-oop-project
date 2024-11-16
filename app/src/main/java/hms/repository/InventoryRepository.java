package hms.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hms.model.medication.Inventory;
import hms.model.medication.ReplenishmentRequest;

public class InventoryRepository extends BaseRepository {
    private Inventory inventory;
    private Set<ReplenishmentRequest> replenishmentRequest;

    public InventoryRepository() {
        this.inventory = new Inventory();
        this.replenishmentRequest = new HashSet<>();
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public List<ReplenishmentRequest> getReplenishmentRequests() {
        return List.copyOf(this.replenishmentRequest);
    }

    public void addReplenishmentRequest(ReplenishmentRequest rr) {
        this.replenishmentRequest.add(rr);
    }

    public void removeReplenishmentRequest(ReplenishmentRequest rr) {
        this.replenishmentRequest.remove(rr);
    }
}
