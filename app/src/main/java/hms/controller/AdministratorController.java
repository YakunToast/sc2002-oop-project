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
 * Manages and displays hospital staff by adding, updating, or removing staff members. Manages
 * appointments by accessing real-time updates of scheduled appointments. Manages inventory of
 * medication by adding, removing, or updating stock levels, updating the low stock level alert line
 * of each medicine, and approving replenishment requests from pharmacists.
 */
public class AdministratorController implements InventoryManager, AppointmentManager {
    private final Administrator administrator;

    private final UserRepository ur;
    private final AppointmentRepository ar;

    private final InventoryController inventoryController;

    /**
     * Constructor for the AdministratorController.
     *
     * @param administrator the administrator user who will manage the system
     */
    public AdministratorController(Administrator administrator) {
        this.administrator = administrator;

        this.ur = RepositoryManager.getInstance().getUserRepository();
        this.ar = RepositoryManager.getInstance().getAppointmentRepository();

        this.inventoryController = new InventoryController();
    }

    /**
     * Adds a new user to the system.
     *
     * @param user the user to be added
     * @return true if the user is successfully added, false if the user ID or username already
     *     exists
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
     * Removes an existing user from the system.
     *
     * @param user the user to be removed
     * @return true if the user is successfully removed, false if the user does not exist
     */
    public boolean removeUser(User user) {
        return this.ur.removeUser(user);
    }

    /**
     * Retrieves all users in the system.
     *
     * @return a list of all users
     */
    public List<User> getUsers() {
        return this.ur.getAllUsers();
    }

    /**
     * Retrieves a staff member by their ID.
     *
     * @param id the ID of the staff member
     * @return an optional containing the staff member if found, otherwise empty
     */
    public Optional<Staff> getStaffById(String id) {
        return this.ur.getUserById(id).filter(u -> u instanceof Staff).map(u -> (Staff) u);
    }

    /**
     * Retrieves a staff member by their username.
     *
     * @param username the username of the staff member
     * @return an optional containing the staff member if found, otherwise empty
     */
    public Optional<Staff> getStaffByUsername(String username) {
        return this.ur
                .getUserByUsername(username)
                .filter(u -> u instanceof Staff)
                .map(u -> (Staff) u);
    }

    /**
     * Retrieves a patient by their ID.
     *
     * @param id the ID of the patient
     * @return an optional containing the patient if found, otherwise empty
     */
    public Optional<Patient> getPatientById(String id) {
        return this.ur.getUserById(id).filter(u -> u instanceof Patient).map(u -> (Patient) u);
    }

    /**
     * Retrieves a patient by their username.
     *
     * @param username the username of the patient
     * @return an optional containing the patient if found, otherwise empty
     */
    public Optional<Patient> getPatientByUsername(String username) {
        return this.ur
                .getUserByUsername(username)
                .filter(u -> u instanceof Patient)
                .map(u -> (Patient) u);
    }

    /**
     * Retrieves a doctor by their ID.
     *
     * @param id the ID of the doctor
     * @return an optional containing the doctor if found, otherwise empty
     */
    public Optional<Doctor> getDoctorById(String id) {
        return this.ur.getUserById(id).filter(u -> u instanceof Doctor).map(u -> (Doctor) u);
    }

    /**
     * Retrieves a doctor by their username.
     *
     * @param username the username of the doctor
     * @return an optional containing the doctor if found, otherwise empty
     */
    public Optional<Doctor> getDoctorByUsername(String username) {
        return this.ur
                .getUserByUsername(username)
                .filter(u -> u instanceof Doctor)
                .map(u -> (Doctor) u);
    }

