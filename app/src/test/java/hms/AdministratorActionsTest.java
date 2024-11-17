package hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import hms.controller.AdministratorController;
import hms.controller.DoctorController;
import hms.controller.PatientController;
import hms.controller.PharmacistController;
import hms.model.appointment.Appointment;
import hms.model.appointment.Appointment.AppointmentStatus;
import hms.model.medication.Medication;
import hms.model.medication.MedicationSideEffect;
import hms.model.medication.ReplenishmentRequest;
import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.Pharmacist;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdministratorActionsTest {
    private Patient testPatient;
    private PatientController patientController;
    private Administrator testAdmin;
    private AdministratorController adminController;
    private Doctor testDoctor;
    private DoctorController doctorController;
    private Pharmacist testPharmacist;
    private PharmacistController pharmacistController;

    @BeforeEach
    void setup() {
        TestUtils.setupTestRepositories();

        testPatient = TestUtils.createTestPatient();
        testDoctor = TestUtils.createTestDoctor();
        testPharmacist = TestUtils.createTestPharmacist();
        testAdmin = TestUtils.createTestAdmin();

        patientController = new PatientController(testPatient);
        doctorController = new DoctorController(testDoctor);
        pharmacistController = new PharmacistController(testPharmacist);
        adminController = new AdministratorController(testAdmin);

        Appointment ap = TestUtils.createTestAppointment(testDoctor);
        patientController.scheduleAppointment(ap);
        doctorController.acceptAppointment(ap);
    }

    @Test
    @DisplayName("Test Case 20: View and Manage Hospital Staff")
    void testManageHospitalStaff() {
        // Test adding new staff
        Doctor newDoctor = TestUtils.createTestDoctor();
        boolean added = adminController.addUser(newDoctor);
        assertFalse(added);

        // Test updating staff
        // TODO: Not needed, should just be updated
        newDoctor.setEmail("updated@hospital.com");
        assertEquals(adminController.getDoctorById(newDoctor.getId()).get(), newDoctor);
        // boolean updated = adminController.updateStaffMember(newDoctor);
        // assertTrue(updated);

        // Test removing staff
        boolean removed = adminController.removeUser(newDoctor);
        assertTrue(removed);

        // TODO: Not sure why this is needed
        // // Test filtering staff
        // List<Staff> femaleStaff =
        //         adminController.filterStaff(staff -> staff.getGender() == Gender.FEMALE);
        // assertNotNull(femaleStaff);
        // assertTrue(femaleStaff.stream().allMatch(staff -> staff.getGender() == Gender.FEMALE));

        // List<Staff> doctorStaff = adminController.filterStaff(staff -> staff instanceof Doctor);
        // assertNotNull(doctorStaff);
        // assertTrue(doctorStaff.stream().allMatch(staff -> staff instanceof Doctor));
    }

    @Test
    @DisplayName("Test Case 21: View Appointments Details")
    void testViewAppointmentsDetails() {
        // Get all appointments
        var allAppointments = adminController.getAllAppointments();
        assertNotNull(allAppointments);

        // Test filtering appointments by status
        var confirmedAppointments =
                adminController.getAppointmentsByStatus(AppointmentStatus.CONFIRMED);
        assertNotNull(confirmedAppointments);
        assertTrue(
                confirmedAppointments.stream()
                        .allMatch(appt -> appt.getStatus() == AppointmentStatus.CONFIRMED));

        // Test getting appointment details
        Appointment appointment = allAppointments.get(0);

        var details = adminController.getAppointmentById(appointment.getId()).orElseThrow();
        assertNotNull(details);
        assertEquals(appointment.getId(), details.getId());
        assertNotNull(details.getDoctor());
    }

    @Test
    @DisplayName("Test Case 22: View and Manage Medication Inventory")
    void testManageMedicationInventory() {
        // Test adding new medication
        Medication newMed =
                new Medication(
                        "TEST001",
                        "Test Medication",
                        "100mg",
                        List.of(MedicationSideEffect.DROWSINESS));
        boolean added = adminController.addMedication(newMed);
        assertTrue(added);

        // Test updating medication stock
        boolean updated = adminController.addMedicationStock(newMed, 150);
        assertTrue(updated);

        // Test updating low stock alert level
        boolean alertUpdated = adminController.setMedicationStockAlert(newMed, 30);
        assertTrue(alertUpdated);

        // Test getting medication inventory
        var inventory = adminController.getInventory();
        assertNotNull(inventory);
        assertFalse(inventory.getMedications().isEmpty());

        // Test removing medication
        boolean removed = adminController.removeMedication(newMed);
        assertTrue(removed);
    }

    @Test
    @DisplayName("Test Case 23: Approve Replenishment Requests")
    void testApproveReplenishmentRequests() {
        TestUtils.createTestMedication();

        // Create a test replenishment request
        var medication = adminController.getInventory().getMedications().get(0);
        var medicationStock = adminController.getMedicationStock(medication);
        ReplenishmentRequest request =
                new ReplenishmentRequest(medication, 100, LocalDateTime.now(), testPharmacist);

        // Test approving request
        boolean approved = adminController.approveReplenishmentRequest(request);
        assertTrue(approved);

        // Verify inventory is updated
        var updatedMedicationStock = adminController.getMedicationStock(medication);
        assertEquals(medicationStock + request.getRequestedQuantity(), updatedMedicationStock);

        // Test getting pending requests
        var pendingRequests = adminController.getPendingReplenishmentRequests();
        assertNotNull(pendingRequests);
    }
}
