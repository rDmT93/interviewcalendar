package pt.rt.interviewcalendar.service;

import pt.rt.interviewcalendar.model.domain.Slot;

public interface ISlotRepositoryService {

    Slot save(Slot slot);

    void deleteSlot(Long slotId);
}
