package com.zuhayrkhan.converter.strategy;

import com.zuhayrkhan.converter.context.CommonDatesPopulator;
import com.zuhayrkhan.converter.context.ContextBuilder;
import com.zuhayrkhan.converter.context.ContextHolder;

import java.time.Clock;
import java.util.function.Supplier;

public interface ConverterStrategy<CONTEXT, CONTEXT_HOLDER extends
        ContextHolder<CONTEXT>, CONTEXT_BUILDER extends ContextBuilder<CONTEXT, CONTEXT_HOLDER>> {

    String CLOCK_IN_CONTEXT = "clock";

    Supplier<CONTEXT_BUILDER> getContextBuilderFactory();

    default CONTEXT_HOLDER newCreateContextHolder(Clock clock) {
        return getContextBuilderFactory().get()
                .populateFrom(context -> context.addIntoContext(CLOCK_IN_CONTEXT, clock))
                .populateFrom(CommonDatesPopulator::addCommonDatesToContext)
                .build();
    }

    String doConvert(CONTEXT_HOLDER contextHolder, String input);

    default String getName() {
        return getClass().getSimpleName();
    }

}
