package pt.rt.interviewcalendar.management.validator;

import org.springframework.stereotype.Component;
import pt.rt.interviewcalendar.foundation.exception.InterviewCalendarUnprocessableEntityRuntimeException;
import pt.rt.interviewcalendar.model.dto.UserDTO;

import java.util.function.Consumer;

@Component("userValidator")
public class UserValidator implements Consumer<UserDTO> {

    @Override
    public void accept(UserDTO userDTO) {
        validateRequiredFields(userDTO);
        validateRole(userDTO);
    }

    private void validateRequiredFields(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
    }

    private void validateRole(UserDTO userDTO) {
        if (userDTO.getRole() == null) {
            throw new InterviewCalendarUnprocessableEntityRuntimeException("Invalid role");
        }
    }
}
