package hms.model.schedule;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import hms.model.user.User;

public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;

    private final User owner;
    private List<TimeSlot> timeslots;

    public Schedule(User owner) {
        timeslots = new ArrayList<>();
        this.owner = owner;
    }

    public User getOwner() {
        return this.owner;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TimeSlot timeslot : timeslots) {
            sb.append(timeslot).append("\n");
        }
        return sb.toString();
    }

    public List<TimeSlot> getTimeSlots() {
        return timeslots;
    }

    // Method to add availability timeslot in 1-hour windows over a date range
    public void addSlots(LocalDate startDate, LocalDate endDate, LocalTime start, LocalTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            addDaySlots(currentDate, start, end);
            currentDate = currentDate.plusDays(1);
        }
    }

    private void addDaySlots(LocalDate date, LocalTime start, LocalTime end) {
        LocalDateTime slotStartTime = LocalDateTime.of(date, start);
        LocalDateTime slotEndTime = LocalDateTime.of(date, end);

        while (slotStartTime.isBefore(slotEndTime)) {
            LocalDateTime nextHour = slotStartTime.plusHours(1);
            timeslots.add(new TimeSlot(this.owner, slotStartTime, nextHour));
            slotStartTime = nextHour;
        }
    }

    // Method to remove availability timeslot by matching slots within a date and
    // time range
    public void removeSlots(
            LocalDate startDate, LocalDate endDate, LocalTime start, LocalTime end) {
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            removeDaySlots(currentDate, start, end);
            currentDate = currentDate.plusDays(1);
        }
    }

    private void removeDaySlots(LocalDate date, LocalTime start, LocalTime end) {
        LocalDateTime removeStartTime = LocalDateTime.of(date, start);
        LocalDateTime removeEndTime = LocalDateTime.of(date, end);

        timeslots.removeIf(
                slot ->
                        (slot.getStart().equals(removeStartTime)
                                || slot.getEnd().equals(removeEndTime)
                                || (slot.getStart().isAfter(removeStartTime)
                                        && slot.getStart().isBefore(removeEndTime))
                                || (slot.getEnd().isAfter(removeStartTime)
                                        && slot.getEnd().isBefore(removeEndTime))));
    }

    // Method to mark a time slot as available
    public void markAvailable(LocalDateTime start, LocalDateTime end) {
        updateSlotStatus(start, end, TimeSlotStatus.AVAILABLE);
    }

    // Method to mark a time slot as pending
    public void markPending(LocalDateTime start, LocalDateTime end) {
        updateSlotStatus(start, end, TimeSlotStatus.PENDING);
    }

    // Method to mark a time slot as blocked
    public void markBlocked(LocalDateTime start, LocalDateTime end) {
        updateSlotStatus(start, end, TimeSlotStatus.BLOCKED);
    }

    // Private helper method to update the status of a time slot
    private void updateSlotStatus(LocalDateTime start, LocalDateTime end, TimeSlotStatus status) {
        for (TimeSlot slot : timeslots) {
            if (slot.getStart().equals(start)
                    || slot.getEnd().equals(end)
                    || (slot.getStart().isAfter(start) && slot.getStart().isBefore(end))
                    || (slot.getEnd().isAfter(start) && slot.getEnd().isBefore(end))) {
                slot.setStatus(status);
            }
        }
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
}
