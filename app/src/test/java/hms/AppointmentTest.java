package hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import hms.controller.AdministratorController;
import hms.controller.DoctorController;
import hms.controller.PatientController;
import hms.controller.PharmacistController;
import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentStatus;
import hms.model.appointment.state.CancelledState;
import hms.model.appointment.state.CompletedState;
import hms.model.appointment.state.ConfirmedState;
import hms.model.appointment.state.FreeState;
import hms.model.appointment.state.PendingState;
import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.Pharmacist;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppointmentTest {
    private Patient testPatient;
    private PatientController patientController;
    private Administrator testAdmin;
    private AdministratorController adminController;
    private Doctor testDoctor;
    private DoctorController doctorController;
    private Pharmacist testPharmacist;
    private PharmacistController pharmacistController;

    private Appointment ap;

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

        ap = TestUtils.createTestAppointment(testDoctor);
    }

    @Test
    @DisplayName("Test: Appointment state transition flow normal")
    void testAppointmentStatesNormal() {
        // Ensure base case is correct
        assertTrue(ap.isFree());
        assertInstanceOf(FreeState.class, ap.getState());
        assertEquals("Free", ap.getState().toString());
        assertEquals(AppointmentStatus.FREE, ap.getStatus());

        patientController.scheduleAppointment(ap);
        assertTrue(ap.isPending());
        assertInstanceOf(PendingState.class, ap.getState());
        assertEquals("Pending", ap.getState().toString());
        assertEquals(AppointmentStatus.PENDING, ap.getStatus());

        doctorController.acceptAppointment(ap);
        assertTrue(ap.isConfirmed());
        assertInstanceOf(ConfirmedState.class, ap.getState());
        assertEquals("Confirmed", ap.getState().toString());
        assertEquals(AppointmentStatus.CONFIRMED, ap.getStatus());

        doctorController.addAppointmentOutcome(ap, "finished", null);
        assertTrue(ap.isCompleted());
        assertInstanceOf(CompletedState.class, ap.getState());
        assertEquals("Completed", ap.getState().toString());
        assertEquals(AppointmentStatus.COMPLETED, ap.getStatus());
    }

    @Test
    @DisplayName("Test: Appointment state transition flow cancelled")
    void testAppointmentStatesCancelled() {
        // Ensure base case is correct
        assertTrue(ap.isFree());
        assertInstanceOf(FreeState.class, ap.getState());
        assertEquals("Free", ap.getState().toString());
        assertEquals(AppointmentStatus.FREE, ap.getStatus());

        patientController.scheduleAppointment(ap);
        assertTrue(ap.isPending());
        assertInstanceOf(PendingState.class, ap.getState());
        assertEquals("Pending", ap.getState().toString());
        assertEquals(AppointmentStatus.PENDING, ap.getStatus());

        doctorController.acceptAppointment(ap);
        assertTrue(ap.isConfirmed());
        assertInstanceOf(ConfirmedState.class, ap.getState());
        assertEquals("Confirmed", ap.getState().toString());
        assertEquals(AppointmentStatus.CONFIRMED, ap.getStatus());

        patientController.cancelAppointment(ap);
        assertTrue(ap.isCancelled());
        assertInstanceOf(CancelledState.class, ap.getState());
        assertEquals("Cancelled", ap.getState().toString());
        assertEquals(AppointmentStatus.CANCELLED, ap.getStatus());

        doctorController.freeAppointment(ap);
        assertTrue(ap.isFree());
        assertInstanceOf(FreeState.class, ap.getState());
        assertEquals("Free", ap.getState().toString());
        assertEquals(AppointmentStatus.FREE, ap.getStatus());
    }
}
