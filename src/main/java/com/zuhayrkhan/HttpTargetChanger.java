package com.zuhayrkhan;

import org.apache.commons.jexl3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class HttpTargetChanger {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpTargetChanger.class);

    private final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(false).create();

    public static void main(String[] args) {
        new HttpTargetChanger();
    }

    public HttpTargetChanger() {

        // Assuming we have a JexlEngine instance initialized in our class named 'jexl':
        // Create an expression object for our calculation
        String httpTargetURIAsString = "http://localhost:8080/fromDate=${yesterday}&untilDate=${today}";

        LOGGER.info("httpTargetURIAsString={}", httpTargetURIAsString);

        String httpTargetURIAsJexlExpression = "'" + httpTargetURIAsString
                .replace("${", "' + ")
                .replaceAll("}([^$]*)", " + '$1")
                .replaceAll("}$", "");

        LOGGER.info("httpTargetURIAsJexlExpression={}", httpTargetURIAsJexlExpression);

        JexlScript script = jexl.createScript("var yesterday;\n" +
                "var url = 'http://localhost:8080/fromDate=${yesterday}'\n" +
                "return url;");

        JexlExpression jexlExpression = jexl.createExpression(httpTargetURIAsJexlExpression);

        // populate the context
        JexlContext context = new MapContext();

        ZonedDateTime today = ZonedDateTime.now();
        ZonedDateTime yesterday = today.minus(Period.ofDays(1));
        ZonedDateTime tomorrow = today.plus(Period.ofDays(1));

        context.set("today", today.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        context.set("yesterday", yesterday.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        context.set("tomorrow", tomorrow.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

        // ...

        // work it out
        String result = (String) jexlExpression.evaluate(context);
//        String result = (String) script.execute(context);

        LOGGER.info("result={}", result);

    }

}
