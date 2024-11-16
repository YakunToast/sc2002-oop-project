package hms;

import hms.model.user.*;
import hms.model.appointment.*;
import hms.model.medication.*;
import hms.repository.*;
import java.util.UUID;

public class TestUtils {
    public static Patient createTestPatient() {
        Patient patient =
                new Patient(
                        UUID.randomUUID().toString(),
                        "TestPatient",
                        "Test",
                        "Patient",
                        "password",
                        "test@example.com",
                        "1234567890",
                        "1 January 1990",
                        "Male",
                        "O");
        return patient;
    }

    public static Doctor createTestDoctor() {
        Doctor doctor =
                new Doctor(
                        UUID.randomUUID().toString(),
                        "TestDoctor",
                        "Test",
                        "Doctor",
                        "password",
                        "doctor@hospital.com",
                        "9876543210");
        return doctor;
    }

    public static Pharmacist createTestPharmacist() {
        Pharmacist pharmacist =
                new Pharmacist(
                        UUID.randomUUID().toString(),
                        "TestPharmacist",
                        "Test",
                        "Pharmacist",
                        "password",
                        "pharmacist@hospital.com",
                        "5555555555",
                        UserRole.PHARMACIST);
        return pharmacist;
    }

    public static Administrator createTestAdmin() {
        Administrator admin =
                new Administrator(
                        UUID.randomUUID().toString(),
                        "TestAdmin",
                        "Test",
                        "Admin",
                        "password",
                        "admin@hospital.com",
                        "1111111111",
                        UserRole.ADMINISTRATOR);
        return admin;
    }

    public static Medication createTestMedication() {
        MedicationSideEffect[] sideEffects = {
            MedicationSideEffect.ANXIETY, MedicationSideEffect.BLURRED_VISION
        };

        Medication medication =
                new Medication(
                        "Test Medication",
                        "Test medication description",
                        "Take one tablet by mouth once daily.",
                        Arrays.asList(sideEffects));
        medication.setId(UUID.randomUUID());
        return medication;

    }

    public static void setupTestRepositories() {
        UserRepository userRepo = new UserRepository();
        AppointmentRepository apptRepo = new AppointmentRepository();
        InventoryRepository invRepo = new InventoryRepository();
        RepositoryManager repoManager = new RepositoryManager();
    }
}
