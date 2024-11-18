package hms.model.appointment.state;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentStatus;

class ConfirmedState
        implements IAppointmentState,
                ICancellableAppointment,
                ICompletableAppointment,
                IFreeableAppointment,
                IPendableAppointment {

    /**
     * @param appointment
     */
    @Override
    public void cancel(Appointment appointment) {
        appointment.setState(new CancelledState());
    }

    @Override
    public void complete(Appointment appointment) {
        appointment.setState(new CompletedState());
    }

    @Override
    public void free(Appointment appointment) {
        appointment.setState(new FreeState());
    }

    @Override
    public void pending(Appointment appointment) {
        appointment.setState(new PendingState());
    }

    @Override
    public AppointmentStatus getStatus() {
        return AppointmentStatus.CONFIRMED;
    }
}
