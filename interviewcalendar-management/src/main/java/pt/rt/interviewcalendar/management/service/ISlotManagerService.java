package pt.rt.interviewcalendar.management.service;

import pt.rt.interviewcalendar.model.dto.SlotDTO;

public interface ISlotManagerService {

    SlotDTO saveSlot(SlotDTO slot);

    void deleteSlot(Long slotId);

}
