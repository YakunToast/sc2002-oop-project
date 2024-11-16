package hms;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import hms.model.user.*;
import hms.model.medication.*;
import hms.controller.PharmacistController;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PharmacistActionsTest {
    private PharmacistController pharmacistController;
    private Pharmacist testPharmacist;

    @BeforeEach
    void setup() {
        TestUtils.setupTestRepositories();
        testPharmacist = TestUtils.createTestPharmacist();
        pharmacistController = new PharmacistController(testPharmacist);
    }

    @Test
    @DisplayName("Test Case 16: View Appointment Outcome Record")
    void testViewAppointmentOutcomeRecord() {
        var outcomes = pharmacistController.getAppointmentOutcomesWithPrescriptions();

        assertNotNull(outcomes);
        assertTrue(
                outcomes.stream()
                        .allMatch(outcome -> !outcome.getPrescribedMedications().isEmpty()));
    }

    @Test
    @DisplayName("Test Case 17: Update Prescription Status")
    void testUpdatePrescriptionStatus() {
        // Get a prescription that needs to be dispensed
        var prescription = pharmacistController.getPendingPrescriptions().get(0);

        boolean updated =
                pharmacistController.updatePrescriptionStatus(
                        prescription.getId(), PrescriptionStatus.DISPENSED);

        assertTrue(updated);
        assertEquals(
                PrescriptionStatus.DISPENSED,
                pharmacistController.getPrescription(prescription.getId()).getStatus());
    }

    @Test
    @DisplayName("Test Case 18: View Medication Inventory")
    void testViewMedicationInventory() {
        var inventory = pharmacistController.getInventory();

        assertNotNull(inventory);
        assertFalse(inventory.getMedications().isEmpty());
    }

    @Test
    @DisplayName("Test Case 19: Submit Replenishment Request")
    void testSubmitReplenishmentRequest() {
        var medication = pharmacistController.getInventory().getMedications().get(0);
        int requestQuantity = 100;

        ReplenishmentRequest request =
                pharmacistController.createReplenishmentRequest(
                        medication.getId(), requestQuantity);

        assertNotNull(request);
        assertEquals(medication.getId(), request.getMedicationId());
        assertEquals(requestQuantity, request.getRequestedQuantity());
    }
}
