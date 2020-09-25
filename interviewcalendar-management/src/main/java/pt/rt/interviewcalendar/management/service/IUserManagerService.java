package pt.rt.interviewcalendar.management.service;

import pt.rt.interviewcalendar.model.dto.SlotDTO;
import pt.rt.interviewcalendar.model.dto.UserDTO;

import java.util.List;

public interface IUserManagerService {

    UserDTO saveUser(UserDTO user);

    UserDTO getUser(Long userId);

    List<UserDTO> listUsers();

    void deleteUser(Long userId);

    List<SlotDTO> listUserSlots(Long userId);
}
