package hms.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hms.model.appointment.AppointmentOutcome;
import hms.model.medication.Inventory;
import hms.model.medication.Medication;
import hms.model.medication.Prescription;
import hms.model.medication.PrescriptionStatus;
import hms.model.medication.ReplenishmentRequest;
import hms.model.user.Pharmacist;
import hms.repository.RepositoryManager;

public class PharmacistController extends UserController {
    private final Pharmacist pharmacist;
    private final InventoryController inventoryController;

    public PharmacistController(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
        this.inventoryController =
                new InventoryController(
                        RepositoryManager.getInstance().getInventoryRepository().getInventory());
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

    public boolean updatePrescriptionStatus(Prescription p, PrescriptionStatus ps) {
        switch (ps) {
            case PrescriptionStatus.PENDING -> p.setPending();
            case PrescriptionStatus.DISPENSED -> p.setDispensed();
            case PrescriptionStatus.CANCELLED -> p.setCancelled();
        }
        // TODO: Need to find a way to better return status
        return true;
    }

    public Inventory getInventory() {
        return this.inventoryController.getInventory();
    }

    public List<Medication> getMedications() {
        return this.inventoryController.getMedications();
    }

    public ReplenishmentRequest createReplenishmentRequest(Medication m, int qty) {
        ReplenishmentRequest rr = new ReplenishmentRequest(m, qty);
        this.inventoryController.addReplenishmentRequest(rr);
        return rr;
    }
}