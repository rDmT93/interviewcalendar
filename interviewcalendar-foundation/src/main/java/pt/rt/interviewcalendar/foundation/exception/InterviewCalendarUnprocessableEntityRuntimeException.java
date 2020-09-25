package pt.rt.interviewcalendar.foundation.exception;

public class InterviewCalendarUnprocessableEntityRuntimeException extends InterviewCalendarRuntimeException {

    public static final String USER_UNIQUE = "User already exists with that name and role";
    public static final String SLOT_UNIQUE = "Slot already exists for that user";

    private static final long serialVersionUID = 7005468150165360247L;

    public InterviewCalendarUnprocessableEntityRuntimeException(String message) {
        super(message);
    }
}