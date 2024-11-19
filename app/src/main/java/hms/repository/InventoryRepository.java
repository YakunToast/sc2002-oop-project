package hms.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hms.model.medication.Inventory;
import hms.model.medication.ReplenishmentRequest;

/**
 * This class extends the {@link BaseRepository} and manages an inventory of medications and related
 * replenishment requests.
 */
public class InventoryRepository extends BaseRepository {
    /** The current inventory of medications. */
    private Inventory inventory;

    /** A set of replenishment requests for medications. */
    private Set<ReplenishmentRequest> replenishmentRequest;

    /**
     * Constructs an instance of {@link InventoryRepository} with an empty inventory and an empty
     * set of replenishment requests.
     */
    public InventoryRepository() {
        this.inventory = new Inventory();
        this.replenishmentRequest = new HashSet<>();
    }

    /**
     * Returns the current inventory of medications.
     *
     * @return the current inventory of medications
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Returns a list of all replenishment requests.
     *
     * <p>The list is a copy of the internal set of replenishment requests.
     *
     * @return a list of all replenishment requests
     */
    public List<ReplenishmentRequest> getReplenishmentRequests() {
        return List.copyOf(this.replenishmentRequest);
    }

    /**
     * Adds a new replenishment request to the set of replenishment requests.
     *
     * @param rr the replenishment request to add
     */
    public void addReplenishmentRequest(ReplenishmentRequest rr) {
        this.replenishmentRequest.add(rr);
    }

    /**
     * Removes a replenishment request from the set of replenishment requests.
     *
     * @param rr the replenishment request to remove
     */
    public void removeReplenishmentRequest(ReplenishmentRequest rr) {
        this.replenishmentRequest.remove(rr);
    }
}
