package hms.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule implements Serializable {
    private List<TimeSlot> timeslots;

    public Schedule() {
        timeslots = new ArrayList<>();
    }

    // Method to add availability timeslot in 1-hour windows over a date range
    public void add(LocalDate startDate, LocalDate endDate, LocalTime start, LocalTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            addDailyAvailability(currentDate, start, end);
            currentDate = currentDate.plusDays(1);
        }
    }

    private void addDailyAvailability(LocalDate date, LocalTime start, LocalTime end) {
        LocalDateTime slotStartTime = LocalDateTime.of(date, start);
        LocalDateTime slotEndTime = LocalDateTime.of(date, end);

        while (slotStartTime.isBefore(slotEndTime)) {
            LocalDateTime nextHour = slotStartTime.plusHours(1);
            timeslots.add(new TimeSlot(slotStartTime, nextHour));
            slotStartTime = nextHour;
        }
    }

    // Method to remove availability timeslot by matching slots within a date and
    // time range
    public void removeAvailability(LocalDate startDate, LocalDate endDate, LocalTime start, LocalTime end) {
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            removeDailyAvailability(currentDate, start, end);
            currentDate = currentDate.plusDays(1);
        }
    }

    private void removeDailyAvailability(LocalDate date, LocalTime start, LocalTime end) {
        LocalDateTime removeStartTime = LocalDateTime.of(date, start);
        LocalDateTime removeEndTime = LocalDateTime.of(date, end);

        timeslots.removeIf(slot -> (slot.getStart().equals(removeStartTime) || slot.getEnd().equals(removeEndTime) ||
                (slot.getStart().isAfter(removeStartTime) && slot.getStart().isBefore(removeEndTime)) ||
                (slot.getEnd().isAfter(removeStartTime) && slot.getEnd().isBefore(removeEndTime))));
    }

    // Helper function to filter timeslots
    public List<TimeSlot> filterTimeslots(LocalDateTime from, LocalDateTime to) {
        List<TimeSlot> filteredTimeslots = new ArrayList<>();
        for (TimeSlot slot : timeslots) {
            if (!slot.getStart().isBefore(from) && !slot.getEnd().isAfter(to)) {
                filteredTimeslots.add(slot);
            }
        }
        return filteredTimeslots;
    }

    // Enum to represent the status of a TimeSlot
    private enum TimeSlotStatus {
        AVAILABLE, PENDING, BLOCKED
    }

    // Inner class to represent a time slot of 10 minutes
    private class TimeSlot implements Serializable {
        private final LocalDateTime start;
        private final LocalDateTime end;
        private TimeSlotStatus status;

        public TimeSlot(LocalDateTime start, LocalDateTime end) {
            this.start = start.withSecond(0).withNano(0).plusMinutes((65 - start.getMinute()) % 10);
            ;
            this.end = end.withSecond(0).withNano(0).plusMinutes((65 - end.getMinute()) % 10);
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

}
