package com.zuhayrkhan.converter.strategy;

public interface ConverterStrategy<CONTEXT_HOLDER> {

    String CLOCK_IN_CONTEXT = "clock";

    CONTEXT_HOLDER createContextHolder();

    String doConvert(CONTEXT_HOLDER contextHolder, String input);

    default String getName() {
        return getClass().getSimpleName();
    }

}
