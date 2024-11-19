package hms.controller.appointment;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;
import hms.model.medication.Prescription;

public interface AppointmentDoctor {
    boolean acceptAppointment(Appointment ap);

    boolean declineAppointment(Appointment ap);

    public boolean freeAppointment(Appointment ap);

    boolean addAppointmentOutcome(Appointment ap, String description, Prescription pr);

    AppointmentOutcome getAppointmentOutcome(Appointment ap);
}
