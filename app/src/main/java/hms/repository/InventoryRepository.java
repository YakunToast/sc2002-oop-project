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

    /**
     * @return Inventory
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    
    /** 
     * @return List<ReplenishmentRequest>
     */
    public List<ReplenishmentRequest> getReplenishmentRequests() {
        return List.copyOf(this.replenishmentRequest);
    }

    
    /** 
     * @param rr
     */
    public void addReplenishmentRequest(ReplenishmentRequest rr) {
        this.replenishmentRequest.add(rr);
    }

    
    /** 
     * @param rr
     */
    public void removeReplenishmentRequest(ReplenishmentRequest rr) {
        this.replenishmentRequest.remove(rr);
    }
}
