package oncall.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

public enum Holiday {

    NEW_YEAR("신정", 1, 1),
    THREE_ONE("삼일절", 3, 1),
    CHILDRENS_DAY("어린이날", 5, 5),
    MEMORIAL_DAY("현충일", 6, 6),
    LIBERATION_DAY("광복절", 8, 15),
    NATIONAL_FOUNDATION_DAY("개천절", 10, 3),
    HANGEUL_DAY("한글날", 10, 9),
    CHRISTMAS_DAY("성탄절", 12, 25);

    private final String koreanName;
    private final int month;
    private final int day;

    Holiday(String koreanName, int month, int day) {
        this.koreanName = koreanName;
        this.month = month;
        this.day = day;
    }

    public boolean matches(LocalDate date) {
        return this.month == date.getMonthValue()
                && this.day == date.getDayOfMonth();
    }

    public static boolean isHoliday(LocalDate date) {
        return Arrays.stream(values())
                .anyMatch(h -> h.matches(date));
    }
}

