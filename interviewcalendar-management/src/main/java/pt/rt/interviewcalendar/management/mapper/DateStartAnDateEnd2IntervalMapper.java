package pt.rt.interviewcalendar.management.mapper;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.BiFunction;

@Component("dateStartAnDateEnd2IntervalMapper")
public class DateStartAnDateEnd2IntervalMapper implements BiFunction<Date, Date, Interval> {

    @Override
    public Interval apply(Date start, Date end) {
        return new Interval(new DateTime(start).toInstant(), new DateTime(end).toInstant());
    }
}