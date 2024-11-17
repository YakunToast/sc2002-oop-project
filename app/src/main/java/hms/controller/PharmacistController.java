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

public class PharmacistController implements InventoryUser {
    private final Pharmacist pharmacist;
    private final InventoryController inventoryController;

    public PharmacistController(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
        this.inventoryController = new InventoryController();
    }

    public List<AppointmentOutcome> getAppointmentOutcomes() {
        return AppointmentController.getAppointmentOutcomes();
    }

    public List<Prescription> getPendingPrescriptions() {
        return getAppointmentOutcomes().stream()
                .map(ao -> ao.getPrescription())
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

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

    public boolean updatePrescriptionStatus(Prescription p, PrescriptionStatus ps) {
        switch (ps) {
            case PrescriptionStatus.PENDING -> p.setPending();
            case PrescriptionStatus.DISPENSED -> p.setDispensed();
            case PrescriptionStatus.CANCELLED -> p.setCancelled();
        }
        // TODO: Need to find a way to better return status
        return true;
    }

    @Override
    public Inventory getInventory() {
        return this.inventoryController.getInventory();
    }

    @Override
    public int getMedicationStock(Medication medication) {
        return this.inventoryController.getMedicationStock(medication);
    }

    @Override
    public int getMedicationStockAlert(Medication medication) {
        return this.inventoryController.getMedicationStockAlert(medication);
    }

    @Override
    public List<Medication> getMedications() {
        return this.inventoryController.getMedications();
    }

    @Override
    public Optional<Medication> getMedicationByName(String name) {
        return this.inventoryController.getMedicationByName(name);
    }

    @Override
    public Optional<Medication> getMedicationByUUID(UUID uuid) {
        return this.inventoryController.getMedicationByUUID(uuid);
    }

    @Override
    public Optional<Medication> getMedicationByUUID(String uuid) {
        return this.inventoryController.getMedicationByUUID(uuid);
    }

    @Override
    public ReplenishmentRequest createReplenishmentRequest(Medication m, int qty) {
        ReplenishmentRequest rr =
                new ReplenishmentRequest(m, qty, LocalDateTime.now(), this.pharmacist);
        this.inventoryController.addReplenishmentRequest(rr);
        return rr;
    }
}
