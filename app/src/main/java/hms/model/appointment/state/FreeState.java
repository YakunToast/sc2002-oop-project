package hms.model.appointment.state;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentStatus;

public class FreeState implements IAppointmentState, IConfirmableAppointment, IPendableAppointment {
    @Override
    public String toString() {
        return "Free";
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
    public void pending(Appointment appointment) {
        appointment.setState(new PendingState());
    }

    /**
     * @return AppointmentStatus
     */
    @Override
    public AppointmentStatus getStatus() {
        return AppointmentStatus.FREE;
    }
}
