package hms.model.appointment.state;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentStatus;

public class ConfirmedState
        implements IAppointmentState,
                ICancellableAppointment,
                ICompletableAppointment,
                IFreeableAppointment,
                IPendableAppointment {
    @Override
    public String toString() {
        return "Confirmed";
    }

    /**
     * @param appointment
     */
    @Override
    public void cancel(Appointment appointment) {
        appointment.setState(new CancelledState());
    }

    /**
     * @param appointment
     */
    @Override
    public void complete(Appointment appointment) {
        appointment.setState(new CompletedState());
    }

    /**
     * @param appointment
     */
    @Override
    public void free(Appointment appointment) {
        appointment.setState(new FreeState());
    }

    /**
     * @param appointment
     */
    @Override
    public void pending(Appointment appointment) {
        appointment.setState(new PendingState());
    }

    /**
     * @return AppointmentStatus
     */
    @Override
    public AppointmentStatus getStatus() {
        return AppointmentStatus.CONFIRMED;
    }
}
