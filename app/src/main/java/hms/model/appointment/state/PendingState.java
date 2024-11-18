package hms.model.appointment.state;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentStatus;

public class PendingState
        implements IAppointmentState, IConfirmableAppointment, ICancellableAppointment {
    @Override
    public void confirm(Appointment appointment) {
        appointment.setState(new ConfirmedState());
    }

    @Override
    public void cancel(Appointment appointment) {
        appointment.setState(new CancelledState());
    }

    @Override
    public AppointmentStatus getStatus() {
        return AppointmentStatus.PENDING;
    }
}