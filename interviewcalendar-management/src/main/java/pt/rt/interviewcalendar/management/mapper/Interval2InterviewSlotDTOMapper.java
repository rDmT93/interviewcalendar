package pt.rt.interviewcalendar.management.mapper;

import org.joda.time.Interval;
import org.springframework.stereotype.Component;
import pt.rt.interviewcalendar.model.dto.InterviewSlotDTO;

import java.util.function.Function;

@Component("interval2InterviewSlotDTOMapper")
public class Interval2InterviewSlotDTOMapper implements Function<Interval, InterviewSlotDTO> {

    @Override
    public InterviewSlotDTO apply(Interval interval) {
        return InterviewSlotDTO.builder()
                .startDateTime(interval.getStart().toDate())
                .endDateTime(interval.getEnd().toDate())
                .build();
    }
}
