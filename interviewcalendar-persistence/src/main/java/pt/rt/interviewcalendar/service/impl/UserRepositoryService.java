package pt.rt.interviewcalendar.service.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pt.rt.interviewcalendar.foundation.exception.InterviewCalendarResourceNotFoundRuntimeException;
import pt.rt.interviewcalendar.foundation.exception.InterviewCalendarUnprocessableEntityRuntimeException;
import pt.rt.interviewcalendar.model.domain.User;
import pt.rt.interviewcalendar.repository.IUserRepository;
import pt.rt.interviewcalendar.service.IUserRepositoryService;

import java.util.List;

@Service
public class UserRepositoryService implements IUserRepositoryService {

    private final IUserRepository userRepository;

    @Autowired
    public UserRepositoryService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        try {
            return this.userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new InterviewCalendarUnprocessableEntityRuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public User get(Long userId) throws InterviewCalendarResourceNotFoundRuntimeException {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new InterviewCalendarResourceNotFoundRuntimeException(InterviewCalendarResourceNotFoundRuntimeException.USER_NOT_FOUND));
    }

    @Override
    public List<User> getAll() {
        return Lists.newArrayList(this.userRepository.findAll());
    }

    @Override
    public List<User> getAllById(List<Long> userIdList) throws InterviewCalendarResourceNotFoundRuntimeException {
        return Lists.newArrayList(this.userRepository.findAllById(userIdList));
    }

    @Override
    public void deleteUser(Long userId) {
        try {
            this.userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new InterviewCalendarResourceNotFoundRuntimeException(InterviewCalendarResourceNotFoundRuntimeException.USER_NOT_FOUND);
        }
    }
}
