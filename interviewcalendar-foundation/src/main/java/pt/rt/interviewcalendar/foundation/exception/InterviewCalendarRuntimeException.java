package pt.rt.interviewcalendar.foundation.exception;

public abstract class InterviewCalendarRuntimeException extends RuntimeException {

    public static final String TECH_ERROR = "Technical Error";

    public InterviewCalendarRuntimeException(String message) {
        super(message);
    }
}
