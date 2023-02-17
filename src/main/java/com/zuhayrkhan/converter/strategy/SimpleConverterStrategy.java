package com.zuhayrkhan.converter.strategy;

import com.zuhayrkhan.context.CommonDatesPopulator;
import com.zuhayrkhan.context.ContextHolder;
import com.zuhayrkhan.context.MapContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.util.Arrays;
import java.util.Map;

public class SimpleConverterStrategy implements ConverterStrategy<ContextHolder<Map<String, Object>>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleConverterStrategy.class);

    private final Clock clock;

    public SimpleConverterStrategy(Clock clock) {
        this.clock = clock;
    }

    @Override
    public ContextHolder<Map<String, Object>> createContextHolder() {
        return new MapContextBuilder()
                .populateFrom(contextHolder -> contextHolder.addIntoContext(CLOCK_IN_CONTEXT, clock))
                .populateFrom(CommonDatesPopulator::addCommonDatesToContext)
                .build();
    }

    @Override
    public String doConvert(ContextHolder<Map<String, Object>> mapContextHolder, String input) {

        LOGGER.error("TODO do conversion of: {}", input);

        return input;
    }

    private static String createJexlExpression(String expressionToBeConverted) {

        if (expressionToBeConverted == null) {
            return null;
        }

        String[] splitEntries = expressionToBeConverted.split("\\$\\{|}");

        LOGGER.debug("splitEntries={}", Arrays.toString(splitEntries));

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


}
