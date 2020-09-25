package pt.rt.interviewcalendar.management.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.rt.interviewcalendar.management.service.IUserManagerService;
import pt.rt.interviewcalendar.management.validator.UserValidator;
import pt.rt.interviewcalendar.model.domain.User;
import pt.rt.interviewcalendar.model.dto.SlotDTO;
import pt.rt.interviewcalendar.model.dto.UserDTO;
import pt.rt.interviewcalendar.service.IUserRepositoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManagerService implements IUserManagerService {

    private final UserValidator userValidator;
    private final IUserRepositoryService userRepositoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserManagerService(UserValidator userValidator,
                              IUserRepositoryService userRepositoryService,
                              ModelMapper modelMapper) {
        this.userValidator = userValidator;
        this.userRepositoryService = userRepositoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO saveUser(UserDTO user) {
        userValidator.accept(user);
        User newUser = this.userRepositoryService.save(this.modelMapper.map(user, User.class));
        return this.modelMapper.map(newUser, UserDTO.class);
    }

    @Override
    public UserDTO getUser(Long userId) {
        return this.modelMapper.map(this.userRepositoryService.get(userId), UserDTO.class);
    }

    @Override
    public List<UserDTO> listUsers() {
        return this.userRepositoryService.getAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepositoryService.deleteUser(userId);
    }

    @Override
    public List<SlotDTO> listUserSlots(Long userId) {
        return this.userRepositoryService.get(userId).getSlots()
                .stream()
                .map(s -> {
                    SlotDTO slot = this.modelMapper.map(s, SlotDTO.class);
                    slot.setUserId(userId);
                    return slot;
                })
                .collect(Collectors.toList());
    }
}
