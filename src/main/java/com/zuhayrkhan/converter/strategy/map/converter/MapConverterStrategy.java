package com.zuhayrkhan.converter.strategy.map.converter;

import com.zuhayrkhan.converter.strategy.ConverterStrategy;
import com.zuhayrkhan.converter.strategy.map.context.MapContextBuilder;
import com.zuhayrkhan.converter.strategy.map.context.MapContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

public class MapConverterStrategy implements ConverterStrategy<Map<String, Object>,
        MapContextHolder, MapContextBuilder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapConverterStrategy.class);

    @Override
    public Supplier<MapContextBuilder> getContextBuilderFactory() {
        return MapContextBuilder::new;
    }

    @Override
    public String doConvert(MapContextHolder contextHolder, String input) {

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
