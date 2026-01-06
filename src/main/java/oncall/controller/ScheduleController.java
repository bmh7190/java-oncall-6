package oncall.controller;

import java.util.ArrayList;
import java.util.List;
import oncall.dto.ScheduleDTO;
import oncall.dto.StartDayDTO;
import oncall.service.ScheduleService;
import oncall.view.InputView;
import oncall.view.OutputView;

public class ScheduleController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ScheduleService scheduleService;

    public ScheduleController(InputView inputView, OutputView outputView, ScheduleService scheduleService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.scheduleService = scheduleService;
    }

    public void run(){

        // 근무 월 입력 및 근무표 생성
        StartDayDTO startDayDTO = inputStartDayStep();

        int start = scheduleService.findWeekDayOfMonth(startDayDTO);
        scheduleService.create(startDayDTO,start);

        // 근무표 입력
        List<String> weekdayEmployee = new ArrayList<>();
        List<String> weekendEmployee = new ArrayList<>();

        inputEmployeeStep(weekdayEmployee, weekendEmployee);

        // 배정 및 확인
        scheduleService.assign(startDayDTO,start,weekdayEmployee,weekendEmployee);
        scheduleService.check(startDayDTO, start);

        // 결과 출력
        List<ScheduleDTO> schedules = scheduleService.getSchedule();
        outputView.printSchedules(schedules);
    }

    private StartDayDTO inputStartDayStep() {
        while (true) {
            try {
                return inputView.inputStartDay();     // 성공하면 step 종료
            } catch (IllegalArgumentException e) {
                handleError(e);
            }
        }
    }

    private void inputEmployeeStep(List<String> weekDay, List<String> weekend) {
        while (true) {
            try {

                List<String> weekDayEmployee = inputView.inputWeekDayEmployee();
                List<String> weekendEmployee = inputView.inputWeekendEmployee();

                weekDay.addAll(weekDayEmployee);
                weekend.addAll(weekendEmployee);

                return;
            } catch (IllegalArgumentException e) {
                handleError(e);
            }
        }
    }

    private void handleError(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }

}
