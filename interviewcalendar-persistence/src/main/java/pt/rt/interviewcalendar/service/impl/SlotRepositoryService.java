package pt.rt.interviewcalendar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pt.rt.interviewcalendar.foundation.exception.InterviewCalendarResourceNotFoundRuntimeException;
import pt.rt.interviewcalendar.foundation.exception.InterviewCalendarUnprocessableEntityRuntimeException;
import pt.rt.interviewcalendar.model.domain.Slot;
import pt.rt.interviewcalendar.repository.ISlotRepository;
import pt.rt.interviewcalendar.service.ISlotRepositoryService;

@Service
public class SlotRepositoryService implements ISlotRepositoryService {

    private final ISlotRepository slotRepository;

    @Autowired
    public SlotRepositoryService(ISlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Override
    public Slot save(Slot slot) {
        try {
            return this.slotRepository.save(slot);
        } catch (DataIntegrityViolationException e) {
            throw new InterviewCalendarUnprocessableEntityRuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteSlot(Long slotId) {
        try {
            this.slotRepository.deleteById(slotId);
        } catch (EmptyResultDataAccessException e) {
            throw new InterviewCalendarResourceNotFoundRuntimeException(InterviewCalendarResourceNotFoundRuntimeException.SLOT_NOT_FOUND);
        }
    }
}
