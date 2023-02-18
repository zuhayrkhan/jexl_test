package com.zuhayrkhan.converter;

import com.zuhayrkhan.converter.context.ContextBuilder;
import com.zuhayrkhan.converter.context.ContextHolder;
import com.zuhayrkhan.converter.strategy.ConverterStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;

public class SimpleExpressionConverter<CONTEXT, CONTEXT_HOLDER extends ContextHolder<CONTEXT>,
        CONTEXT_BUILDER extends ContextBuilder<CONTEXT, CONTEXT_HOLDER>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleExpressionConverter.class);

    private final Clock clock;

    private final ConverterStrategy<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> converterStrategy;

    public SimpleExpressionConverter(Clock clock, ConverterStrategy<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> converterStrategy) {
        this.clock = clock;
        this.converterStrategy = converterStrategy;
    }

    public String convert(String httpTargetURIAsString) {

        LOGGER.info("httpTargetURIAsString={}", httpTargetURIAsString);

        if (httpTargetURIAsString == null) {
            return null;
        }

//        converterStrategy.newCreateContextHolder(clock, converterStrategy.getContextBuilderFactory());

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
