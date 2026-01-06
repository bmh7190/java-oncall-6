package oncall.dto;

import java.time.DayOfWeek;

public record ScheduleDTO(int month, int day, DayOfWeek dayOfWeek, String worker, boolean isHoliday) {
}
