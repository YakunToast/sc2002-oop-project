package hms.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import hms.controller.inventory.InventoryUser;
import hms.model.appointment.AppointmentOutcome;
import hms.model.medication.Inventory;
import hms.model.medication.Medication;
import hms.model.medication.Prescription;
import hms.model.medication.PrescriptionStatus;
import hms.model.medication.ReplenishmentRequest;
import hms.model.user.Pharmacist;

/**
 * A controller class for handling operations related to pharmacists. It provides methods to manage
 * prescriptions, dispense medications, cancel prescriptions, update prescription statuses, and
 * manage inventory.
 */
public class PharmacistController implements InventoryUser {

    private final Pharmacist pharmacist;
    private final InventoryController inventoryController;

    /**
     * Constructs a new PharmacistController instance with the given pharmacist.
     *
     * @param pharmacist The pharmacist for whom this controller is handling operations.
     */
    public PharmacistController(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
        this.inventoryController = new InventoryController();
    }

    /**
     * Retrieves a list of all appointment outcomes.
     *
     * @return A list of AppointmentOutcome objects.
     */
    public List<AppointmentOutcome> getAppointmentOutcomes() {
        return AppointmentController.getAppointmentOutcomes();
    }

    /**
     * Retrieves a list of all pending prescriptions.
     *
     * @return A list of Prescription objects that are pending.
     */
    public List<Prescription> getPendingPrescriptions() {
        return getAppointmentOutcomes().stream()
                .map(ao -> ao.getPrescription())
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    /**
     * Dispenses a given prescription by removing the required medications from inventory.
     *
     * @param p The prescription to be dispensed.
     * @return true if the prescription was successfully dispensed, false if it was already
     *     dispensed.
     */
    public boolean dispensePrescription(Prescription p) {
        if (p.isDispensed()) {
            return false;
        }

        for (Map.Entry<Medication, Integer> entry : p.getMedications().entrySet()) {
            Medication key = entry.getKey();
            Integer value = entry.getValue();
            inventoryController.removeMedicationStock(key, value);
        }
        p.setDispensed();
        return true;
    }

    /**
     * Cancels a given prescription by returning the medications back to inventory.
     *
     * @param p The prescription to be cancelled.
     * @return true if the prescription was successfully cancelled, false if it was already
     *     cancelled.
     */
    public boolean cancelPrescription(Prescription p) {
        if (p.isCancelled()) {
            return false;
        }

        for (Map.Entry<Medication, Integer> entry : p.getMedications().entrySet()) {
            Medication key = entry.getKey();
            Integer value = entry.getValue();
            inventoryController.addMedicationStock(key, value);
        }
        p.setCancelled();
        return true;
    }

    /**
     * Updates the status of a given prescription.
     *
     * @param p The prescription whose status is to be updated.
     * @param ps The new status of the prescription.
     * @return true if the status was successfully updated.
     */
    public boolean updatePrescriptionStatus(Prescription p, PrescriptionStatus ps) {
        switch (ps) {
            case PENDING -> p.setPending();
            case DISPENSED -> p.setDispensed();
            case CANCELLED -> p.setCancelled();
        }
        return true;
    }

    /**
     * Retrieves the current inventory.
     *
     * @return The Inventory object representing the current state of the inventory.
     */
    @Override
    public Inventory getInventory() {
        return this.inventoryController.getInventory();
    }

    /**
     * Retrieves the stock level of a specific medication.
     *
     * @param medication The medication for which to check the stock level.
     * @return The current stock level of the medication.
     */
    @Override
    public int getMedicationStock(Medication medication) {
        return this.inventoryController.getMedicationStock(medication);
    }

    /**
     * Retrieves the stock alert level of a specific medication.
     *
     * @param medication The medication for which to check the stock alert level.
     * @return The current stock alert level of the medication.
     */
    @Override
    public int getMedicationStockAlert(Medication medication) {
        return this.inventoryController.getMedicationStockAlert(medication);
    }

    /**
     * Retrieves a list of all medications in the inventory.
     *
     * @return A list of all Medication objects in the inventory.
     */
    @Override
    public List<Medication> getMedications() {
        return this.inventoryController.getMedications();
    }

    /**
     * Retrieves a medication by its name.
     *
     * @param name The name of the medication to retrieve.
     * @return An Optional containing the Medication object if found, or an empty Optional if not
     *     found.
     */
    @Override
    public Optional<Medication> getMedicationByName(String name) {
        return this.inventoryController.getMedicationByName(name);
    }

    /**
     * Retrieves a medication by its UUID.
     *
     * @param uuid The UUID of the medication to retrieve.
     * @return An Optional containing the Medication object if found, or an empty Optional if not
     *     found.
     */
    @Override
    public Optional<Medication> getMedicationByUUID(UUID uuid) {
        return this.inventoryController.getMedicationByUUID(uuid);
    }

    /**
     * Retrieves a medication by its UUID, specified as a string.
     *
     * @param uuid The UUID of the medication to retrieve, as a string.
     * @return An Optional containing the Medication object if found, or an empty Optional if not
     *     found.
     */
    @Override
    public Optional<Medication> getMedicationByUUID(String uuid) {
        return this.inventoryController.getMedicationByUUID(uuid);
    }

    /**
     * Creates a replenishment request for a specific medication.
     *
     * @param m The medication for which to create a replenishment request.
     * @param qty The quantity of the medication to be replenished.
     * @return The ReplenishmentRequest object that was created.
     */
    @Override
    public ReplenishmentRequest createReplenishmentRequest(Medication m, int qty) {
        ReplenishmentRequest rr =
                new ReplenishmentRequest(m, qty, LocalDateTime.now(), this.pharmacist);
        this.inventoryController.addReplenishmentRequest(rr);
        return rr;
    }
}
