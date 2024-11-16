package hms;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import hms.model.user.*;
import hms.model.medication.*;
import hms.controller.AdministratorController;
import java.time.LocalDateTime;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdministratorActionsTest {
    private AdministratorController adminController;
    private Administrator testAdmin;
    private Doctor testDoctor;
    private Pharmacist testPharmacist;

    @BeforeEach
    void setup() {
        TestUtils.setupTestRepositories();
        testAdmin = TestUtils.createTestAdmin();
        testDoctor = TestUtils.createTestDoctor();
        testPharmacist = TestUtils.createTestPharmacist();
        adminController = new AdministratorController(testAdmin);
    }

    @Test
    @DisplayName("Test Case 20: View and Manage Hospital Staff")
    void testManageHospitalStaff() {
        // Test adding new staff
        Doctor newDoctor = TestUtils.createTestDoctor();
        boolean added = adminController.addStaffMember(newDoctor);
        assertTrue(added);
        
        // Test updating staff
        newDoctor.setEmail("updated@hospital.com");
        boolean updated = adminController.updateStaffMember(newDoctor);
        assertTrue(updated);
        
        // Test removing staff
        boolean removed = adminController.removeStaffMember(newDoctor.getId());
        assertTrue(removed);
        
        // Test filtering staff
        List<Staff> femaleStaff = adminController.filterStaff(staff -> staff.getGender() == Gender.FEMALE);
        assertNotNull(femaleStaff);
        assertTrue(femaleStaff.stream().allMatch(staff -> staff.getGender() == Gender.FEMALE));
        
        List<Staff> doctorStaff = adminController.filterStaff(staff -> staff instanceof Doctor);
        assertNotNull(doctorStaff);
        assertTrue(doctorStaff.stream().allMatch(staff -> staff instanceof Doctor));
    }

    @Test
    @DisplayName("Test Case 21: View Appointments Details")
    void testViewAppointmentsDetails() {
        // Get all appointments
        var allAppointments = adminController.getAllAppointments();
        assertNotNull(allAppointments);
        
        // Test filtering appointments by status
        var confirmedAppointments = adminController.getAppointmentsByStatus(AppointmentStatus.CONFIRMED);
        assertNotNull(confirmedAppointments);
        assertTrue(confirmedAppointments.stream()
            .allMatch(appt -> appt.getStatus() == AppointmentStatus.CONFIRMED));
        
        // Test getting appointment details
        var appointment = allAppointments.get(0);
        var details = adminController.getAppointmentDetails(appointment.getId());
        assertNotNull(details);
        assertEquals(appointment.getId(), details.getId());
        assertNotNull(details.getPatientId());
        assertNotNull(details.getDoctorId());
    }

    @Test
    @DisplayName("Test Case 22: View and Manage Medication Inventory")
    void testManageMedicationInventory() {
        // Test adding new medication
        Medication newMed = new Medication(
            "TEST001",
            "Test Medication",
            100,
            20,  // low stock alert level
            "Description",
            List.of(MedicationSideEffect.DROWSINESS)
        );
        boolean added = adminController.addMedication(newMed);
        assertTrue(added);
        
        // Test updating medication stock
        boolean updated = adminController.updateMedicationStock(newMed.getId(), 150);
        assertTrue(updated);
        
        // Test updating low stock alert level
        boolean alertUpdated = adminController.updateLowStockAlertLevel(newMed.getId(), 30);
        assertTrue(alertUpdated);
        
        // Test getting medication inventory
        var inventory = adminController.getMedicationInventory();
        assertNotNull(inventory);
        assertFalse(inventory.getMedications().isEmpty());
        
        // Test removing medication
        boolean removed = adminController.removeMedication(newMed.getId());
        assertTrue(removed);
    }

    @Test
    @DisplayName("Test Case 23: Approve Replenishment Requests")
    void testApproveReplenishmentRequests() {
        // Create a test replenishment request
        var medication = adminController.getMedicationInventory().getMedications().get(0);
        ReplenishmentRequest request = new ReplenishmentRequest(
            medication.getId(),
            100,
            LocalDateTime.now(),
            testPharmacist.getId()
        );
        
        // Test approving request
        boolean approved = adminController.approveReplenishmentRequest(request.getId());
        assertTrue(approved);
        
        // Verify inventory is updated
        var updatedMedication = adminController.getMedication(medication.getId());
        assertEquals(medication.getStock() + request.getRequestedQuantity(), 
            updatedMedication.getStock());
        
        // Test getting pending requests
        var pendingRequests = adminController.getPendingReplenishmentRequests();
        assertNotNull(pendingRequests);
    }
}