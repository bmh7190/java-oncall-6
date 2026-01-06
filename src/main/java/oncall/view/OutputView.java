package oncall.view;

import static java.lang.Character.LINE_SEPARATOR;

import java.util.Collections;
import java.util.List;
import oncall.dto.ScheduleDTO;
import oncall.util.KoreanDay;

public class OutputView {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void printSchedules(List<ScheduleDTO> schedules) {
        Collections.reverse(schedules);
        System.out.println();
        for (ScheduleDTO schedule : schedules) {

            if (schedule.isHoliday()) {
                printHolidaySchedule(schedule);
                continue;
            }

            printSchedule(schedule);
        }
    }

    void printSchedule(ScheduleDTO schedule) {
        int month = schedule.month();
        KoreanDay from = KoreanDay.from(schedule.dayOfWeek());
        String worker = schedule.worker();
        int day = schedule.day();

        System.out.printf("%d월 %d일 %s %s" + LINE_SEPARATOR, month, day, from, worker);
    }

    void printHolidaySchedule(ScheduleDTO schedule) {

        int month = schedule.month();
        KoreanDay from = KoreanDay.from(schedule.dayOfWeek());
        String worker = schedule.worker();
        int day = schedule.day();

        System.out.printf("%d월 %d일 %s(휴일) %s" + LINE_SEPARATOR, month, day, from, worker);
    }
}
