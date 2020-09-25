package pt.rt.interviewcalendar.management.function;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pt.rt.interviewcalendar.management.mapper.DateStartAnDateEnd2IntervalMapper;
import pt.rt.interviewcalendar.management.mapper.Interval2InterviewSlotDTOMapper;
import pt.rt.interviewcalendar.model.dto.InterviewSlotDTO;

import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SplitIntervalHourPeriodsTest {

    @InjectMocks
    SplitIntervalHourPeriods function;

    @Mock
    Interval2InterviewSlotDTOMapper interval2InterviewSlotDTOMapper;

    @Mock
    DateStartAnDateEnd2IntervalMapper dateStartAnDateEnd2IntervalMapper;

    @Test
    public void applyTest() {
        Date start = new DateTime("2020-09-28T09:00:00.00Z").toDate();
        Date end = new DateTime("2020-09-28T11:00:00.00Z").toDate();
        Interval interval = new Interval(new DateTime(start).toInstant(), new DateTime(end).toInstant());

        Mockito.when(dateStartAnDateEnd2IntervalMapper.apply(start, end)).thenReturn(interval);

        Date start1 = new DateTime("2020-09-28T09:00:00.00Z").toDate();
        Date end1 = new DateTime("2020-09-28T10:00:00.00Z").toDate();
        Interval interval1 = new Interval(new DateTime(start1).toInstant(), new DateTime(end1).toInstant());

        Date start2 = new DateTime("2020-09-28T10:00:00.00Z").toDate();
        Date end2 = new DateTime("2020-09-28T11:00:00.00Z").toDate();
        Interval interval2 = new Interval(new DateTime(start2).toInstant(), new DateTime(end2).toInstant());

        InterviewSlotDTO is1 = InterviewSlotDTO.builder()
                .startDateTime(new DateTime("2020-09-28T09:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-09-28T10:00:00.00Z").toDate())
                .build();

        InterviewSlotDTO is2 = InterviewSlotDTO.builder()
                .startDateTime(new DateTime("2020-09-28T10:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-09-28T11:00:00.00Z").toDate())
                .build();

        Mockito.when(interval2InterviewSlotDTOMapper.apply(interval1)).thenReturn(is1);
        Mockito.when(interval2InterviewSlotDTOMapper.apply(interval2)).thenReturn(is2);

        InterviewSlotDTO input = InterviewSlotDTO.builder()
                .startDateTime(new DateTime("2020-09-28T09:00:00.00Z").toDate())
                .endDateTime(new DateTime("2020-09-28T11:00:00.00Z").toDate())
                .build();

        List<InterviewSlotDTO> result = function.apply(input);

        Mockito.verify(dateStartAnDateEnd2IntervalMapper).apply(start, end);

        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
    }
}
