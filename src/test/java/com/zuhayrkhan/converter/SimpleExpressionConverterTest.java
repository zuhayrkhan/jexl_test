package com.zuhayrkhan.converter;

import com.zuhayrkhan.converter.context.ContextBuilder;
import com.zuhayrkhan.converter.context.ContextHolder;
import com.zuhayrkhan.converter.context.SimpleMapContext;
import com.zuhayrkhan.converter.strategy.JexlConverterStrategy;
import com.zuhayrkhan.converter.strategy.MapConverterStrategy;
import org.apache.commons.jexl3.JexlContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Stream;

import static com.zuhayrkhan.converter.SimpleExpressionConverterParams.*;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleExpressionConverterTest {

    private static final Clock CLOCK = Clock.fixed(Instant.ofEpochMilli(0), ZoneId.of("UTC"));

    public static Stream<SimpleExpressionConverterParams<JexlContext, ContextHolder, ContextBuilder>>
    createURIStringsAndExpectedWithJexl() {
        SimpleExpressionConverter<JexlContext, ContextBuilder> simpleExpressionConverter =
                new SimpleExpressionConverter<>(CLOCK, new JexlConverterStrategy());
        return Stream.of(
                simpleExpressionConverterParamsWithAllVars(JexlContext.class, ContextHolder.class, ContextBuilder.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build(),
                simpleExpressionConverterParamsWithSomeVars(JexlContext.class, ContextHolder.class, ContextBuilder.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build(),
                simpleExpressionConverterParamsWithNoVars(JexlContext.class, ContextHolder.class, ContextBuilder.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build()
        );
    }

    public static Stream<SimpleExpressionConverterParams> createURIStringsAndExpectedWithMap() {
        SimpleExpressionConverter<SimpleMapContext, ContextBuilder> simpleExpressionConverter =
                new SimpleExpressionConverter<>(CLOCK, new MapConverterStrategy());
        return Stream.of(
                simpleExpressionConverterParamsWithAllVars(SimpleMapContext.class, ContextHolder.class, ContextBuilder.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build(),
                simpleExpressionConverterParamsWithSomeVars(SimpleMapContext.class, ContextHolder.class, ContextBuilder.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build(),
                simpleExpressionConverterParamsWithNoVars(SimpleMapContext.class, ContextHolder.class, ContextBuilder.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build()
        );
    }

    @ParameterizedTest
    @MethodSource({"createURIStringsAndExpectedWithJexl", "createURIStringsAndExpectedWithMap"})
    void can_specify_target_with_date_vars_and_have_them_converted(
            SimpleExpressionConverterParams simpleExpressionConverterParams) {

        String converted = simpleExpressionConverterParams.getSimpleExpressionConverter().convert(
                simpleExpressionConverterParams.getHttpTargetURIAsString());

        assertThat(converted).isNotBlank().isEqualTo(simpleExpressionConverterParams.getExpectedURI());

    }

}