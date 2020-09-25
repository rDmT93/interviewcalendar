package pt.rt.interviewcalendar.management.function;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.rt.interviewcalendar.management.mapper.DateStartAnDateEnd2IntervalMapper;
import pt.rt.interviewcalendar.management.mapper.Interval2InterviewSlotDTOMapper;
import pt.rt.interviewcalendar.model.dto.InterviewSlotDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("splitIntervalHourPeriods")
public class SplitIntervalHourPeriods implements Function<InterviewSlotDTO, List<InterviewSlotDTO>> {

    private final Interval2InterviewSlotDTOMapper interval2InterviewSlotDTOMapper;
    private final DateStartAnDateEnd2IntervalMapper dateStartAnDateEnd2IntervalMapper;

    @Autowired
    public SplitIntervalHourPeriods(
            Interval2InterviewSlotDTOMapper interval2InterviewSlotDTOMapper,
            DateStartAnDateEnd2IntervalMapper dateStartAnDateEnd2IntervalMapper) {
        this.interval2InterviewSlotDTOMapper = interval2InterviewSlotDTOMapper;
        this.dateStartAnDateEnd2IntervalMapper = dateStartAnDateEnd2IntervalMapper;
    }

    @Override
    public List<InterviewSlotDTO> apply(InterviewSlotDTO interviewSlot) {

        Interval interval = dateStartAnDateEnd2IntervalMapper.apply(interviewSlot.getStartDateTime(), interviewSlot.getEndDateTime());

        long startMillis = interval.getStartMillis();
        long endMillis = interval.getEndMillis();
        long durationMillis = endMillis - startMillis;
        long chunks = interval.getEnd().getHourOfDay() - interval.getStart().getHourOfDay();
        long chunkSize = durationMillis / chunks;

        List<Interval> list = new ArrayList<>();
        for (int i = 1; i <= chunks; ++i) {
            list.add(new Interval(startMillis, startMillis += chunkSize));
        }

        return list.stream().map(i -> interval2InterviewSlotDTOMapper.apply(i)).collect(Collectors.toList());
    }

}
