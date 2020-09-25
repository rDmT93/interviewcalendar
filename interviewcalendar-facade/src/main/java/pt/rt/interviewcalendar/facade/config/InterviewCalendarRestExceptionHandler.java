package pt.rt.interviewcalendar.facade.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pt.rt.interviewcalendar.api.model.Error;
import pt.rt.interviewcalendar.foundation.exception.InterviewCalendarResourceNotFoundRuntimeException;
import pt.rt.interviewcalendar.foundation.exception.InterviewCalendarUnprocessableEntityRuntimeException;

@ControllerAdvice
public class InterviewCalendarRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InterviewCalendarResourceNotFoundRuntimeException.class})
    public ResponseEntity<Object> handleResourceNotFound(final InterviewCalendarResourceNotFoundRuntimeException e, final WebRequest request) {
        final Error error = new Error().code(HttpStatus.NOT_FOUND.value()).message(e.getLocalizedMessage());
        return new ResponseEntity<Object>(error, HttpStatus.valueOf(error.getCode()));
    }

    @ExceptionHandler({InterviewCalendarUnprocessableEntityRuntimeException.class})
    public ResponseEntity<Object> handleUnprocessableEntity(final InterviewCalendarUnprocessableEntityRuntimeException e, final WebRequest request) {
        final Error error = new Error().code(HttpStatus.UNPROCESSABLE_ENTITY.value()).message(get422Message(e.getLocalizedMessage()));
        return new ResponseEntity<Object>(error, HttpStatus.valueOf(error.getCode()));
    }

    private String get422Message(String exceptionMsg) {
        String[] cases = {"USER_UNIQUE", "SLOT_UNIQUE"};

        int i;
        for (i = 0; i < cases.length; i++) {
            if (exceptionMsg.contains(cases[i])) break;
        }

        switch (i) {
            case 0:
                return InterviewCalendarUnprocessableEntityRuntimeException.USER_UNIQUE;
            case 1:
                return InterviewCalendarUnprocessableEntityRuntimeException.SLOT_UNIQUE;
            default:
                return exceptionMsg;
        }
    }
}