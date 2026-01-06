package oncall.view;

import camp.nextstep.edu.missionutils.Console;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import oncall.dto.StartDayDTO;
import oncall.util.ErrorMessage;
import oncall.util.KoreanDay;

public class InputView {

    public StartDayDTO inputStartDay() {
        System.out.print("비상 근무를 배정할 월과 시작 요일을 입력하세요> ");
        String string = Console.readLine();

        return parseStartDay(string);
    }

    public List<String> inputWeekDayEmployee() {
        System.out.print("평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
        String string = Console.readLine();

        return parseEmployee(string);
    }

    public List<String> inputWeekendEmployee() {
        System.out.print("휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
        String string = Console.readLine();

        return parseEmployee(string);
    }

    private List<String> parseEmployee(String string) {

        List<String> employees = Arrays.stream(string.split(",")).toList();
        validateEmployees(employees);

        return employees;
    }

    private void validateEmployees(List<String> employees) {

        validateName(employees);
        validateCount(employees);
        validateDuplicate(employees);

    }

    private void validateName(List<String> names) {
        boolean hasInvalid = names.stream()
                .anyMatch(name -> name.length() > 5);

        if (hasInvalid) {
            throw new IllegalArgumentException(
                    ErrorMessage.of("유효하지 않은 입력 값입니다. 다시 입력해 주세요.")
            );
        }
    }

    private void validateCount(List<String> names) {
        int size = names.size();

        if (size < 5 || size > 35) {
            throw new IllegalArgumentException(
                    ErrorMessage.of("유효하지 않은 입력 값입니다. 다시 입력해 주세요.")
            );
        }
    }

    private void validateDuplicate(List<String> names) {

        long count = names.stream()
                .distinct()
                .count();

        if (names.size() != count) {
            throw new IllegalArgumentException(ErrorMessage.of("유효하지 않은 입력 값입니다. 다시 입력해 주세요."));
        }
    }

    private StartDayDTO parseStartDay(String input) {
        List<String> tokens = Arrays.stream(input.split(","))
                .map(String::trim)
                .toList();

        validateTokenSize(tokens);

        int month = Integer.parseInt(tokens.get(0).trim());
        DayOfWeek dayOfWeek = KoreanDay.from(tokens.get(1)).toDayOfWeek();

        validateStartDay(month);

        return new StartDayDTO(month, dayOfWeek);
    }

    private void validateTokenSize(List<String> tokens) {
        if (tokens.size() != 2) {
            throw new IllegalArgumentException(
                    ErrorMessage.of("유효하지 않은 입력 값입니다. 다시 입력해 주세요.")
            );
        }
    }

    private void validateStartDay(int month) {
        if (month > 0 && month < 13) {
            return;
        }
        throw new IllegalArgumentException(ErrorMessage.of("유효하지 않은 입력 값입니다. 다시 입력해 주세요."));
    }


}
