package hms.model.appointment.state;

import java.io.Serializable;

import hms.model.appointment.AppointmentStatus;

public interface IAppointmentState extends Serializable {
    AppointmentStatus getStatus();
}
