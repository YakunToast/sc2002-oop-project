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
import hms.model.user.Doctor;
import hms.model.user.Patient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatientActionsTest {
    private PatientController patientController;
    private Patient testPatient;
    private DoctorController doctorController;
    private Doctor testDoctor;

    @BeforeEach
    void setup() {
        TestUtils.setupTestRepositories();
        testPatient = TestUtils.createTestPatient();
        testDoctor = TestUtils.createTestDoctor();
        patientController = new PatientController(testPatient);
        doctorController = new DoctorController(testDoctor);

        TestUtils.createTestAppointments(testDoctor);
    }

    @Test
    @DisplayName("Test Case 1: View Medical Record")
    void testViewMedicalRecord() {
        var medicalRecord = patientController.getMedicalRecord();

        assertNotNull(medicalRecord);
        assertEquals(testPatient.getId(), testPatient.getId());
        assertNotNull(testPatient.getBloodType());
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
        assertTrue(slots.stream().allMatch(ap -> ap.isFree()));
    }

    // TODO: For the appointment endpoints,
    // Create a doctor
    // Add slots to doctor
    // and feed it to scheduleAppointment
    // e.g. cheduleAppointment(Doctor doctor, List<TimeSlot> ts)
    @Test
    @DisplayName("Test Case 4: Schedule an Appointment")
    void testScheduleAppointment() {
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1);
        var appointment = patientController.getAvailableAppointmentSlots().get(0);
        patientController.scheduleAppointment(appointment);

        assertNotNull(appointment);
        assertEquals(AppointmentStatus.PENDING, appointment.getStatus());
        assertEquals(testPatient, appointment.getPatient());
        assertEquals(testDoctor, appointment.getDoctor());
    }

    @Test
    @DisplayName("Test Case 5: Reschedule an Appointment")
    void testRescheduleAppointment() {
        var appointment = patientController.getAvailableAppointmentSlots().get(0);
        assertNotNull(appointment);
        patientController.scheduleAppointment(appointment);

        var newAppointment = patientController.getAvailableAppointmentSlots().get(0);
        assertNotNull(newAppointment);
        patientController.rescheduleAppointment(appointment, newAppointment);

        assertEquals(appointment.getStatus(), AppointmentStatus.FREE);
        assertEquals(newAppointment.getStatus(), AppointmentStatus.PENDING);
    }

    @Test
    @DisplayName("Test Case 6: Cancel an Appointment")
    void testCancelAppointment() {
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1);
        var appointment = patientController.getAvailableAppointmentSlots().get(0);
        patientController.scheduleAppointment(
                patientController.getAvailableAppointmentSlots().get(0));

        patientController.cancelAppointment(appointment);

        // var cancelledAppointment = patientController.getAppointment(appointment);
        assertEquals(AppointmentStatus.CANCELLED, appointment.getStatus());
    }

    @Test
    @DisplayName("Test Case 7: View Scheduled Appointments")
    void testViewScheduledAppointments() {
        var appointment1 = patientController.getAvailableAppointmentSlots().get(0);
        var appointment2 = patientController.getAvailableAppointmentSlots().get(1);

        assertNotNull(appointment1);
        assertNotNull(appointment2);

        patientController.scheduleAppointment(appointment1);
        patientController.scheduleAppointment(appointment2);

        var appointments = patientController.getScheduledAppointments();

        assertNotNull(appointments);
        assertFalse(appointments.isEmpty());
        assertTrue(appointments.stream().allMatch(appt -> appt.getPatient().equals(testPatient)));
    }

    @Test
    @DisplayName("Test Case 8: View Past Appointment Outcome Records")
    void testViewPastAppointmentOutcomes() {
        var outcomes = patientController.getPastAppointmentOutcomes();

        assertNotNull(outcomes);
        assertTrue(
                outcomes.stream()
                        .allMatch(
                                outcome ->
                                        outcome.getAppointment().getStatus()
                                                == AppointmentStatus.COMPLETED));
    }
}