    /**
     * Retrieves all staff members in the system.
     *
     * @return a list of all staff members
     */
    public List<Staff> getStaffs() {
        return this.getUsers().stream()
                .filter(u -> u instanceof Staff)
                .map(u -> (Staff) u)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all patients in the system.
     *
     * @return a list of all patients
     */
    public List<Patient> getPatients() {
        return this.getUsers().stream()
                .filter(u -> u instanceof Patient)
                .map(u -> (Patient) u)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all doctors in the system.
     *
     * @return a list of all doctors
     */
    public List<Doctor> getDoctors() {
        return this.getUsers().stream()
                .filter(u -> u instanceof Doctor)
                .map(u -> (Doctor) u)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all pharmacists in the system.
     *
     * @return a list of all pharmacists
     */
    public List<Pharmacist> getPharmacists() {
        return this.getUsers().stream()
                .filter(u -> u instanceof Pharmacist)
                .map(u -> (Pharmacist) u)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all appointments in the system.
     *
     * @return a list of all appointments
     */
    @Override
    public List<Appointment> getAllAppointments() {
        return this.ar.getAllAppointments();
    }

    /**
     * Retrieves all appointments by their status.
     *
     * @param as the appointment status to filter by
     * @return a list of appointments with the specified status
     */
    @Override
    public List<Appointment> getAllAppointmentsByStatus(AppointmentStatus as) {
        return this.ar.getAllAppointments().stream()
                .filter(ap -> ap.getState().getStatus() == as)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id the ID of the appointment
     * @return an optional containing the appointment if found, otherwise empty
     */
    @Override
    public Optional<Appointment> getAppointmentById(int id) {
        return this.ar.getAppointmentById(id);
    }

    /**
     * Retrieves the outcome of an appointment.
     *
     * @param ap the appointment to retrieve the outcome from
     * @return the appointment outcome
     */
    @Override
    public AppointmentOutcome getAppointmentOutcome(Appointment ap) {
        return ap.getOutcome();
    }

    /**
     * Retrieves the inventory repository.
     *
     * @return the inventory repository
     */
    @Override
    public Inventory getInventory() {
        return this.inventoryController.getInventory();
    }

    /**
     * Retrieves all medications in the inventory.
     *
     * @return a list of all medications
     */
    @Override
    public List<Medication> getMedications() {
        return this.inventoryController.getMedications();
    }

    /**
     * Retrieves a medication by its name.
     *
     * @param name the name of the medication
     * @return an optional containing the medication if found, otherwise empty
     */
    @Override
    public Optional<Medication> getMedicationByName(String name) {
        return this.inventoryController.getMedicationByName(name);
    }

    /**
     * Retrieves a medication by its UUID.
     *
     * @param uuid the UUID of the medication
     * @return an optional containing the medication if found, otherwise empty
     */
    @Override
    public Optional<Medication> getMedicationByUUID(UUID uuid) {
        return this.inventoryController.getMedicationByUUID(uuid);
    }

    /**
     * Retrieves a medication by its UUID as a string.
     *
     * @param uuid the UUID of the medication as a string
     * @return an optional containing the medication if found, otherwise empty
     */
    @Override
    public Optional<Medication> getMedicationByUUID(String uuid) {
        return this.inventoryController.getMedicationByUUID(uuid);
    }

    /**
     * Approves a replenishment request from a pharmacist.
     *
     * @param rr the replenishment request to approve
     * @return true if the request is successfully approved, false otherwise
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
     * Retrieves all pending replenishment requests.
     *
     * @return a list of all pending replenishment requests
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
     * Retrieves the stock level of a medication.
     *
     * @param medication the medication to retrieve the stock level for
     * @return the stock level of the medication
     */
    @Override
    public int getMedicationStock(Medication medication) {
        return this.inventoryController.getMedicationStock(medication);
    }

    /**
     * Adds a new medication to the inventory.
     *
     * @param medication the medication to add
     * @return true if the medication is successfully added, false otherwise
     */
    @Override
    public boolean addMedication(Medication medication) {
        return this.inventoryController.addMedication(medication);
    }

    /**
     * Adds stock to an existing medication in the inventory.
     *
     * @param medication the medication to add stock to
     * @param qty the quantity of stock to add
     * @return true if the stock is successfully added, false otherwise
     */
    @Override
    public boolean addMedicationStock(Medication medication, int qty) {
        return this.inventoryController.addMedicationStock(medication, qty);
    }

    /**
     * Sets the stock level of a medication in the inventory.
     *
     * @param medication the medication to set the stock level for
     * @param qty the new stock level to set
     * @return true if the stock level is successfully set, false otherwise
     */
    @Override
    public boolean setMedicationStock(Medication medication, int qty) {
        return this.inventoryController.setMedicationStock(medication, qty);
    }

    /**
     * Removes a medication from the inventory.
     *
     * @param medication the medication to remove
     * @return true if the medication is successfully removed, false otherwise
     */
    @Override
    public boolean removeMedication(Medication medication) {
        return this.inventoryController.removeMedication(medication);
    }

    /**
     * Removes stock from an existing medication in the inventory.
     *
     * @param medication the medication to remove stock from
     * @param qty the quantity of stock to remove
     * @return true if the stock is successfully removed, false otherwise
     */
    @Override
    public boolean removeMedicationStock(Medication medication, int qty) {
        return this.inventoryController.removeMedicationStock(medication, qty);
    }

    /**
     * Sets the stock alert level for a medication in the inventory.
     *
     * @param medication the medication to set the stock alert level for
     * @param alertQty the stock alert level to set
     * @return true if the stock alert level is successfully set, false otherwise
     */
    @Override
    public boolean setMedicationStockAlert(Medication medication, int alertQty) {
        return this.inventoryController.setMedicationStockAlert(medication, alertQty);
    }

    /**
     * Retrieves the stock alert level of a medication.
     *
     * @param medication the medication to retrieve the stock alert level for
     * @return the stock alert level of the medication
     */
    @Override
    public int getMedicationStockAlert(Medication medication) {
        return this.inventoryController.getMedicationStockAlert(medication);
    }
}
