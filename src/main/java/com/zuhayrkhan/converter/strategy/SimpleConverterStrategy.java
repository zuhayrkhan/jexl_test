package com.zuhayrkhan.converter.strategy;

import com.zuhayrkhan.context.CommonDatesPopulator;
import com.zuhayrkhan.context.ContextHolder;
import com.zuhayrkhan.context.MapContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.util.Arrays;

public class SimpleConverterStrategy implements ConverterStrategy<ContextHolder<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleConverterStrategy.class);

    private final Clock clock;

    public SimpleConverterStrategy(Clock clock) {
        this.clock = clock;
    }

    @Override
    public ContextHolder<?> createContextHolder() {
        return new MapContextBuilder()
                .populateFrom(contextHolder -> contextHolder.addIntoContext(CLOCK_IN_CONTEXT, clock))
                .populateFrom(CommonDatesPopulator::addCommonDatesToContext)
                .build();
    }

    @Override
    public String doConvert(ContextHolder<?> contextHolder, String input) {

        if (input == null) {
            return null;
        }

        String[] splitEntries = input.split("\\$\\{|}");

        LOGGER.debug("splitEntries={}", Arrays.toString(splitEntries));

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0, splitEntriesLength = splitEntries.length; i < splitEntriesLength; i++) {

            boolean isEvenIndex = i % 2 == 0;

            String splitEntry = splitEntries[i];

            if (isEvenIndex) {
                stringBuilder.append(splitEntry);
            } else {
                stringBuilder.append(contextHolder.getFromContext(splitEntry));
            }
        }

        return stringBuilder.toString();

    }


}
