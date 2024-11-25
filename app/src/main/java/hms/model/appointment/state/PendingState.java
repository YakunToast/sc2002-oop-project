package hms.model.appointment.state;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentStatus;

public class PendingState
        implements IAppointmentState,
                IConfirmableAppointment,
                ICancellableAppointment,
                IFreeableAppointment {
    @Override
    public String toString() {
        return "Pending";
    }

    /**
     * @param appointment
     */
    @Override
    public void confirm(Appointment appointment) {
        appointment.setState(new ConfirmedState());
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
    public void free(Appointment appointment) {
        appointment.setState(new FreeState());
    }

    /**
     * @return AppointmentStatus
     */
    @Override
    public AppointmentStatus getStatus() {
        return AppointmentStatus.PENDING;
    }
}
