package hms.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import hms.model.medication.Inventory;
import hms.model.medication.Medication;
import hms.model.medication.ReplenishmentRequest;
import hms.repository.RepositoryManager;

/**
 * The InventoryController class manages the inventory operations of the hospital's medication
 * system. It provides methods to add, retrieve, and update medication information, as well as
 * manage stock levels and replenishment requests.
 */
public class InventoryController {
    private final Inventory inventory;

    /**
     * Constructs an instance of InventoryController, initializing the inventory with the data from
     * the repository.
     */
    public InventoryController() {
        this.inventory = RepositoryManager.getInstance().getInventoryRepository().getInventory();
    }

    /**
     * Retrieves the entire inventory.
     *
     * @return The Inventory object representing the current state of the medication inventory.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Retrieves the list of all medications in the inventory.
     *
     * @return A list of Medication objects representing the medications in the inventory.
     */
    public List<Medication> getMedications() {
        return inventory.getMedications();
    }

    /**
     * Retrieves a medication by its name.
     *
     * @param name The name of the medication to retrieve.
     * @return An Optional containing the Medication object if found, or an empty Optional if not
     *     found.
     */
    public Optional<Medication> getMedicationByName(String name) {
        return inventory.getMedicationByName(name);
    }

    /**
     * Retrieves a medication by its UUID.
     *
     * @param uuid The UUID of the medication to retrieve.
     * @return An Optional containing the Medication object if found, or an empty Optional if not
     *     found.
     */
    public Optional<Medication> getMedicationByUUID(UUID uuid) {
        return inventory.getMedicationByUUID(uuid);
    }

    /**
     * Retrieves a medication by its UUID represented as a string.
     *
     * @param uuid The UUID of the medication to retrieve, as a string.
     * @return An Optional containing the Medication object if found, or an empty Optional if not
     *     found.
     */
    public Optional<Medication> getMedicationByUUID(String uuid) {
        return inventory.getMedicationByUUID(UUID.fromString(uuid));
    }

    /**
     * Adds a replenishment request to the inventory.
     *
     * @param rr The ReplenishmentRequest object to be added.
     * @return true if the request was added successfully, false otherwise.
     */
    public boolean addReplenishmentRequest(ReplenishmentRequest rr) {
        RepositoryManager.getInstance().getInventoryRepository().addReplenishmentRequest(rr);
        return true;
    }

    /**
     * Retrieves the stock quantity of a specific medication.
     *
     * @param medication The Medication object for which to retrieve the stock quantity.
     * @return The stock quantity of the specified medication.
     */
    public int getMedicationStock(Medication medication) {
        return this.inventory.getMedicationStock(medication);
    }

    /**
     * Adds a new medication to the inventory.
     *
     * @param medication The Medication object to be added.
     * @return true if the medication was added successfully, false otherwise.
     */
    public boolean addMedication(Medication medication) {
        return this.inventory.addMedication(medication);
    }

    /**
     * Adds a specified quantity of stock to an existing medication in the inventory.
     *
     * @param medication The Medication object to which stock is to be added.
     * @param qty The quantity of stock to be added.
     * @return true if the stock was added successfully, false otherwise.
     */
    public boolean addMedicationStock(Medication medication, int qty) {
        return this.inventory.addMedicationStock(medication, qty);
    }

    /**
     * Sets the stock quantity of a specified medication to a new value.
     *
     * @param medication The Medication object for which to set the stock quantity.
     * @param qty The new stock quantity to be set.
     * @return true if the stock quantity was set successfully, false otherwise.
     */
    public boolean setMedicationStock(Medication medication, int qty) {
        return this.inventory.setMedicationStock(medication, qty);
    }

    /**
     * Removes a medication from the inventory.
     *
     * @param medication The Medication object to be removed.
     * @return true if the medication was removed successfully, false otherwise.
     */
    public boolean removeMedication(Medication medication) {
        return this.inventory.removeMedication(medication);
    }

    /**
     * Removes a specified quantity of stock from an existing medication in the inventory.
     *
     * @param medication The Medication object from which stock is to be removed.
     * @param qty The quantity of stock to be removed.
     * @return true if the stock was removed successfully, false otherwise.
     */
    public boolean removeMedicationStock(Medication medication, int qty) {
        return this.inventory.removeMedicationStock(medication, qty);
    }

    /**
     * Sets the stock alert quantity for a specified medication.
     *
     * @param medication The Medication object for which to set the stock alert quantity.
     * @param alertQty The stock alert quantity to be set.
     * @return true if the stock alert quantity was set successfully, false otherwise.
     */
    public boolean setMedicationStockAlert(Medication medication, int alertQty) {
        return this.inventory.setMedicationStockAlert(medication, alertQty);
    }

    /**
     * Retrieves the stock alert quantity for a specified medication.
     *
     * @param medication The Medication object for which to retrieve the stock alert quantity.
     * @return The stock alert quantity of the specified medication.
     */
    public int getMedicationStockAlert(Medication medication) {
        return this.inventory.getMedicationStockAlert(medication);
    }
}
