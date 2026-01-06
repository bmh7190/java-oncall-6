package oncall.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import oncall.domain.Schedule;
import oncall.domain.Work;
import oncall.dto.ScheduleDTO;
import oncall.dto.StartDayDTO;
import oncall.util.Holiday;

public class ScheduleService {
    private static final int YEAR = 2023;

    private final Schedule schedule = new Schedule();

    public void create(StartDayDTO startDayDTO, int startDay) {
        Integer month = startDayDTO.month();

        // 시작 일로부터 월 끝까지 돌면서 근무표 생성
        LocalDate start = LocalDate.of(YEAR, month, startDay);
        LocalDate end = YearMonth.of(YEAR, month).atEndOfMonth();

        for (LocalDate day = start; !day.isAfter(end); day = day.plusDays(1)) {
            schedule.addDate(day);
        }
    }

    public void assign(StartDayDTO startDayDTO, int startDay, List<String> weekday, List<String> weekend){
        int weekday_idx = 0;
        int weekend_idx = 0;

        LocalDate start = LocalDate.of(YEAR, startDayDTO.month(), startDay);
        LocalDate end = YearMonth.of(YEAR, startDayDTO.month()).atEndOfMonth();

        for (LocalDate day = start; !day.isAfter(end); day = day.plusDays(1)) {
            if(isWeekend(day)){
                String name = weekend.get(weekend_idx%weekend.size());
                schedule.addWorker(day, name);
                weekend_idx++;
                continue;
            }

            String name = weekday.get(weekday_idx%weekday.size());
            schedule.addWorker(day, name);
            weekday_idx++;
        }
    }

    public void check(StartDayDTO startDayDTO, int startDay) {

        LocalDate start = LocalDate.of(YEAR, startDayDTO.month(), startDay);
        LocalDate end = YearMonth.of(YEAR, startDayDTO.month()).atEndOfMonth();

        // next와 swapDay(next+1)가 모두 "이번 달"에 있어야 스왑 가능
        for (LocalDate day = start; !day.isAfter(end.minusDays(2)); day = day.plusDays(1)) {
            LocalDate next = day.plusDays(1);
            LocalDate swapDay = next.plusDays(1);

            if (!schedule.check(day, next)) {
                schedule.swap(day,next);
            }
        }
    }

    public List<ScheduleDTO> getSchedule(){
        Map<LocalDate, Work> work = schedule.getWork();
        List<ScheduleDTO> schedules = new ArrayList<>();

        for(Map.Entry<LocalDate, Work> entry : work.entrySet()){

            int month = entry.getKey().getMonthValue();
            int day = entry.getKey().getDayOfMonth();
            String name = entry.getValue().getWorker();
            DayOfWeek dayOfWeek = entry.getKey().getDayOfWeek();
            boolean isHoliday = Holiday.isHoliday(entry.getKey());

            schedules.add(new ScheduleDTO(month,day, dayOfWeek, name, isHoliday));
        }

        return schedules;
    }

    public boolean isWeekend(LocalDate date) {
        DayOfWeek day= date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY || Holiday.isHoliday(date);
    }

    public int findWeekDayOfMonth(StartDayDTO dto) {
        LocalDate first = LocalDate.of(YEAR, dto.month(), 1);

        int diff = dto.dayOfWeek().getValue() - first.getDayOfWeek().getValue();

        if (diff < 0) {
            diff += 7;
        }

        return 1 + diff;
    }

}
