package hms.model.appointment.state;

import hms.model.appointment.AppointmentStatus;

public interface IAppointmentState {
    AppointmentStatus getStatus();
}
