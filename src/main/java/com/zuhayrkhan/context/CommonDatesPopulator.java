package com.zuhayrkhan.context;

import com.zuhayrkhan.converter.strategy.ConverterStrategy;

import java.time.Clock;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CommonDatesPopulator {

    public static void addCommonDatesToContext(ContextHolder<?> contextHolder) {

        Clock clock = (Clock) contextHolder.getFromContext(ConverterStrategy.CLOCK_IN_CONTEXT);

        ZonedDateTime today = ZonedDateTime.now(clock).truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime yesterday = today.minus(Period.ofDays(1));
        ZonedDateTime tomorrow = today.plus(Period.ofDays(1));

        contextHolder.addIntoContext("today", today.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        contextHolder.addIntoContext("yesterday", yesterday.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        contextHolder.addIntoContext("tomorrow", tomorrow.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

    }
}
