package hms;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import hms.model.user.*;
import hms.model.appointment.*;
import hms.controller.PatientController;
import java.time.LocalDateTime;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatientActionsTest {
    private PatientController patientController;
    private Patient testPatient;
    private Doctor testDoctor;

    @BeforeEach
    void setup() {
        TestUtils.setupTestRepositories();
        testPatient = TestUtils.createTestPatient();
        testDoctor = TestUtils.createTestDoctor();
        patientController = new PatientController(testPatient);
    }

    @Test
    @DisplayName("Test Case 1: View Medical Record")
    void testViewMedicalRecord() {
        var medicalRecord = patientController.getMedicalRecord();
        
        assertNotNull(medicalRecord);
        assertEquals(testPatient.getId(), medicalRecord.getPatientId());
        assertNotNull(medicalRecord.getBloodType());
        assertNotNull(medicalRecord.getPastDiagnoses());
    }

    @Test
    @DisplayName("Test Case 2: Update Personal Information")
    void testUpdatePersonalInfo() {
        String newEmail = "updated@example.com";
        String newPhone = "9999999999";
        
        patientController.setEmail(newEmail);
        patientController.setPhoneNumber(newPhone);
        
        assertEquals(newEmail, testPatient.getEmail());
        assertEquals(newPhone, testPatient.getPhoneNumber());
    }

    @Test
    @DisplayName("Test Case 3: View Available Appointment Slots")
    void testViewAvailableSlots() {
        var slots = patientController.getAvailableAppointmentSlots();
        
        assertNotNull(slots);
        assertFalse(slots.isEmpty());
        assertTrue(slots.stream().allMatch(slot -> !slot.isBooked()));
    }

    @Test
    @DisplayName("Test Case 4: Schedule an Appointment")
    void testScheduleAppointment() {
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1);
        var appointment = patientController.scheduleAppointment(testDoctor.getId(), appointmentTime);
        
        assertNotNull(appointment);
        assertEquals(AppointmentStatus.CONFIRMED, appointment.getStatus());
        assertEquals(testPatient.getId(), appointment.getPatientId());
        assertEquals(testDoctor.getId(), appointment.getDoctorId());
    }

    @Test
    @DisplayName("Test Case 5: Reschedule an Appointment")
    void testRescheduleAppointment() {
        LocalDateTime originalTime = LocalDateTime.now().plusDays(1);
        var appointment = patientController.scheduleAppointment(testDoctor.getId(), originalTime);
        
        LocalDateTime newTime = LocalDateTime.now().plusDays(2);
        var rescheduledAppointment = patientController.rescheduleAppointment(
            appointment.getId(), newTime);
        
        assertNotNull(rescheduledAppointment);
        assertEquals(newTime, rescheduledAppointment.getDateTime());
        assertEquals(AppointmentStatus.CONFIRMED, rescheduledAppointment.getStatus());
    }

    @Test
    @DisplayName("Test Case 6: Cancel an Appointment")
    void testCancelAppointment() {
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1);
        var appointment = patientController.scheduleAppointment(testDoctor.getId(), appointmentTime);
        
        boolean cancelled = patientController.cancelAppointment(appointment.getId());
        
        assertTrue(cancelled);
        var cancelledAppointment = patientController.getAppointment(appointment.getId());
        assertEquals(AppointmentStatus.CANCELLED, cancelledAppointment.getStatus());
    }

    @Test
    @DisplayName("Test Case 7: View Scheduled Appointments")
    void testViewScheduledAppointments() {
        patientController.scheduleAppointment(testDoctor.getId(), LocalDateTime.now().plusDays(1));
        patientController.scheduleAppointment(testDoctor.getId(), LocalDateTime.now().plusDays(2));
        
        var appointments = patientController.getScheduledAppointments();
        
        assertNotNull(appointments);
        assertFalse(appointments.isEmpty());
        assertTrue(appointments.stream()
            .allMatch(appt -> appt.getPatientId().equals(testPatient.getId())));
    }

    @Test
    @DisplayName("Test Case 8: View Past Appointment Outcome Records")
    void testViewPastAppointmentOutcomes() {
        var outcomes = patientController.getPastAppointmentOutcomes();
        
        assertNotNull(outcomes);
        assertTrue(outcomes.stream()
            .allMatch(outcome -> outcome.getAppointment().getStatus() == AppointmentStatus.COMPLETED));
    }
}