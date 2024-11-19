package hms.model.medication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/** Represents an inventory of medications, including stock levels and alert thresholds. */
public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Medication> medications;
    private Map<String, Integer> alert;
    private Map<String, Integer> stock;

    /** Constructs a new empty inventory. */
    public Inventory() {
        this.medications = new HashMap<>();
        this.stock = new HashMap<>();
        this.alert = new HashMap<>();
    }

    /**
     * Adds a new medication to the inventory.
     *
     * @param medication the medication to be added
     * @return true if the medication was successfully added, false if a medication with the same
     *     name already exists
     */
    public boolean addMedication(Medication medication) {
        // Add medication
        if (medications.containsKey(medication.getName())) {
            return false;
        }
        medications.put(medication.getName(), medication);

        // Update stock as well
        if (!stock.containsKey(medication.getName())) {
            stock.put(medication.getName(), 0);
        }

        return true;
    }

    /**
     * Adds a specified quantity of a medication to the inventory stock.
     *
     * @param medication the medication to add stock for
     * @param qty the quantity of medication to add
     * @return true if the stock was successfully updated, false if the quantity was negative
     */
    public boolean addMedicationStock(Medication medication, int qty) {
        if (qty < 0) {
            return false;
        }
        stock.merge(medication.getName(), qty, Integer::sum);
        return true;
    }

    /**
     * Sets the stock level of a medication to a specified quantity.
     *
     * @param medication the medication to set the stock for
     * @param qty the quantity of medication to set
     * @return true if the stock was successfully set, false if the quantity was negative
     */
    public boolean setMedicationStock(Medication medication, int qty) {
        if (qty < 0) {
            return false;
        }
        stock.put(medication.getName(), qty);
        return true;
    }

    /**
     * Removes a medication from the inventory.
     *
     * @param medication the medication to be removed
     * @return true if the medication was successfully removed, false if the medication was not
     *     found
     */
    public boolean removeMedication(Medication medication) {
        // Remove medication
        if (!medications.containsKey(medication.getName())) {
            return false;
        }
        medications.remove(medication.getName());

        // Remove stock as well
        if (stock.containsKey(medication.getName())) {
            stock.remove(medication.getName());
        }
        return true;
    }

    /**
     * Removes a specified quantity of a medication from the inventory stock.
     *
     * @param medication the medication to remove stock for
     * @param qty the quantity of medication to remove
     * @return true if the stock was successfully updated, false if the quantity was positive
     */
    public boolean removeMedicationStock(Medication medication, int qty) {
        if (qty > 0) {
            return false;
        }

        // Make sure we cannot remove more than what's left
        int currentStock = stock.get(medication.getName());
        int delta = Integer.min(currentStock, Math.abs(qty));

        stock.merge(medication.getName(), -delta, Integer::sum);
        return true;
    }

    /**
     * Sets the stock alert threshold for a medication.
     *
     * @param medication the medication to set the alert threshold for
     * @param qty the alert threshold quantity
     * @return true if the alert threshold was successfully set, false if the quantity was
     *     non-positive
     */
    public boolean setMedicationStockAlert(Medication medication, int qty) {
        if (qty <= 0) {
            return false;
        }
        this.alert.put(medication.getName(), qty);
        return true;
    }

    /**
     * Retrieves the stock alert threshold for a medication.
     *
     * @param medication the medication to retrieve the alert threshold for
     * @return the stock alert threshold quantity
     * @throws NullPointerException if the medication is not found in the inventory
     */
    public int getMedicationStockAlert(Medication medication) {
        return this.alert.get(medication.getName());
    }

    /**
     * Retrieves a list of all medications in the inventory.
     *
     * @return a list of all medications in the inventory
     */
    public List<Medication> getMedications() {
        return List.copyOf(this.medications.values());
    }

    /**
     * Retrieves a medication by its name.
     *
     * @param name the name of the medication to retrieve
     * @return an Optional containing the medication if found, or an empty Optional if not found
     */
    public Optional<Medication> getMedicationByName(String name) {
        return Optional.ofNullable(this.medications.get(name));
    }

    /**
     * Retrieves a medication by its UUID.
     *
     * @param id the UUID of the medication to retrieve
     * @return an Optional containing the medication if found, or an empty Optional if not found
     */
    public Optional<Medication> getMedicationByUUID(UUID id) {
        return this.medications.values().stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    /**
     * Retrieves the stock level of a medication.
     *
     * @param medication the medication to retrieve the stock level for
     * @return the stock level of the medication
     * @throws NullPointerException if the medication is not found in the inventory
     */
    public int getMedicationStock(Medication medication) {
        return this.stock.get(medication.getName());
    }
}
