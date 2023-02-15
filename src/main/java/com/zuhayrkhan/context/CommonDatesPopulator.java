package com.zuhayrkhan.context;

import org.apache.commons.jexl3.JexlContext;

import java.time.Clock;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CommonDatesPopulator {

    public static void addCommonDatesToContext(JexlContext context) {

        Clock clock = (Clock) context.get("clock");

        ZonedDateTime today = ZonedDateTime.now(clock).truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime yesterday = today.minus(Period.ofDays(1));
        ZonedDateTime tomorrow = today.plus(Period.ofDays(1));

        context.set("today", today.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        context.set("yesterday", yesterday.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        context.set("tomorrow", tomorrow.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

    }
}
