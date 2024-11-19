package hms.model.appointment.state;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentStatus;

public class CancelledState
        implements IAppointmentState, IFreeableAppointment, IPendableAppointment {
    @Override
    public String toString() {
        return "Cancelled";
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
        return AppointmentStatus.CANCELLED;
    }
}
