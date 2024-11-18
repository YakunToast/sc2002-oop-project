package hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import hms.controller.DoctorController;
import hms.controller.PatientController;
import hms.model.appointment.Appointment;
import hms.model.medication.Medication;
import hms.model.medication.Prescription;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.repository.RepositoryManager;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepositoryTest {
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
    @DisplayName("Test: Repository Save & Load: Appointment Outcomes")
    void testViewPatientMedicalRecords() {
        Appointment ap = TestUtils.createTestAppointment(testDoctor);
        patientController.scheduleAppointment(ap);
        doctorController.acceptAppointment(ap);

        Medication me = TestUtils.createTestMedication();
        doctorController.addAppointmentOutcome(ap, "Appointment Description", new Prescription(me));

        // Refresh repository
        RepositoryManager.getInstance().save();
        RepositoryManager.destroyInstance();
        RepositoryManager.load();

        // Get old users
        Doctor newTestDoctor =
                (Doctor)
                        RepositoryManager.getInstance()
                                .getUserRepository()
                                .getUserByUsername(testDoctor.getUsername())
                                .get();
        Patient newTestPatient =
                (Patient)
                        RepositoryManager.getInstance()
                                .getUserRepository()
                                .getUserByUsername(testPatient.getUsername())
                                .get();
        DoctorController newDoctorController = new DoctorController(newTestDoctor);
        PatientController newPatientController = new PatientController(newTestPatient);

        // Get appointment and check appointment
        Appointment newAppointment = newDoctorController.getAppointments().get(0);
        assertNotNull(newPatientController.getAppointmentOutcome(newAppointment));
        assertEquals(newAppointment.getPatient(), newTestPatient);
        assertEquals("Appointment Description", newAppointment.getOutcome().getDescription());
    }
}
