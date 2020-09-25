package pt.rt.interviewcalendar.management.validator;

import org.springframework.stereotype.Component;
import pt.rt.interviewcalendar.foundation.exception.InterviewCalendarUnprocessableEntityRuntimeException;
import pt.rt.interviewcalendar.model.domain.User;

import java.util.function.BiConsumer;


@Component("roleValidator")
public class RoleValidator implements BiConsumer<User, String> {

    @Override
    public void accept(User user, String role) {
        if (!role.equals(user.getRole())) {
            throw new InterviewCalendarUnprocessableEntityRuntimeException("User has invalid role: " + user.getUserId());
        }
    }
}
