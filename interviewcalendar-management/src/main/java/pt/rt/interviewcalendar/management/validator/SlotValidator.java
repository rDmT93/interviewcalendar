package pt.rt.interviewcalendar.management.validator;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import pt.rt.interviewcalendar.foundation.exception.InterviewCalendarUnprocessableEntityRuntimeException;
import pt.rt.interviewcalendar.model.dto.SlotDTO;

import java.util.function.Consumer;

@Component("slotValidator")
public class SlotValidator implements Consumer<SlotDTO> {

    @Override
    public void accept(SlotDTO slotDTO) {
        validateRequiredFields(slotDTO);
        validateSlotPeriod(slotDTO);
    }

    private void validateRequiredFields(SlotDTO slotDTO) {
        if (slotDTO == null) {
            throw new IllegalArgumentException("Slot cannot be null");
        }

        if (slotDTO.getStartDateTime() == null || slotDTO.getEndDateTime() == null) {
            throw new InterviewCalendarUnprocessableEntityRuntimeException("Slot start time or end time cannot be null");
        }
    }

    private void validateSlotPeriod(SlotDTO slotDTO) {
        DateTime start = new DateTime(slotDTO.getStartDateTime());
        DateTime end = new DateTime(slotDTO.getEndDateTime());

        if (start.getMinuteOfHour() != 0 ||
                start.getSecondOfMinute() != 0 ||
                start.getMillisOfSecond() != 0 ||
                end.getMinuteOfHour() != 0 ||
                end.getSecondOfMinute() != 0 ||
                end.getMillisOfSecond() != 0) {
            throw new InterviewCalendarUnprocessableEntityRuntimeException("Slot start time or end time should be the beginning of an hour");
        }

        if (end.getHourOfDay() - start.getHourOfDay() < 1) {
            throw new InterviewCalendarUnprocessableEntityRuntimeException("Slot period cannot be less than an hour");
        }
    }

}
