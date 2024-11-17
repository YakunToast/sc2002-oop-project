package hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import hms.controller.DoctorController;
import hms.controller.PatientController;
import hms.model.appointment.Appointment.AppointmentStatus;
import hms.model.medication.Medication;
import hms.model.medication.Prescription;
import hms.model.user.Doctor;
import hms.model.user.Patient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DoctorActionsTest {
    private Doctor testDoctor;
    private DoctorController doctorController;
    private Patient testPatient;
    private PatientController patientController;
    private Medication testMedication;

    @BeforeEach
    void setup() {
        TestUtils.setupTestRepositories();

        testDoctor = TestUtils.createTestDoctor();
        testPatient = TestUtils.createTestPatient();
        testMedication = TestUtils.createTestMedication();

        doctorController = new DoctorController(testDoctor);
        patientController = new PatientController(testPatient);
    }

    @Test
    @DisplayName("Test Case 9: View Patient Medical Records")
    void testViewPatientMedicalRecords() {
        var medicalRecord = doctorController.getPatientMedicalRecord(testPatient);

        assertNotNull(medicalRecord);
        assertEquals(testPatient.getMedicalRecord(), medicalRecord);
    }

    @Test
    @DisplayName("Test Case 10: Update Patient Medical Records")
    void testUpdatePatientMedicalRecords() {
        String diagnosis = "Hypertension";
        String treatment = "Prescribed medication and lifestyle changes";

        boolean updated =
                doctorController.updatePatientMedicalRecord(testPatient, diagnosis, treatment);

        assertTrue(updated);
        var medicalRecord = doctorController.getPatientMedicalRecord(testPatient);
        assertTrue(medicalRecord.getPastDiagnoses().contains(diagnosis));
    }

    @Test
    @DisplayName("Test Case 11: View Personal Schedule")
    void testViewPersonalSchedule() {
        var schedule = doctorController.getPersonalSchedule();

        assertNotNull(schedule);
        assertNotNull(schedule.getAppointments());
    }

    @Test
    @DisplayName("Test Case 12: Set Availability for Appointments")
    void testSetAvailability() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.plusHours(8);

        boolean availabilitySet = doctorController.setAvailability(startTime, endTime);

        assertTrue(availabilitySet);
        var schedule = doctorController.getPersonalSchedule();
        assertFalse(schedule.getAppointments().isEmpty());
    }

    @Test
    @DisplayName("Test Case 13: Accept or Decline Appointment Requests")
    void testHandleAppointmentRequests() {
        var appointment = TestUtils.createTestAppointment(testDoctor);
        assertEquals(appointment.getDoctor(), testDoctor);

        boolean accepted = doctorController.acceptAppointment(appointment);
        assertTrue(accepted);
        assertEquals(AppointmentStatus.CONFIRMED, appointment.getStatus());

        appointment = TestUtils.createTestAppointment(testDoctor);
        boolean declined = doctorController.declineAppointment(appointment);
        assertTrue(declined);
        assertEquals(AppointmentStatus.CANCELLED, appointment.getStatus());
    }

    @Test
    @DisplayName("Test Case 14: View Upcoming Appointments")
    void testViewUpcomingAppointments() {
        // Create some test appointments
        var appointment1 = TestUtils.createTestAppointment(testDoctor);
        var appointment2 = TestUtils.createTestAppointment(testDoctor);
        assertNotNull(appointment1);
        assertNotNull(appointment2);

        var pendingAppointments = doctorController.getPendingAppointments();
        assertTrue(pendingAppointments.isEmpty());

        var appointments = doctorController.getFreeAppointments();
        assertNotNull(appointments);
        assertFalse(appointments.isEmpty());
        assertTrue(appointments.stream().allMatch(appt -> appt.getDoctor().equals(testDoctor)));
    }

    @Test
    @DisplayName("Test Case 15: Record Appointment Outcome")
    void testRecordAppointmentOutcome() {
        var appointment = TestUtils.createTestAppointment(testDoctor);
        appointment.setStatus(AppointmentStatus.COMPLETED);

        boolean recorded =
                doctorController.addAppointmentOutcome(
                        appointment,
                        "Regular checkup",
                        new Prescription(testMedication, testMedication));

        assertTrue(recorded);
        var savedOutcome = doctorController.getAppointmentOutcome(appointment);
        assertNotNull(savedOutcome);
        assertEquals("Regular checkup", savedOutcome.getDescription());
    }
}
