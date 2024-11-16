package hms.controller;

import java.util.List;
import java.util.stream.Collectors;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.model.record.MedicalRecord;
import hms.model.schedule.TimeSlot;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.repository.RepositoryManager;

public class PatientController {
    private final Patient patient;

    public PatientController(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setEmail(String email) {
        this.patient.setEmail(email);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.patient.setPhoneNumber(phoneNumber);
    }

    public MedicalRecord getMedicalRecord() {
        return this.patient.getMedicalRecord();
    }

    public List<Appointment> getAppointments() {
        return RepositoryManager.getInstance()
                .getAppointmentRepository()
                .getAllAppointments()
                .stream()
                .filter(a -> a.getPatient() == patient)
                .collect(Collectors.toList());
    }

    public List<Doctor> getDoctors() {
        return this.getAppointments().stream().map(a -> a.getDoctor()).collect(Collectors.toList());
    }

    public List<List<TimeSlot>> getAvailableAppointmentSlots() {
        return this.getDoctors().stream()
                .map(e -> e.getSchedule().getSlots())
                .collect(Collectors.toList());
    }

    public Appointment scheduleAppointment(Doctor doctor, List<TimeSlot> ts) {
        return AppointmentController.createAppointment(doctor, patient, ts);
    }

    public Appointment rescheduleAppointment(Appointment ap, List<TimeSlot> ts) {
        Doctor doctor = ap.getDoctor();

        AppointmentController.cancelAppointment(ap);

        return this.scheduleAppointment(doctor, ts);
    }

    public List<Appointment> getScheduledAppointments() {
        // TODO: Filter by date or status?
        return this.getAppointments().stream().collect(Collectors.toList());
    }

    public List<AppointmentOutcome> getPastAppointmentOutcomes() {
        return this.getAppointments().stream()
                .filter(ap -> ap.getOutcome() != null)
                .map(ap -> ap.getOutcome())
                .collect(Collectors.toList());
    }
}
