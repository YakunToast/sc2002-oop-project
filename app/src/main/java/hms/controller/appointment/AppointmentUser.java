package hms.controller.appointment;

import java.util.List;
import java.util.Map;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.model.record.MedicalRecord;
import hms.model.user.Doctor;

public interface AppointmentUser extends AppointmentBase {
    MedicalRecord getMedicalRecord();

    List<Doctor> getAllDoctors();

    Map<Doctor, List<Appointment>> getAvailableAppointmentSlotsByDoctors();

    List<Appointment> getPersonalAppointments();

    List<Appointment> getAvailableAppointmentSlots();

    List<Doctor> getPersonalDoctors();

    boolean scheduleAppointment(Appointment ap);

    boolean rescheduleAppointment(Appointment oldAp, Appointment newAp);

    void cancelAppointment(Appointment ap);

    List<Appointment> getScheduledAppointments();

    List<AppointmentOutcome> getPastAppointmentOutcomes();
}
