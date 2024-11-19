package hms.model.appointment.state;

import hms.model.appointment.AppointmentStatus;

public class CompletedState implements IAppointmentState {
    @Override
    public String toString() {
        return "Completed";
    }

    /**
     * @return AppointmentStatus
     */
    @Override
    public AppointmentStatus getStatus() {
        return AppointmentStatus.COMPLETED;
    }
}
