package hms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import hms.controller.appointment.AppointmentUser;
import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.model.record.MedicalRecord;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.repository.RepositoryManager;

/**
 * This class provides the functionality for a patient to manage their medical appointments and
 * medical records.
 */
public class PatientController implements AppointmentUser {
    private final Patient patient;

    /**
     * Constructs a new PatientController with the specified patient.
     *
     * @param patient the patient to be controlled
     */
    public PatientController(Patient patient) {
        this.patient = patient;
    }

    /**
     * Returns the patient associated with this controller.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return this.patient;
    }

    /**
     * Sets the email address of the patient.
     *
     * @param email the email address to be set
     */
    public void setEmail(String email) {
        this.patient.setEmail(email);
    }

    /**
     * Sets the phone number of the patient.
     *
     * @param phoneNumber the phone number to be set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.patient.setPhoneNumber(phoneNumber);
    }

    /**
     * Returns the medical record of the patient.
     *
     * @return the medical record
     */
    @Override
    public MedicalRecord getMedicalRecord() {
        return this.patient.getMedicalRecord();
    }

    /**
     * Returns a list of all doctors in the system.
     *
     * @return the list of all doctors
     */
    @Override
    public List<Doctor> getAllDoctors() {
        return RepositoryManager.getInstance().getUserRepository().getAllUsers().stream()
                .filter(u -> u instanceof Doctor)
                .map(u -> (Doctor) u)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of doctors with whom the patient has personal appointments.
     *
     * @return the list of personal doctors
     */
    @Override
    public List<Doctor> getPersonalDoctors() {
        return this.getPersonalAppointments().stream()
                .map(a -> a.getDoctor())
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of all appointments for the patient.
     *
     * @return the list of personal appointments
     */
    @Override
    public List<Appointment> getPersonalAppointments() {
        return RepositoryManager.getInstance()
                .getAppointmentRepository()
                .getAllAppointments()
                .stream()
                .filter(a -> a.getPatient() == patient)
                .collect(Collectors.toList());
    }

    /**
     * Returns a map of doctors and their available appointment slots.
     *
     * @return the map of available appointment slots by doctor
     */
    @Override
    public HashMap<Doctor, List<Appointment>> getAvailableAppointmentSlotsByDoctors() {
        return this.getAllDoctors().stream()
                .collect(
                        Collectors.toMap(
                                doctor -> doctor,
                                doctor ->
                                        doctor.getSchedule().getAppointments().stream()
                                                .filter(appointment -> appointment.isFree())
                                                .collect(Collectors.toList()),
                                (prev, next) -> next,
                                HashMap::new));
    }

    /**
     * Returns a list of all available appointment slots.
     *
     * @return the list of available appointment slots
     */
    @Override
    public List<Appointment> getAvailableAppointmentSlots() {
        return this.getAvailableAppointmentSlotsByDoctors().values().stream()
                .flatMap(aps -> aps.stream())
                .collect(Collectors.toList());
    }

    /**
     * Schedules an appointment for the patient.
     *
     * @param ap the appointment to be scheduled
     * @return true if the appointment was scheduled successfully, false otherwise
     */
    @Override
    public boolean scheduleAppointment(Appointment ap) {
        if (!ap.isFree()) {
            return false;
        }

        ap.setPatient(this.patient);
        ap.pending();
        return true;
    }

    /**
     * Reschedules an appointment for the patient.
     *
     * @param oldAp the current appointment
     * @param newAp the new appointment to be scheduled
     * @return true if the appointment was rescheduled successfully, false otherwise
     */
    @Override
    public boolean rescheduleAppointment(Appointment oldAp, Appointment newAp) {
        if (!newAp.isFree()) {
            return false;
        }

        if (oldAp.getPatient() != patient) {
            return false;
        }

        oldAp.setPatient(null);
        oldAp.free();

        this.scheduleAppointment(newAp);

        return true;
    }

    /**
     * Cancels an appointment for the patient.
     *
     * @param ap the appointment to be canceled
     */
    @Override
    public void cancelAppointment(Appointment ap) {
        AppointmentController ac = new AppointmentController(ap);
        ac.decline();
    }

    /**
     * Returns a list of all scheduled appointments for the patient.
     *
     * @return the list of scheduled appointments
     */
    @Override
    public List<Appointment> getScheduledAppointments() {
        return this.getPersonalAppointments().stream()
                .filter(ap -> ap.isPending() || ap.isConfirmed())
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of past appointment outcomes for the patient.
     *
     * @return the list of past appointment outcomes
     */
    @Override
    public List<AppointmentOutcome> getPastAppointmentOutcomes() {
        return this.getPersonalAppointments().stream()
                .filter(ap -> ap.getOutcome() != null)
                .map(ap -> ap.getOutcome())
                .collect(Collectors.toList());
    }

    /**
     * Returns the outcome of a specific appointment.
     *
     * @param ap the appointment
     * @return the appointment outcome
     */
    @Override
    public AppointmentOutcome getAppointmentOutcome(Appointment ap) {
        return ap.getOutcome();
    }
}
