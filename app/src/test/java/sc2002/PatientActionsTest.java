import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sc2002.model.MedicalRecord;
import sc2002.model.appointment.Appointment;
import sc2002.model.role.Doctor;
import sc2002.model.role.Patient;

// Assuming the following classes exist in your Hospital Management System:
// - Patient
// - MedicalRecord
// - Appointment
// - Doctor
// - AppointmentSlot

public class PatientActionsTest {

    // Test Case 1: View Medical Record
    @Test
    public void testViewMedicalRecord() {
        // Arrange
        Patient patient = new Patient(1, "John Doe", "1990-01-01", "Male", "johndoe@example.com", "1234567890",
                "A+");
        MedicalRecord medicalRecord = new MedicalRecord(patient, "Hypertension", "Treatment XYZ");

        // Act
        MedicalRecord viewedRecord = patient.viewMedicalRecord();

        // Assert
        assertEquals(patient.getPatientID(), viewedRecord.getPatientID());
        assertEquals(patient.getName(), viewedRecord.getPatientName());
        assertEquals(patient.getDateOfBirth(), viewedRecord.getDateOfBirth());
        assertEquals(patient.getGender(), viewedRecord.getGender());
        assertEquals(patient.getContactInfo(), viewedRecord.getContactInfo());
        assertEquals(patient.getBloodType(), viewedRecord.getBloodType());
        assertEquals(medicalRecord.getPastDiagnosesAndTreatments(), viewedRecord.getPastDiagnosesAndTreatments());
    }

    // Test Case 2: Update Personal Information
    @Test
    public void testUpdatePersonalInformation() {
        // Arrange
        Patient patient = new Patient("12345", "John Doe", "1990-01-01", "Male", "johndoe@example.com", "1234567890",
                "A+");

        // Act
        patient.updateContactInformation("newemail@example.com", "0987654321");
        MedicalRecord updatedRecord = patient.viewMedicalRecord();

        // Assert
        assertEquals("newemail@example.com", updatedRecord.getContactInfo().getEmail());
        assertEquals("0987654321", updatedRecord.getContactInfo().getPhoneNumber());
    }

    // Test Case 3: View Available Appointment Slots
    @Test
    public void testViewAvailableAppointmentSlots() {
        // Arrange
        Doctor doctor1 = new Doctor("Dr. Smith");
        Doctor doctor2 = new Doctor("Dr. Johnson");
        AppointmentSlot slot1 = new AppointmentSlot(doctor1, "2023-03-15", "09:00");
        AppointmentSlot slot2 = new AppointmentSlot(doctor2, "2023-03-16", "10:00");
        List<AppointmentSlot> availableSlots = new ArrayList<>();
        availableSlots.add(slot1);
        availableSlots.add(slot2);

        // Act
        List<AppointmentSlot> viewedSlots = AppointmentSlot.viewAvailableSlots();

        // Assert
        assertEquals(2, viewedSlots.size());
        assertTrue(viewedSlots.contains(slot1));
        assertTrue(viewedSlots.contains(slot2));
    }

    // Test Case 4: Schedule an Appointment
    @Test
    public void testScheduleAppointment() {
        // Arrange
        Patient patient = new Patient("12345", "John Doe", "1990-01-01", "Male", "johndoe@example.com", "1234567890",
                "A+");
        Doctor doctor = new Doctor("Dr. Smith");
        AppointmentSlot slot = new AppointmentSlot(doctor, "2023-03-15", "09:00");

        // Act
        Appointment appointment = patient.scheduleAppointment(slot);

        // Assert
        assertEquals("Confirmed", appointment.getStatus());
        assertFalse(AppointmentSlot.isSlotAvailable(slot));
    }

    // Test Case 5: Reschedule an Appointment
    @Test
    public void testRescheduleAppointment() {
        // Arrange
        Patient patient = new Patient("12345", "John Doe", "1990-01-01", "Male", "johndoe@example.com", "1234567890",
                "A+");
        Doctor doctor1 = new Doctor("Dr. Smith");
        Doctor doctor2 = new Doctor("Dr. Johnson");
        AppointmentSlot oldSlot = new AppointmentSlot(doctor1, "2023-03-15", "09:00");
        AppointmentSlot newSlot = new AppointmentSlot(doctor2, "2023-03-16", "10:00");
        Appointment appointment = patient.scheduleAppointment(oldSlot);

        // Act
        appointment.reschedule(newSlot);

        // Assert
        assertEquals(newSlot, appointment.getSlot());
        assertTrue(AppointmentSlot.isSlotAvailable(oldSlot));
        assertFalse(AppointmentSlot.isSlotAvailable(newSlot));
    }

    // Test Case 6: Cancel an Appointment
    @Test
    public void testCancelAppointment() {
        // Arrange
        Patient patient = new Patient("12345", "John Doe", "1990-01-01", "Male", "johndoe@example.com", "1234567890",
                "A+");
        Doctor doctor = new Doctor("Dr. Smith");
        AppointmentSlot slot = new AppointmentSlot(doctor, "2023-03-15", "09:00");
        Appointment appointment = patient.scheduleAppointment(slot);

        // Act
        appointment.cancel();

        // Assert
        assertEquals("Cancelled", appointment.getStatus());
        assertTrue(AppointmentSlot.isSlotAvailable(slot));
    }

    // Test Case 7: View Scheduled Appointments
    @Test
    public void testViewScheduledAppointments() {
        // Arrange
        Patient patient = new Patient("12345", "John Doe", "1990-01-01", "Male", "johndoe@example.com", "1234567890",
                "A+");
        Doctor doctor1 = new Doctor("Dr. Smith");
        Doctor doctor2 = new Doctor("Dr. Johnson");
        AppointmentSlot slot1 = new AppointmentSlot(doctor1, "2023-03-15", "09:00");
        AppointmentSlot slot2 = new AppointmentSlot(doctor2, "2023-03-16", "10:00");
        patient.scheduleAppointment(slot1);
        patient.scheduleAppointment(slot2);

        // Act
        List<Appointment> appointments = patient.viewScheduledAppointments();

        // Assert
        assertEquals(2, appointments.size());
        assertTrue(appointments.stream().anyMatch(a -> a.getSlot().equals(slot1)));
        assertTrue(appointments.stream().anyMatch(a -> a.getSlot().equals(slot2)));
    }

    // Test Case 8: View Past Appointment Outcome Records
    @Test
    public void testViewPastAppointmentOutcomeRecords() {
        // Arrange
        Patient patient = new Patient("12345", "John Doe", "1990-01-01", "Male", "johndoe@example.com", "1234567890",
                "A+");
        Doctor doctor = new Doctor("Dr. Smith");
        AppointmentSlot slot = new AppointmentSlot(doctor, "2023-03-10", "09:00");
        Appointment appointment = patient.scheduleAppointment(slot);
        appointment.setOutcome("Service X", "Medication Y", "Consultation Note Z");
        appointment.cancel(); // Mark as past appointment

        // Act
        List<Appointment> pastAppointments = patient.viewPastAppointments();

        // Assert
        assertEquals(1, pastAppointments.size());
        Appointment pastAppointment = pastAppointments.get(0);
        assertEquals("Service X", pastAppointment.getServiceProvided());
        assertEquals("Medication Y", pastAppointment.getPrescribedMedication());
        assertEquals("Consultation Note Z", pastAppointment.getConsultationNote());
    }
}
