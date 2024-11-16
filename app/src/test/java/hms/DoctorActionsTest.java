package hms;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import hms.model.user.*;
import hms.model.appointment.*;
import hms.controller.DoctorController;
import java.time.LocalDateTime;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DoctorActionsTest {
    private DoctorController doctorController;
    private Doctor testDoctor;
    private Patient testPatient;

    private Appointment createTestAppointment() {
        return doctorController.createAppointment(
                testPatient.getId(), LocalDateTime.now().plusDays(1));
    }

    @BeforeEach
    void setup() {
        TestUtils.setupTestRepositories();
        testDoctor = TestUtils.createTestDoctor();
        testPatient = TestUtils.createTestPatient();
        doctorController = new DoctorController(testDoctor);
    }

    @Test
    @DisplayName("Test Case 9: View Patient Medical Records")
    void testViewPatientMedicalRecords() {
        var medicalRecord = doctorController.viewPatientMedicalRecord(testPatient.getId());

        assertNotNull(medicalRecord);
        assertEquals(testPatient.getId(), medicalRecord.getPatientId());
    }

    @Test
    @DisplayName("Test Case 10: Update Patient Medical Records")
    void testUpdatePatientMedicalRecords() {
        String diagnosis = "Hypertension";
        String treatment = "Prescribed medication and lifestyle changes";

        boolean updated =
                doctorController.updatePatientMedicalRecord(
                        testPatient.getId(), diagnosis, treatment);

        assertTrue(updated);
        var medicalRecord = doctorController.viewPatientMedicalRecord(testPatient.getId());
        assertTrue(medicalRecord.getPastDiagnoses().contains(diagnosis));
    }

    @Test
    @DisplayName("Test Case 11: View Personal Schedule")
    void testViewPersonalSchedule() {
        var schedule = doctorController.getPersonalSchedule();

        assertNotNull(schedule);
        assertNotNull(schedule.getTimeSlots());
    }

    @Test
    @DisplayName("Test Case 12: Set Availability for Appointments")
    void testSetAvailability() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.plusHours(8);

        boolean availabilitySet = doctorController.setAvailability(startTime, endTime);

        assertTrue(availabilitySet);
        var schedule = doctorController.getPersonalSchedule();
        assertFalse(schedule.getTimeSlots().isEmpty());
    }

    @Test
    @DisplayName("Test Case 13: Accept or Decline Appointment Requests")
    void testHandleAppointmentRequests() {
        var appointment = createTestAppointment();

        boolean accepted = doctorController.acceptAppointment(appointment.getId());
        assertTrue(accepted);
        assertEquals(
                AppointmentStatus.CONFIRMED,
                doctorController.getAppointment(appointment.getId()).getStatus());

        appointment = createTestAppointment();
        boolean declined = doctorController.declineAppointment(appointment.getId());
        assertTrue(declined);
        assertEquals(
                AppointmentStatus.CANCELLED,
                doctorController.getAppointment(appointment.getId()).getStatus());
    }

    @Test
    @DisplayName("Test Case 14: View Upcoming Appointments")
    void testViewUpcomingAppointments() {
        // Create some test appointments
        createTestAppointment();
        createTestAppointment();

        var appointments = doctorController.getUpcomingAppointments();

        assertNotNull(appointments);
        assertFalse(appointments.isEmpty());
        assertTrue(
                appointments.stream()
                        .allMatch(appt -> appt.getDoctorId().equals(testDoctor.getId())));
    }

    @Test
    @DisplayName("Test Case 15: Record Appointment Outcome")
    void testRecordAppointmentOutcome() {
        var appointment = createTestAppointment();
        appointment.setStatus(AppointmentStatus.COMPLETED);

        AppointmentOutcome outcome =
                new AppointmentOutcome(
                        appointment,
                        "Regular checkup",
                        List.of("Aspirin 100mg"),
                        "Patient is stable");

        boolean recorded = doctorController.recordAppointmentOutcome(outcome);

        assertTrue(recorded);
        var savedOutcome = doctorController.getAppointmentOutcome(appointment.getId());
        assertNotNull(savedOutcome);
        assertEquals("Regular checkup", savedOutcome.getServiceProvided());
    }
}
