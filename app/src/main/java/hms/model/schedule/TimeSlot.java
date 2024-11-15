package hms.model.schedule;

import java.io.Serializable;
import java.time.LocalDateTime;

// Inner class to represent a time slot of 10 minutes
public class TimeSlot implements Serializable {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private TimeSlotStatus status;

    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        this.start = start.withSecond(0).withNano(0).plusMinutes((90 - start.getMinute()) % 30);
        ;
        this.end = end.withSecond(0).withNano(0).plusMinutes((90 - end.getMinute()) % 30);
        ;
        this.status = TimeSlotStatus.AVAILABLE; // Default status
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public TimeSlotStatus getStatus() {
        return status;
    }

    public void setStatus(TimeSlotStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return start + "-" + end + " (" + status + ")";
    }
}
