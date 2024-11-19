package hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import hms.controller.AdministratorController;
import hms.controller.DoctorController;
import hms.controller.PatientController;
import hms.controller.PharmacistController;
import hms.model.appointment.Appointment;
import hms.model.medication.Medication;
import hms.model.medication.Prescription;
import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.Pharmacist;
import hms.repository.RepositoryManager;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepositoryTest {
    private Doctor testDoctor;
    private DoctorController doctorController;
    private Patient testPatient;
    private PatientController patientController;
    private Pharmacist testPharmacist;
    private PharmacistController pharmacistController;
    private Administrator testAdministrator;
    private AdministratorController administratorController;
    private Medication testMedication;

    @BeforeEach
    void setup() {
        TestUtils.setupTestRepositories();

        testDoctor = TestUtils.createTestDoctor();
        testPatient = TestUtils.createTestPatient();
        testPharmacist = TestUtils.createTestPharmacist();
        testAdministrator = TestUtils.createTestAdmin();
        testMedication = TestUtils.createTestMedication();

        doctorController = new DoctorController(testDoctor);
        patientController = new PatientController(testPatient);
        pharmacistController = new PharmacistController(testPharmacist);
        administratorController = new AdministratorController(testAdministrator);
    }

    @Test
    @DisplayName("Test: Repository Save & Load: Appointment Outcomes")
    void testViewPatientMedicalRecords() {
        // Prepare appointment
        Appointment ap = TestUtils.createTestAppointment(testDoctor);
        patientController.scheduleAppointment(ap);
        doctorController.acceptAppointment(ap);

        // Mark appointment outcome
        Prescription prescription = new Prescription(testMedication);
        doctorController.addAppointmentOutcome(ap, "Appointment Description", prescription);

        // Pharmacist check if prescrition is correct
        assertEquals(prescription, pharmacistController.getPendingPrescriptions().get(0));

        // Refresh repository
        RepositoryManager.getInstance().saveAndLog();
        RepositoryManager.destroyInstance();
        RepositoryManager.load();

        // Get users
        Patient newTestPatient =
                (Patient)
                        RepositoryManager.getInstance()
                                .getUserRepository()
                                .getUserByUsername(testPatient.getUsername())
                                .get();
        Doctor newTestDoctor =
                (Doctor)
                        RepositoryManager.getInstance()
                                .getUserRepository()
                                .getUserByUsername(testDoctor.getUsername())
                                .get();
        Pharmacist newTestPharmacist =
                (Pharmacist)
                        RepositoryManager.getInstance()
                                .getUserRepository()
                                .getUserByUsername(testPharmacist.getUsername())
                                .get();
        Administrator newTestAdministrator =
                (Administrator)
                        RepositoryManager.getInstance()
                                .getUserRepository()
                                .getUserByUsername(testAdministrator.getUsername())
                                .get();
        PatientController newPatientController = new PatientController(newTestPatient);
        DoctorController newDoctorController = new DoctorController(newTestDoctor);
        PharmacistController newPharmacistController = new PharmacistController(newTestPharmacist);
        AdministratorController newAdministratorController =
                new AdministratorController(newTestAdministrator);

        // Get appointment and check appointment
        Appointment newAppointment = newDoctorController.getAppointments().get(0);
        assertNotNull(newPatientController.getAppointmentOutcome(newAppointment));
        assertEquals(newAppointment.getPatient(), newTestPatient);

        // Check appointment outcome
        assertEquals("Appointment Description", newAppointment.getOutcome().getDescription());

        // Check prescription from pharmacist
        assertNotNull(newPharmacistController.getPendingPrescriptions().get(0));
    }
}
