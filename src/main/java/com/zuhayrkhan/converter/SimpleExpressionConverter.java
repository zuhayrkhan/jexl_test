package com.zuhayrkhan.converter;

import com.zuhayrkhan.converter.strategy.ConverterStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;

public class SimpleExpressionConverter<CONTEXT> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleExpressionConverter.class);

    private final Clock clock;

    private final ConverterStrategy<CONTEXT> converterStrategy;

    public SimpleExpressionConverter(Clock clock, ConverterStrategy<CONTEXT> converterStrategy) {
        this.clock = clock;
        this.converterStrategy = converterStrategy;
    }

    public String convert(String input) {

        LOGGER.info("input={}", input);

        if (input == null) {
            return null;
        }

        String result = converterStrategy.convert(clock, input);

        LOGGER.info("result={}", result);

        return result;
    }

    @Override
    public String toString() {
        return "SimpleExpressionConverter{" +
                "converterStrategy.name=" + converterStrategy.getName() +
                '}';
    }
}
