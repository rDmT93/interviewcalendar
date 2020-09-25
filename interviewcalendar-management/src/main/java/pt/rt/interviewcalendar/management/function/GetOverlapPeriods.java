package pt.rt.interviewcalendar.management.function;

import org.joda.time.Interval;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.rt.interviewcalendar.management.mapper.DateStartAnDateEnd2IntervalMapper;
import pt.rt.interviewcalendar.management.mapper.Interval2InterviewSlotDTOMapper;
import pt.rt.interviewcalendar.model.domain.Slot;
import pt.rt.interviewcalendar.model.domain.User;
import pt.rt.interviewcalendar.model.dto.InterviewSlotDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Component("getOverlapPeriods")
public class GetOverlapPeriods implements BiFunction<User, List<User>, List<InterviewSlotDTO>> {

    private final Interval2InterviewSlotDTOMapper interval2InterviewSlotDTOMapper;
    private final DateStartAnDateEnd2IntervalMapper dateStartAnDateEnd2IntervalMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public GetOverlapPeriods(
            Interval2InterviewSlotDTOMapper interval2InterviewSlotDTOMapper,
            DateStartAnDateEnd2IntervalMapper dateStartAnDateEnd2IntervalMapper,
            ModelMapper modelMapper) {
        this.interval2InterviewSlotDTOMapper = interval2InterviewSlotDTOMapper;
        this.dateStartAnDateEnd2IntervalMapper = dateStartAnDateEnd2IntervalMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<InterviewSlotDTO> apply(User candidate, List<User> interviewers) {

        List<InterviewSlotDTO> interviewSlots = candidate.getSlots()
                .stream()
                .map(s -> this.modelMapper.map(s, InterviewSlotDTO.class))
                .collect(Collectors.toList());

        interviewers.sort(Comparator.comparingInt(u -> u.getSlots().size()));
        for (User i : interviewers) {
            List<InterviewSlotDTO> currentInterviewSlots = new ArrayList<InterviewSlotDTO>();
            for (Slot s : i.getSlots()) {

                for (InterviewSlotDTO is : interviewSlots) {
                    Interval period1 = dateStartAnDateEnd2IntervalMapper.apply(is.getStartDateTime(), is.getEndDateTime());
                    Interval period2 = dateStartAnDateEnd2IntervalMapper.apply(s.getStartDateTime(), s.getEndDateTime());

                    Interval overlap = period1.overlap(period2);
                    if (overlap != null) {
                        currentInterviewSlots.add(interval2InterviewSlotDTOMapper.apply(overlap));
                    }
                }
            }
            interviewSlots = currentInterviewSlots;
            if (interviewSlots.isEmpty()) {
                break;
            }
        }

        return interviewSlots;
    }
}
