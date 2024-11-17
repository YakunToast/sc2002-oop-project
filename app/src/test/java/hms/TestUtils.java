package hms;

import java.util.Arrays;
import java.util.UUID;

import hms.model.medication.Medication;
import hms.model.medication.MedicationSideEffect;
import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.Pharmacist;
import hms.model.user.UserRole;
import hms.repository.RepositoryManager;

public class TestUtils {
    private static RepositoryManager rm = RepositoryManager.getInstance();

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
        rm.getUserRepository().addUser(patient);
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
        rm.getUserRepository().addUser(doctor);
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
        rm.getUserRepository().addUser(pharmacist);
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
        rm.getUserRepository().addUser(admin);
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
        rm.getInventoryRepository().getInventory().addMedication(medication);
        return medication;
    }

    public static void setupTestRepositories() {
        // Technically refreshes the repositories
        rm = RepositoryManager.getInstance();
    }
}
