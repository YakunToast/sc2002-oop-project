package hms.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import hms.controller.appointment.AppointmentDoctor;
import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.model.appointment.AppointmentStatus;
import hms.model.appointment.Schedule;
import hms.model.medication.Prescription;
import hms.model.record.MedicalRecord;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.UserRole;
import hms.repository.RepositoryManager;

public class DoctorController implements AppointmentDoctor {
    private final Doctor doctor;

    public DoctorController(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * @return List<Patient>
     */
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

    public List<Appointment> getFreeAppointments() {
        return this.getAppointments().stream().filter(a -> a.isFree()).collect(Collectors.toList());
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

    public Appointment addAppointment(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return doctor.getSchedule().addAppointment(startDateTime, endDateTime);
    }

    public List<Appointment> addAppointmentHourly(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return doctor.getSchedule().addAppointmentHourly(startDateTime, endDateTime);
    }

    public List<Appointment> addMultipleAppointmentDays(
            LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return doctor.getSchedule()
                .addMultipleAppointmentDays(startDate, endDate, startTime, endTime);
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
        sc.addAppointment(start, end);
        // TODO: Need to better check success of this
        return true;
    }

    @Override
    public boolean acceptAppointment(Appointment ap) {
        if (ap.getDoctor() != doctor) {
            return false;
        }
        return new AppointmentController(ap).accept();
    }

    @Override
    public boolean declineAppointment(Appointment ap) {
        if (ap.getDoctor() != doctor) {
            return false;
        }
        return new AppointmentController(ap).decline();
    }

    @Override
    public boolean addAppointmentOutcome(Appointment ap, String description, Prescription pr) {
        // Save appointment outcome
        AppointmentOutcome ao = new AppointmentOutcome(ap, description, pr);
        ap.setOutcome(ao);
        ap.setStatus(AppointmentStatus.COMPLETED);
        return true;
    }

    @Override
    public AppointmentOutcome getAppointmentOutcome(Appointment ap) {
        return ap.getOutcome();
    }
}
