package pt.rt.interviewcalendar.service;

import pt.rt.interviewcalendar.foundation.exception.InterviewCalendarResourceNotFoundRuntimeException;
import pt.rt.interviewcalendar.model.domain.User;

import java.util.List;

public interface IUserRepositoryService {

    User save(User user);

    User get(Long userId) throws InterviewCalendarResourceNotFoundRuntimeException;

    List<User> getAll();

    List<User> getAllById(List<Long> userIdList) throws InterviewCalendarResourceNotFoundRuntimeException;

    void deleteUser(Long userId);
}
