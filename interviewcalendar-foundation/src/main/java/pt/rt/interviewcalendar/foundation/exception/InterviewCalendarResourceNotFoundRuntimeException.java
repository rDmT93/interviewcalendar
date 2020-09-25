package pt.rt.interviewcalendar.foundation.exception;

public class InterviewCalendarResourceNotFoundRuntimeException extends InterviewCalendarRuntimeException {

    public static final String USER_NOT_FOUND = "User Not Found";
    public static final String SLOT_NOT_FOUND = "Slot Not Found";

    private static final long serialVersionUID = -1880759435680607287L;

    public InterviewCalendarResourceNotFoundRuntimeException(String message) {
        super(message);
    }
}
