package pt.rt.interviewcalendar.management.service.impl;

import org.joda.time.Interval;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.rt.interviewcalendar.foundation.exception.InterviewCalendarUnprocessableEntityRuntimeException;
import pt.rt.interviewcalendar.management.mapper.DateStartAnDateEnd2IntervalMapper;
import pt.rt.interviewcalendar.management.service.ISlotManagerService;
import pt.rt.interviewcalendar.management.validator.SlotValidator;
import pt.rt.interviewcalendar.model.domain.Slot;
import pt.rt.interviewcalendar.model.domain.User;
import pt.rt.interviewcalendar.model.dto.SlotDTO;
import pt.rt.interviewcalendar.service.ISlotRepositoryService;
import pt.rt.interviewcalendar.service.IUserRepositoryService;

import java.util.List;

@Service
public class SlotManagerService implements ISlotManagerService {

    private final SlotValidator slotValidator;
    private final DateStartAnDateEnd2IntervalMapper dateStartAnDateEnd2IntervalMapper;
    private final IUserRepositoryService userRepositoryService;
    private final ISlotRepositoryService slotRepositoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public SlotManagerService(SlotValidator slotValidator,
                              DateStartAnDateEnd2IntervalMapper DateStartAnDateEnd2IntervalMapper,
                              IUserRepositoryService userRepositoryService,
                              ISlotRepositoryService slotRepositoryService,
                              ModelMapper modelMapper) {
        this.slotValidator = slotValidator;
        this.dateStartAnDateEnd2IntervalMapper = DateStartAnDateEnd2IntervalMapper;
        this.userRepositoryService = userRepositoryService;
        this.slotRepositoryService = slotRepositoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public SlotDTO saveSlot(SlotDTO slot) {
        slotValidator.accept(slot);
        User user = this.userRepositoryService.get(slot.getUserId());
        if (hasSlotConflict(slot)) {
            throw new InterviewCalendarUnprocessableEntityRuntimeException("Slot has conflicts with saved slots");
        }
        Slot slotToSave = this.modelMapper.map(slot, Slot.class);
        slotToSave.setUser(user);
        return this.modelMapper.map(this.slotRepositoryService.save(slotToSave), SlotDTO.class);
    }

    @Override
    public void deleteSlot(Long slotId) {
        this.slotRepositoryService.deleteSlot(slotId);
    }

    private boolean hasSlotConflict(SlotDTO slot) {
        List<Slot> slots = this.userRepositoryService.get(slot.getUserId()).getSlots();
        return slots.stream().anyMatch(s -> {
            Interval period1 = dateStartAnDateEnd2IntervalMapper.apply(slot.getStartDateTime(), slot.getEndDateTime());
            Interval period2 = dateStartAnDateEnd2IntervalMapper.apply(s.getStartDateTime(), s.getEndDateTime());
            return period1.overlap(period2) != null;
        });
    }
}
