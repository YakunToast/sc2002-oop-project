package hms.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import hms.controller.appointment.AppointmentManager;
import hms.controller.inventory.InventoryManager;
import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.model.appointment.AppointmentStatus;
import hms.model.medication.Inventory;
import hms.model.medication.Medication;
import hms.model.medication.ReplenishmentRequest;
import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.Pharmacist;
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

    public Optional<Staff> getStaffById(String id) {
        return this.ur.getUserById(id).filter(u -> u instanceof Staff).map(u -> (Staff) u);
    }

    public Optional<Staff> getStaffByUsername(String username) {
        return this.ur
                .getUserByUsername(username)
                .filter(u -> u instanceof Staff)
                .map(u -> (Staff) u);
    }

    public Optional<Patient> getPatientById(String id) {
        return this.ur.getUserById(id).filter(u -> u instanceof Patient).map(u -> (Patient) u);
    }

    public Optional<Patient> getPatientByUsername(String username) {
        return this.ur
                .getUserByUsername(username)
                .filter(u -> u instanceof Patient)
                .map(u -> (Patient) u);
    }

    public Optional<Doctor> getDoctorById(String id) {
        return this.ur.getUserById(id).filter(u -> u instanceof Doctor).map(u -> (Doctor) u);
    }

    public Optional<Doctor> getDoctorByUsername(String username) {
        return this.ur
                .getUserByUsername(username)
                .filter(u -> u instanceof Doctor)
                .map(u -> (Doctor) u);
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

    public List<Pharmacist> getPharmacists() {
        return this.getUsers().stream()
                .filter(u -> u instanceof Pharmacist)
                .map(u -> (Pharmacist) u)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return this.ar.getAllAppointments();
    }

    @Override
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus as) {
        return this.getAllAppointments().stream()
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

    @Override
    public int getMedicationStock(Medication medication) {
        return this.inventoryController.getMedicationStock(medication);
    }

    @Override
    public boolean addMedication(Medication medication) {
        return this.inventoryController.addMedication(medication);
    }

    @Override
    public boolean addMedicationStock(Medication medication, int qty) {
        return this.inventoryController.addMedicationStock(medication, qty);
    }

    @Override
    public boolean setMedicationStock(Medication medication, int qty) {
        return this.inventoryController.setMedicationStock(medication, qty);
    }

    @Override
    public boolean removeMedication(Medication medication) {
        return this.inventoryController.removeMedication(medication);
    }

    @Override
    public boolean removeMedicationStock(Medication medication, int qty) {
        return this.inventoryController.removeMedicationStock(medication, qty);
    }

    @Override
    public boolean setMedicationStockAlert(Medication medication, int alertQty) {
        return this.inventoryController.setMedicationStockAlert(medication, alertQty);
    }

    @Override
    public int getMedicationStockAlert(Medication medication) {
        return this.inventoryController.getMedicationStockAlert(medication);
    }
}
