package com.zuhayrkhan.converter;

import com.zuhayrkhan.converter.context.ContextHolder;
import com.zuhayrkhan.converter.strategy.ConverterStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleExpressionConverter<CONTEXT> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleExpressionConverter.class);

    private final ConverterStrategy<ContextHolder<CONTEXT>> converterStrategy;

    public SimpleExpressionConverter(ConverterStrategy<ContextHolder<CONTEXT>> converterStrategy) {
        this.converterStrategy = converterStrategy;
    }

    public String convert(String httpTargetURIAsString) {

        LOGGER.info("httpTargetURIAsString={}", httpTargetURIAsString);

        if (httpTargetURIAsString == null) {
            return null;
        }

        String result = converterStrategy.doConvert(converterStrategy.createContextHolder(), httpTargetURIAsString);

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
