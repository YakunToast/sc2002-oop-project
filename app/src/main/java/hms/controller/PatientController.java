package hms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import hms.controller.appointment.AppointmentUser;
import hms.model.appointment.Appointment;
import hms.model.appointment.Appointment.AppointmentStatus;
import hms.model.appointment.AppointmentOutcome;
import hms.model.record.MedicalRecord;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.repository.RepositoryManager;

public class PatientController implements AppointmentUser {
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

    @Override
    public MedicalRecord getMedicalRecord() {
        return this.patient.getMedicalRecord();
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return RepositoryManager.getInstance().getUserRepository().getAllUsers().stream()
                .filter(u -> u instanceof Doctor)
                .map(u -> (Doctor) u)
                .collect(Collectors.toList());
    }

    @Override
    public List<Doctor> getPersonalDoctors() {
        return this.getPersonalAppointments().stream()
                .map(a -> a.getDoctor())
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getPersonalAppointments() {
        return RepositoryManager.getInstance()
                .getAppointmentRepository()
                .getAllAppointments()
                .stream()
                .filter(a -> a.getPatient() == patient)
                .collect(Collectors.toList());
    }

    @Override
    public HashMap<Doctor, List<Appointment>> getAvailableAppointmentSlotsByDoctors() {
        return this.getAllDoctors().stream()
                .collect(
                        Collectors.toMap(
                                doctor -> doctor,
                                doctor ->
                                        doctor.getSchedule().getAppointments().stream()
                                                .filter(
                                                        appointment ->
                                                                appointment.getStatus()
                                                                        == AppointmentStatus.FREE)
                                                .collect(Collectors.toList()),
                                (prev, next) -> next,
                                HashMap::new));
    }

    @Override
    public List<Appointment> getAvailableAppointmentSlots() {
        return this.getAvailableAppointmentSlotsByDoctors().values().stream()
                .flatMap(aps -> aps.stream())
                .collect(Collectors.toList());
    }

    @Override
    public boolean scheduleAppointment(Appointment ap) {
        if (!ap.isFree()) {
            return false;
        }

        ap.setPatient(this.patient);
        ap.setStatus(AppointmentStatus.PENDING);
        return true;
    }

    @Override
    public boolean rescheduleAppointment(Appointment oldAp, Appointment newAp) {
        if (!newAp.isFree()) {
            return false;
        }

        if (oldAp.getPatient() != patient) {
            return false;
        }

        oldAp.setPatient(null);
        oldAp.setStatus(Appointment.AppointmentStatus.FREE);

        this.scheduleAppointment(newAp);

        return true;
    }

    @Override
    public void cancelAppointment(Appointment ap) {
        AppointmentController ac = new AppointmentController(ap);
        ac.cancelAppointment();
    }

    @Override
    public List<Appointment> getScheduledAppointments() {
        // TODO: Filter by date or status?
        return this.getPersonalAppointments().stream().collect(Collectors.toList());
    }

    @Override
    public List<AppointmentOutcome> getPastAppointmentOutcomes() {
        return this.getPersonalAppointments().stream()
                .filter(ap -> ap.getOutcome() != null)
                .map(ap -> ap.getOutcome())
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentOutcome getAppointmentOutcome(Appointment ap) {
        return ap.getOutcome();
    }
}
