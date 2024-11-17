package hms.model.appointment.state;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentStatus;

public class CancelledState
        implements IAppointmentState, IFreeableAppointment, IPendableAppointment {
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
        return AppointmentStatus.CANCELLED;
    }
}
