package hms.model.appointment.state;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentStatus;

public class CompletedState
        implements IAppointmentState, IFreeableAppointment, IPendableAppointment {

    /**
     * @param appointment
     */
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
        return AppointmentStatus.COMPLETED;
    }
}
