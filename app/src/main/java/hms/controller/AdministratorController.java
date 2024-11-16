package hms.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import hms.controller.appointment.AppointmentManager;
import hms.controller.inventory.InventoryManager;
import hms.model.appointment.Appointment;
import hms.model.appointment.Appointment.AppointmentStatus;
import hms.model.appointment.AppointmentOutcome;
import hms.model.medication.Inventory;
import hms.model.medication.Medication;
import hms.model.medication.ReplenishmentRequest;
import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.Staff;
import hms.model.user.User;
import hms.repository.AppointmentRepository;
import hms.repository.RepositoryManager;
import hms.repository.UserRepository;

public class AdministratorController implements InventoryManager, AppointmentManager {
    private final Administrator administrator;

    private final UserRepository ur;
    private final AppointmentRepository ar;

    private final InventoryController inventoryController;

    public AdministratorController(Administrator administrator) {
        this.administrator = administrator;

        this.ur = RepositoryManager.getInstance().getUserRepository();
        this.ar = RepositoryManager.getInstance().getAppointmentRepository();

        this.inventoryController = new InventoryController();
    }

    public boolean addUser(User user) {
        if (this.ur.getUserById(user.getId()).isPresent()) {
            return false;
        }
        if (this.ur.getUserByUsername(user.getUsername()).isPresent()) {
            return false;
        }

        this.ur.addUser(user);

        return true;
    }

    public boolean removeUser(User user) {
        return this.ur.removeUser(user);
    }

    public List<User> getUsers() {
        return this.ur.getAllUsers();
    }

    public List<Staff> getStaffs() {
        return this.getUsers().stream()
                .filter(u -> u instanceof Staff)
                .map(u -> (Staff) u)
                .collect(Collectors.toList());
    }

    public List<Patient> getPatients() {
        return this.getUsers().stream()
                .filter(u -> u instanceof Patient)
                .map(u -> (Patient) u)
                .collect(Collectors.toList());
    }

    public List<Doctor> getDoctors() {
        return this.getUsers().stream()
                .filter(u -> u instanceof Doctor)
                .map(u -> (Doctor) u)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getAppointments() {
        return this.ar.getAllAppointments();
    }

    @Override
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus as) {
        return this.getAppointments().stream()
                .filter(ap -> ap.getStatus() == as)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Appointment> getAppointmentById(UUID id) {
        return this.ar.getAppointmentById(id);
    }

    @Override
    public AppointmentOutcome getAppointmentOutcome(Appointment ap) {
        return ap.getOutcome();
    }

    @Override
    public Inventory getInventory() {
        return this.inventoryController.getInventory();
    }

    @Override
    public List<Medication> getMedications() {
        return this.inventoryController.getMedications();
    }

    @Override
    public boolean approveReplenishmentRequest(ReplenishmentRequest rr) {
        // Add stock
        this.inventoryController.addMedicationStock(rr.getMedication(), rr.getRequestedQuantity());

        // Mark as approved
        rr.setApproved();
        return true;
    }

    @Override
    public List<ReplenishmentRequest> getPendingReplenishmentRequests() {
        return RepositoryManager.getInstance()
                .getInventoryRepository()
                .getReplenishmentRequests()
                .stream()
                .filter(rr -> rr.isPending())
                .collect(Collectors.toList());
    }
}
