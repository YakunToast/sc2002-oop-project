package hms.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import hms.model.appointment.Appointment;
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
}
