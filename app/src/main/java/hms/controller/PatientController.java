package hms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import hms.controller.appointment.AppointmentUser;
import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.model.appointment.AppointmentStatus;
import hms.model.record.MedicalRecord;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.repository.RepositoryManager;

public class PatientController implements AppointmentUser {
    private final Patient patient;

    public PatientController(Patient patient) {
        this.patient = patient;
    }

    /** 
     * @return Patient
     */
    public Patient getPatient() {
        return this.patient;
    }

    
    /** 
     * @param email
     */
    public void setEmail(String email) {
        this.patient.setEmail(email);
    }

    
    /** 
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.patient.setPhoneNumber(phoneNumber);
    }

    
    /** 
     * @return MedicalRecord
     */
    @Override
    public MedicalRecord getMedicalRecord() {
        return this.patient.getMedicalRecord();
    }

    
    /** 
     * @return List<Doctor>
     */
    @Override
    public List<Doctor> getAllDoctors() {
        return RepositoryManager.getInstance().getUserRepository().getAllUsers().stream()
                .filter(u -> u instanceof Doctor)
                .map(u -> (Doctor) u)
                .collect(Collectors.toList());
    }

    
    /** 
     * @return List<Doctor>
     */
    @Override
    public List<Doctor> getPersonalDoctors() {
        return this.getPersonalAppointments().stream()
                .map(a -> a.getDoctor())
                .collect(Collectors.toList());
    }

    
    /** 
     * @return List<Appointment>
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
     * @return HashMap<Doctor, List<Appointment>>
     */
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

    
    /** 
     * @return List<Appointment>
     */
    @Override
    public List<Appointment> getAvailableAppointmentSlots() {
        return this.getAvailableAppointmentSlotsByDoctors().values().stream()
                .flatMap(aps -> aps.stream())
                .collect(Collectors.toList());
    }

    
    /** 
     * @param ap
     * @return boolean
     */
    @Override
    public boolean scheduleAppointment(Appointment ap) {
        if (!ap.isFree()) {
            return false;
        }

        ap.setPatient(this.patient);
        ap.setStatus(AppointmentStatus.PENDING);
        return true;
    }

    
    /** 
     * @param oldAp
     * @param newAp
     * @return boolean
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
        oldAp.setStatus(AppointmentStatus.FREE);

        this.scheduleAppointment(newAp);

        return true;
    }

    
    /** 
     * @param ap
     */
    @Override
    public void cancelAppointment(Appointment ap) {
        AppointmentController ac = new AppointmentController(ap);
        ac.cancelAppointment();
    }

    
    /** 
     * @return List<Appointment>
     */
    @Override
    public List<Appointment> getScheduledAppointments() {
        // TODO: Filter by date or status?
        return this.getPersonalAppointments().stream().collect(Collectors.toList());
    }

    
    /** 
     * @return List<AppointmentOutcome>
     */
    @Override
    public List<AppointmentOutcome> getPastAppointmentOutcomes() {
        return this.getPersonalAppointments().stream()
                .filter(ap -> ap.getOutcome() != null)
                .map(ap -> ap.getOutcome())
                .collect(Collectors.toList());
    }

    
    /** 
     * @param ap
     * @return AppointmentOutcome
     */
    @Override
    public AppointmentOutcome getAppointmentOutcome(Appointment ap) {
        return ap.getOutcome();
    }
}
