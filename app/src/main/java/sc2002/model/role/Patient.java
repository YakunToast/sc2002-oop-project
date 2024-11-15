package sc2002.model.role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import sc2002.model.MedicalRecord;
import sc2002.model.appointment.Appointment;

// Patient class to manage patient operations
public class Patient extends User {
    private MedicalRecord medicalRecord;
    private List<Appointment> appointments;

    public Patient(UUID id, String username, String firstName, String lastName, String password, String email, String phoneNumber, UserRole role) {
        super(id, username, firstName, lastName, password, email, phoneNumber, role);
        this.medicalRecord = new MedicalRecord(this);
        this.appointments = new ArrayList<>();
    }

    public Patient(String username, String firstName, String lastName, String password, String email, String phoneNumber, UserRole role) {
        this(UUID.randomUUID(), username, firstName, lastName, password, email, phoneNumber, role);
    }

    public void updateContactInformation(String phone, String email) {
        medicalRecord.updateContactInformation(phone, email);
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<Appointment> getPastAppointments() {
        return appointments.stream()
                .filter(a -> a.getDateTime().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
}
