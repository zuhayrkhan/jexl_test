package com.zuhayrkhan.converter;

import com.zuhayrkhan.converter.strategy.jexl.context.JexlContextBuilder;
import com.zuhayrkhan.converter.strategy.jexl.context.JexlContextHolder;
import com.zuhayrkhan.converter.strategy.jexl.converter.JexlConverterStrategy;
import com.zuhayrkhan.converter.strategy.map.context.MapContextBuilder;
import com.zuhayrkhan.converter.strategy.map.context.MapContextHolder;
import com.zuhayrkhan.converter.strategy.map.context.SimpleMapContext;
import com.zuhayrkhan.converter.strategy.map.converter.MapConverterStrategy;
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

    public static Stream<SimpleExpressionConverterParams<JexlContext, JexlContextHolder, JexlContextBuilder>>
    createURIStringsAndExpectedWithJexl() {
        SimpleExpressionConverter<JexlContext, JexlContextBuilder> simpleExpressionConverter =
                new SimpleExpressionConverter<>(CLOCK, new JexlConverterStrategy());
        return Stream.of(
                simpleExpressionConverterParamsWithAllVars(JexlContext.class, JexlContextHolder.class, JexlContextBuilder.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build(),
                simpleExpressionConverterParamsWithSomeVars(JexlContext.class, JexlContextHolder.class, JexlContextBuilder.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build(),
                simpleExpressionConverterParamsWithNoVars(JexlContext.class, JexlContextHolder.class, JexlContextBuilder.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build()
        );
    }

    public static Stream<SimpleExpressionConverterParams> createURIStringsAndExpectedWithMap() {
        SimpleExpressionConverter<SimpleMapContext, MapContextBuilder> simpleExpressionConverter =
                new SimpleExpressionConverter<>(CLOCK, new MapConverterStrategy());
        return Stream.of(
                simpleExpressionConverterParamsWithAllVars(SimpleMapContext.class, MapContextHolder.class, MapContextBuilder.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build(),
                simpleExpressionConverterParamsWithSomeVars(SimpleMapContext.class, MapContextHolder.class, MapContextBuilder.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build(),
                simpleExpressionConverterParamsWithNoVars(SimpleMapContext.class, MapContextHolder.class, MapContextBuilder.class)
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