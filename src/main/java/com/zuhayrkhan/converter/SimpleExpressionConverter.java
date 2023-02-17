package com.zuhayrkhan.converter;

import com.zuhayrkhan.context.ContextHolder;
import com.zuhayrkhan.converter.strategy.ConverterStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleExpressionConverter<CONTEXT_HOLDER extends ContextHolder<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleExpressionConverter.class);

    private final ConverterStrategy<CONTEXT_HOLDER> converterStrategy;

    public SimpleExpressionConverter(ConverterStrategy<CONTEXT_HOLDER> converterStrategy) {
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

}
