package hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import hms.controller.PharmacistController;
import hms.model.medication.PrescriptionStatus;
import hms.model.medication.ReplenishmentRequest;
import hms.model.user.Pharmacist;

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
        var outcomes = pharmacistController.getAppointmentOutcomes();

        assertNotNull(outcomes);
        assertTrue(outcomes.stream().allMatch(outcome -> !outcome.getPrescription().isEmpty()));
    }

    @Test
    @DisplayName("Test Case 17: Update Prescription Status")
    void testUpdatePrescriptionStatus() {
        // Get a prescription that needs to be dispensed
        var prescription = pharmacistController.getPendingPrescriptions().get(0);

        boolean updated =
                pharmacistController.updatePrescriptionStatus(
                        prescription, PrescriptionStatus.DISPENSED);

        assertTrue(updated);
        assertTrue(prescription.isDispensed());
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
        var medication = pharmacistController.getMedications().get(0);
        int requestQuantity = 100;

        ReplenishmentRequest request =
                pharmacistController.createReplenishmentRequest(medication, requestQuantity);

        assertNotNull(request);
        assertEquals(medication, request.getMedication());
        assertEquals(requestQuantity, request.getRequestedQuantity());
    }
}
