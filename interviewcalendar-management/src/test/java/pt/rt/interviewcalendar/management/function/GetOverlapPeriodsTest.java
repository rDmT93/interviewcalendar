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
import org.modelmapper.ModelMapper;
import pt.rt.interviewcalendar.management.mapper.DateStartAnDateEnd2IntervalMapper;
import pt.rt.interviewcalendar.management.mapper.Interval2InterviewSlotDTOMapper;
import pt.rt.interviewcalendar.model.domain.Slot;
import pt.rt.interviewcalendar.model.domain.User;
import pt.rt.interviewcalendar.model.dto.InterviewSlotDTO;
import pt.rt.interviewcalendar.model.dto.RoleEnum;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class GetOverlapPeriodsTest {

    @InjectMocks
    GetOverlapPeriods function;

    @Mock
    Interval2InterviewSlotDTOMapper interval2InterviewSlotDTOMapper;

    @Mock
    DateStartAnDateEnd2IntervalMapper dateStartAnDateEnd2IntervalMapper;

    @Mock
    ModelMapper modelMapper;

    @Test
    public void applyTest() {

        User c = User.builder().name("U1").role(RoleEnum.CANDIDATE.toString()).build();
        User i = User.builder().name("U2").role(RoleEnum.INTERVIEWER.toString()).build();

        Date start1 = new DateTime("2020-09-28T09:00:00.00Z").toDate();
        Date end1 = new DateTime("2020-09-28T12:00:00.00Z").toDate();
        Interval interval1 = new Interval(new DateTime(start1).toInstant(), new DateTime(end1).toInstant());

        Date start2 = new DateTime("2020-09-28T10:00:00.00Z").toDate();
        Date end2 = new DateTime("2020-09-28T14:00:00.00Z").toDate();
        Interval interval2 = new Interval(new DateTime(start2).toInstant(), new DateTime(end2).toInstant());

        Date startOverlap = new DateTime("2020-09-28T10:00:00.00Z").toDate();
        Date endOverlap = new DateTime("2020-09-28T12:00:00.00Z").toDate();
        Interval intervalOverlap = new Interval(new DateTime(startOverlap).toInstant(), new DateTime(endOverlap).toInstant());

        Slot cs = Slot.builder().startDateTime(start1).endDateTime(end1).build();
        Slot is = Slot.builder().startDateTime(start2).endDateTime(end2).build();

        InterviewSlotDTO isDTO = InterviewSlotDTO.builder().startDateTime(start1).endDateTime(end1).build();
        InterviewSlotDTO slotResult = InterviewSlotDTO.builder().startDateTime(startOverlap).endDateTime(endOverlap).build();

        c.setSlots(Arrays.asList(cs));
        i.setSlots(Arrays.asList(is));

        List<User> interviewers = Arrays.asList(i);

        Mockito.when(modelMapper.map(cs, InterviewSlotDTO.class)).thenReturn(isDTO);

        Mockito.when(dateStartAnDateEnd2IntervalMapper.apply(start1, end1)).thenReturn(interval1);
        Mockito.when(dateStartAnDateEnd2IntervalMapper.apply(start2, end2)).thenReturn(interval2);

        Mockito.when(interval2InterviewSlotDTOMapper.apply(intervalOverlap)).thenReturn(slotResult);

        List<InterviewSlotDTO> result = function.apply(c, interviewers);

        Mockito.verify(modelMapper).map(cs, InterviewSlotDTO.class);

        Mockito.verify(dateStartAnDateEnd2IntervalMapper).apply(start1, end1);
        Mockito.verify(dateStartAnDateEnd2IntervalMapper).apply(start2, end2);

        Mockito.verify(interval2InterviewSlotDTOMapper).apply(intervalOverlap);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(startOverlap, result.get(0).getStartDateTime());
        Assert.assertEquals(endOverlap, result.get(0).getEndDateTime());
    }
}