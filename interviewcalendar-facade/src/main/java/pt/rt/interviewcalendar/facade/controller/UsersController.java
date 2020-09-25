package pt.rt.interviewcalendar.facade.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pt.rt.interviewcalendar.api.UsersApi;
import pt.rt.interviewcalendar.api.model.Slot;
import pt.rt.interviewcalendar.api.model.User;
import pt.rt.interviewcalendar.api.model.UserId;
import pt.rt.interviewcalendar.management.service.IUserManagerService;
import pt.rt.interviewcalendar.model.dto.UserDTO;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsersController implements UsersApi {

    private final IUserManagerService userManagerService;
    private final ModelMapper modelMapper;

    @Autowired
    public UsersController(IUserManagerService userManagerService, ModelMapper modelMapper) {
        this.userManagerService = userManagerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<UserId> createUser(@Valid User body) {
        UserDTO savedUserDTO = this.userManagerService.saveUser(this.modelMapper.map(body, UserDTO.class));
        UserId userId = new UserId();
        userId.setId(savedUserDTO.getUserId());
        return ResponseEntity.ok(userId);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        this.userManagerService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<User> getUserById(Long userId) {
        return ResponseEntity.ok(this.modelMapper.map(this.userManagerService.getUser(userId), User.class));
    }

    @Override
    public ResponseEntity<List<Slot>> getUserSlots(Long userId) {
        return ResponseEntity.ok(this.userManagerService.listUserSlots(userId)
                .stream()
                .map(s -> this.modelMapper.map(s, Slot.class))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(this.userManagerService.listUsers()
                .stream()
                .map(u -> this.modelMapper.map(u, User.class))
                .collect(Collectors.toList()));
    }
}
