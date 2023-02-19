package com.zuhayrkhan.converter.strategy;

import com.zuhayrkhan.converter.context.CommonDatesPopulator;
import com.zuhayrkhan.converter.context.ContextBuilder;
import com.zuhayrkhan.converter.context.ContextHolder;

import java.time.Clock;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ConverterStrategy<CONTEXT, CONTEXT_HOLDER extends
        ContextHolder<CONTEXT>, CONTEXT_BUILDER extends ContextBuilder<CONTEXT, CONTEXT_HOLDER>> {

    String CLOCK_IN_CONTEXT = "clock";

    Supplier<CONTEXT> getContextFactory();

    Function<CONTEXT, CONTEXT_BUILDER> getContextBuilderFactory();

    default String convert(final Clock clock, final String input) {
        CONTEXT context = getContextFactory().get();
        CONTEXT_HOLDER contextHolder = getContextBuilderFactory().apply(context)
                .populateFrom(thisContext -> thisContext.addIntoContext(CLOCK_IN_CONTEXT, clock))
                .populateFrom(CommonDatesPopulator::addCommonDatesToContext)
                .build();
        return doConvert(contextHolder, context, input);
    }

    String doConvert(CONTEXT_HOLDER contextHolder, CONTEXT context, String input);

    default String getName() {
        return getClass().getSimpleName();
    }
}
