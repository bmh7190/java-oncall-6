package oncall.util;

public final class ErrorMessage {

    private static final String PREFIX = "[ERROR] ";

    private ErrorMessage() {
        // 인스턴스화 방지
    }

    public static String of(String message) {
        return PREFIX + message;
    }
}