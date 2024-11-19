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

/**
 * Manages and display hospital staff by adding, updating, or removing staff members.
 * Manages appointment by accessing real-time updates of scheduled appointments.
 * Manages inventory of medication by adding, removing or updating stock levels, update the low stock level alert line of each medicine, and approve replenishment requests from pharmacists. 
 * 
 * @author AMOS NG ZHENG JIE
 * @author GILBERT ADRIEL TANTOSO
 * @author KUO EUGENE
 * @author RESWARA ANARGYA DZAKIRULLAH
 * @author THEODORE AMADEO ARGASETYA ATMADJA
 * @version 1.0
 * @since 2024-11-19
 */
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

    /**
     * The methods to add a new user
     * @param user
     * @return boolean
     */
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

    /**
     * The methods to remove an existing user
     * @param user
     * @return boolean
     */
    public boolean removeUser(User user) {
        return this.ur.removeUser(user);
    }

    /**
     * The methods to get all users from user repository
     * @return List<User>
     */
    public List<User> getUsers() {
        return this.ur.getAllUsers();
    }

    /**
     * The methods to get staff by ID from user repository
     * @param id
     * @return Optional<Staff>
     */
    public Optional<Staff> getStaffById(String id) {
        return this.ur.getUserById(id).filter(u -> u instanceof Staff).map(u -> (Staff) u);
    }

    /**
     * The methods to get staff by username from user repository
     * @param username
     * @return Optional<Staff>
     */
    public Optional<Staff> getStaffByUsername(String username) {
        return this.ur
                .getUserByUsername(username)
                .filter(u -> u instanceof Staff)
                .map(u -> (Staff) u);
    }

    /**
     * The methods to get patient by ID from user repository
     * @param id
     * @return Optional<Patient>
     */
    public Optional<Patient> getPatientById(String id) {
        return this.ur.getUserById(id).filter(u -> u instanceof Patient).map(u -> (Patient) u);
    }

    /**
     * The methods to get patient by username from user repository
     * @param username
     * @return Optional<Patient>
     */
    public Optional<Patient> getPatientByUsername(String username) {
        return this.ur
                .getUserByUsername(username)
                .filter(u -> u instanceof Patient)
                .map(u -> (Patient) u);
    }

    /**
     * The methods to get doctor by ID from user repository
     * @param id
     * @return Optional<Doctor>
     */
    public Optional<Doctor> getDoctorById(String id) {
        return this.ur.getUserById(id).filter(u -> u instanceof Doctor).map(u -> (Doctor) u);
    }

    /**
     * The methods to get doctor by username from user repository
     * @param username
     * @return Optional<Doctor>
     */
    public Optional<Doctor> getDoctorByUsername(String username) {
        return this.ur
                .getUserByUsername(username)
                .filter(u -> u instanceof Doctor)
                .map(u -> (Doctor) u);
    }

    /**
     * The methods to get all staffs from user repository
     * @return List<Staff>
     */
    public List<Staff> getStaffs() {
        return this.getUsers().stream()
                .filter(u -> u instanceof Staff)
                .map(u -> (Staff) u)
                .collect(Collectors.toList());
    }

    /**
     * The methods to get all patient from user repository
     * @return List<Patient>
     */
    public List<Patient> getPatients() {
        return this.getUsers().stream()
                .filter(u -> u instanceof Patient)
                .map(u -> (Patient) u)
                .collect(Collectors.toList());
    }

    /**
     * The methods to get all doctor from user repository
     * @return List<Doctor>
     */
    public List<Doctor> getDoctors() {
        return this.getUsers().stream()
                .filter(u -> u instanceof Doctor)
                .map(u -> (Doctor) u)
                .collect(Collectors.toList());
    }

    /**
     * The methods to get all pharmacist from user repository
     * @return List<Pharmacist>
     */
    public List<Pharmacist> getPharmacists() {
        return this.getUsers().stream()
                .filter(u -> u instanceof Pharmacist)
                .map(u -> (Pharmacist) u)
                .collect(Collectors.toList());
    }

    /**
     * The methods to get all appointments from appointment repository
     * @return List<Appointment>
     */
    @Override
    public List<Appointment> getAllAppointments() {
        return this.ar.getAllAppointments();
    }

    /**
     * The methods to get appointments by its status from appointment repository
     * @param as
     * @return List<Appointment>
     */
    @Override
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus as) {
        return this.getAllAppointments().stream()
                .filter(ap -> ap.getStatus() == as)
                .collect(Collectors.toList());
    }

    /**
     * The methods to get appointments by ID from appointment repository
     * @param id
     * @return Optional<Appointment>
     */
    @Override
    public Optional<Appointment> getAppointmentById(int id) {
        return this.ar.getAppointmentById(id);
    }

    /**
     * The methods to get appointment outcome
     * @param ap
     * @return AppointmentOutcome
     */
    @Override
    public AppointmentOutcome getAppointmentOutcome(Appointment ap) {
        return ap.getOutcome();
    }

    /**
     * The methods to get inventory repository
     * @return Inventory
     */
    @Override
    public Inventory getInventory() {
        return this.inventoryController.getInventory();
    }

    /**
     * The methods to get medication from inventory repository
     * @return List<Medication>
     */
    @Override
    public List<Medication> getMedications() {
        return this.inventoryController.getMedications();
    }

    /**
     * The methods to get medication by its name from inventory repository
     * @param name
     * @return Optional<Medication>
     */
    @Override
    public Optional<Medication> getMedicationByName(String name) {
        return this.inventoryController.getMedicationByName(name);
    }

    /**
     * The methods to get medication by ID from inventory repository
     * @param uuid
     * @return Optional<Medication>
     */
    @Override
    public Optional<Medication> getMedicationByUUID(UUID uuid) {
        return this.inventoryController.getMedicationByUUID(uuid);
    }

    /** The methods to get medication by ID from inventory repository
     * @param uuid
     * @return Optional<Medication>
     */
    @Override
    public Optional<Medication> getMedicationByUUID(String uuid) {
        return this.inventoryController.getMedicationByUUID(uuid);
    }

    /**
     * The methods to approve replenishment request from pharmacists
     * @param rr
     * @return boolean
     */
    @Override
    public boolean approveReplenishmentRequest(ReplenishmentRequest rr) {
        // Add stock
        this.inventoryController.addMedicationStock(rr.getMedication(), rr.getRequestedQuantity());

        // Mark as approved
        rr.setApproved();
        return true;
    }

    /**
     * The methods to get all replenishment requests that are pending
     * @return List<ReplenishmentRequest>
     */
    @Override
    public List<ReplenishmentRequest> getPendingReplenishmentRequests() {
        return RepositoryManager.getInstance()
                .getInventoryRepository()
                .getReplenishmentRequests()
                .stream()
                .filter(rr -> rr.isPending())
                .collect(Collectors.toList());
    }

    /**
     * The methods to get medication stock from inventory repository
     * @param medication
     * @return int
     */
    @Override
    public int getMedicationStock(Medication medication) {
        return this.inventoryController.getMedicationStock(medication);
    }

    /**
     * The methods to add a new medication
     * @param medication
     * @return boolean
     */
    @Override
    public boolean addMedication(Medication medication) {
        return this.inventoryController.addMedication(medication);
    }

    /**
     * The methods to add new medication stock
     * @param medication
     * @param qty
     * @return boolean
     */
    @Override
    public boolean addMedicationStock(Medication medication, int qty) {
        return this.inventoryController.addMedicationStock(medication, qty);
    }

    /**
     * The methods to set medication stock
     * @param medication
     * @param qty
     * @return boolean
     */
    @Override
    public boolean setMedicationStock(Medication medication, int qty) {
        return this.inventoryController.setMedicationStock(medication, qty);
    }

    /**
     * The methods to remove medication 
     * @param medication
     * @return boolean
     */
    @Override
    public boolean removeMedication(Medication medication) {
        return this.inventoryController.removeMedication(medication);
    }

    /**
     * The methods to remove medication stock
     * @param medication
     * @param qty
     * @return boolean
     */
    @Override
    public boolean removeMedicationStock(Medication medication, int qty) {
        return this.inventoryController.removeMedicationStock(medication, qty);
    }

    /**
     * The methods to set medication stock alert
     * @param medication
     * @param alertQty
     * @return boolean
     */
    @Override
    public boolean setMedicationStockAlert(Medication medication, int alertQty) {
        return this.inventoryController.setMedicationStockAlert(medication, alertQty);
    }

    /**
     * The methods to get medication stock allert
     * @param medication
     * @return int
     */
    @Override
    public int getMedicationStockAlert(Medication medication) {
        return this.inventoryController.getMedicationStockAlert(medication);
    }
}
