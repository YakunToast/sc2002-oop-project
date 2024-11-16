package hms.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import hms.model.appointment.Appointment;
import hms.model.record.MedicalRecord;
import hms.model.schedule.Schedule;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.UserRole;
import hms.repository.RepositoryManager;

public class DoctorController extends UserController {
    private final Doctor doctor;

    public DoctorController(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<Patient> getPatients() {
        return RepositoryManager.getInstance().getUserRepository().getAllUsers().stream()
                .filter(u -> u.getRole() == UserRole.PATIENT)
                .map(u -> (Patient) u)
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointments() {
        return RepositoryManager.getInstance()
                .getAppointmentRepository()
                .getAllAppointments()
                .stream()
                .filter(a -> a.getDoctor() == doctor)
                .collect(Collectors.toList());
    }

    public List<Appointment> getPendingAppointments() {
        return this.getAppointments().stream()
                .filter(a -> a.isPending())
                .collect(Collectors.toList());
    }

    public List<Appointment> getConfirmedAppointments() {
        return this.getAppointments().stream()
                .filter(a -> a.isConfirmed())
                .collect(Collectors.toList());
    }

    public void addSlots(LocalDate startDate, LocalDate endDate, LocalTime start, LocalTime end) {
        doctor.getSchedule().addSlots(startDate, endDate, start, end);
    }

    public MedicalRecord getPatientMedicalRecord(Patient patient) {
        // TODO: Check if patient is a patient of doctor
        return patient.getMedicalRecord();
    }

    public boolean updatePatientMedicalRecord(Patient patient, String diagnosis, String treatment) {
        MedicalRecord mr = this.getPatientMedicalRecord(patient);
        if (mr == null) {
            return false;
        }

        mr.addDiagnosis(diagnosis);
        mr.addTreatment(treatment);

        return true;
    }

    public Schedule getPersonalSchedule() {
        return this.doctor.getSchedule();
    }

    public boolean setAvailability(LocalDateTime start, LocalDateTime end) {
        Schedule sc = this.getPersonalSchedule();
        sc.addSlots(start.toLocalDate(), end.toLocalDate(), start.toLocalTime(), end.toLocalTime());
        // TODO: Need to better check success of this
        return true;
    }

    public boolean acceptAppointment(Appointment ap) {
        if (ap.getDoctor() != doctor) {
            return false;
        }
        return new AppointmentController(ap).accept();
    }

    public boolean declineAppointment(Appointment ap) {
        if (ap.getDoctor() != doctor) {
            return false;
        }
        return new AppointmentController(ap).decline();
    }
}
