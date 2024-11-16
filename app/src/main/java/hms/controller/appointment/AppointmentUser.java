package hms.controller.appointment;

import java.util.List;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.model.record.MedicalRecord;
import hms.model.schedule.TimeSlot;
import hms.model.user.Doctor;

public interface AppointmentUser extends AppointmentBase {
    MedicalRecord getMedicalRecord();

    List<Doctor> getDoctors();

    List<List<TimeSlot>> getAvailableAppointmentSlots();

    Appointment scheduleAppointment(Doctor doctor, List<TimeSlot> ts);

    Appointment rescheduleAppointment(Appointment ap, List<TimeSlot> ts);

    List<Appointment> getScheduledAppointments();

    List<AppointmentOutcome> getPastAppointmentOutcomes();
}
