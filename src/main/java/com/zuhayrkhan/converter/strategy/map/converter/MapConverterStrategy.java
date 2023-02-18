package com.zuhayrkhan.converter.strategy.map.converter;

import com.zuhayrkhan.converter.context.CommonDatesPopulator;
import com.zuhayrkhan.converter.context.ContextHolder;
import com.zuhayrkhan.converter.strategy.ConverterStrategy;
import com.zuhayrkhan.converter.strategy.map.context.MapContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.util.Arrays;
import java.util.Map;

public class MapConverterStrategy implements ConverterStrategy<ContextHolder<Map<String, Object>>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapConverterStrategy.class);

    private final Clock clock;

    public MapConverterStrategy(Clock clock) {
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
    public String doConvert(ContextHolder<Map<String, Object>> contextHolder, String input) {

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
