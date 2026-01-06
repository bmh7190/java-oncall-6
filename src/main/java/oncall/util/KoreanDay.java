package oncall.util;

import java.time.DayOfWeek;
import java.util.Arrays;

public enum KoreanDay {

    월(DayOfWeek.MONDAY),
    화(DayOfWeek.TUESDAY),
    수(DayOfWeek.WEDNESDAY),
    목(DayOfWeek.THURSDAY),
    금(DayOfWeek.FRIDAY),
    토(DayOfWeek.SATURDAY),
    일(DayOfWeek.SUNDAY);

    private final DayOfWeek dayOfWeek;

    KoreanDay(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public DayOfWeek toDayOfWeek() {
        return dayOfWeek;
    }

    public static KoreanDay from(String value) {
        try {
            return KoreanDay.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessage.of("유효하지 않은 입력 값입니다. 다시 입력해 주세요."));
        }
    }

    public static KoreanDay from(DayOfWeek dayOfWeek) {
        return Arrays.stream(KoreanDay.values())
                .filter(koreanDay -> koreanDay.toDayOfWeek() == dayOfWeek)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                ErrorMessage.of("유효하지 않은 입력 값입니다. 다시 입력해 주세요.")
                        )
                );
    }
}
