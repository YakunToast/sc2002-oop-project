package hms.repository;

import hms.model.medication.Inventory;

public class InventoryRepository extends BaseRepository {
    private static Inventory inventory;

    public InventoryRepository() {
        inventory = new Inventory();
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
