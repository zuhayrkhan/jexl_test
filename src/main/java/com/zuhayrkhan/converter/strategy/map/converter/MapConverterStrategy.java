package com.zuhayrkhan.converter.strategy.map.converter;

import com.zuhayrkhan.converter.context.*;
import com.zuhayrkhan.converter.strategy.ConverterStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

public class MapConverterStrategy implements ConverterStrategy<SimpleMapContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapConverterStrategy.class);

    @Override
    public Supplier<SimpleMapContext> getContextFactory() {
        return SimpleMapContextImpl::new;
    }

    @Override
    public Function<SimpleMapContext, ContextHolder> getContextHolderFactory() {
        return simpleMapContext -> new ContextHolderImpl(
                simpleMapContext::put,
                simpleMapContext::get
        );
    }

    @Override
    public Function<ContextHolder, ContextBuilder> getContextBuilderFactory() {
        return ContextBuilderImpl::new;
    }

    @Override
    public String doConvert(ContextHolder contextHolder, SimpleMapContext context, String input) {

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
