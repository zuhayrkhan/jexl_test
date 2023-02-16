package com.zuhayrkhan;

import com.zuhayrkhan.context.CommonDatesPopulator;
import com.zuhayrkhan.context.JexlContextBuilder;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;

public class SimpleExpressionConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleExpressionConverter.class);
    public static final String CLOCK_IN_CONTEXT = "clock";

    private final Clock clock;

    private final JexlEngine jexlEngine = new JexlBuilder().cache(512).strict(true).silent(false).create();

    public SimpleExpressionConverter(Clock clock) {
        this.clock = clock;
    }

    private static String createJexlExpression(String expressionToBeConverted) {

        if (expressionToBeConverted == null) {
            return null;
        }

        String[] splitEntries = expressionToBeConverted.split("\\$\\{|}");

        LOGGER.info("splitEntries={}", splitEntries);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0, splitEntriesLength = splitEntries.length; i < splitEntriesLength; i++) {

            boolean isEvenIndex = i % 2 == 0;

            String splitEntry = splitEntries[i];

            if (isEvenIndex) {
                if (i != 0) {
                    stringBuilder.append(" + ");
                }
                stringBuilder.append("'");
                stringBuilder.append(splitEntry);
                stringBuilder.append("'");
                if (i + 1 != splitEntries.length) {
                    stringBuilder.append(" + ");
                }
            } else {
                stringBuilder.append(splitEntry);
            }
        }

        return stringBuilder.toString();

    }

    public String convert(String httpTargetURIAsString) {

        LOGGER.info("httpTargetURIAsString={}", httpTargetURIAsString);

        if (httpTargetURIAsString == null) {
            return null;
        }

        String httpTargetURIAsJexlExpression = createJexlExpression(httpTargetURIAsString);

        LOGGER.info("httpTargetURIAsJexlExpression={}", httpTargetURIAsJexlExpression);

        JexlExpression jexlExpression = jexlEngine.createExpression(httpTargetURIAsJexlExpression);

        JexlContext jexlContext = new JexlContextBuilder()
                .populateFrom(context -> context.set(CLOCK_IN_CONTEXT, clock))
                .populateFrom(CommonDatesPopulator::addCommonDatesToContext)
                .build();

        String result = (String) jexlExpression.evaluate(jexlContext);

        LOGGER.info("result={}", result);

        return result;
    }

}
