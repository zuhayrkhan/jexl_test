package com.zuhayrkhan.converter;

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

    public static Stream<SimpleExpressionConverterParams<JexlContext>> createURIStringsAndExpectedWithJexl() {
        SimpleExpressionConverter<JexlContext> simpleExpressionConverter =
                new SimpleExpressionConverter<>(CLOCK, new JexlConverterStrategy());
        return Stream.of(
                simpleExpressionConverterParamsWithAllVars(JexlContext.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build(),
                simpleExpressionConverterParamsWithSomeVars(JexlContext.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build(),
                simpleExpressionConverterParamsWithNoVars(JexlContext.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build()
        );
    }

    public static Stream<SimpleExpressionConverterParams<SimpleMapContext>> createURIStringsAndExpectedWithMap() {
        SimpleExpressionConverter<SimpleMapContext> simpleExpressionConverter =
                new SimpleExpressionConverter<>(CLOCK, new MapConverterStrategy());
        return Stream.of(
                simpleExpressionConverterParamsWithAllVars(SimpleMapContext.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build(),
                simpleExpressionConverterParamsWithSomeVars(SimpleMapContext.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build(),
                simpleExpressionConverterParamsWithNoVars(SimpleMapContext.class)
                        .withSimpleExpressionConverter(simpleExpressionConverter).build()
        );
    }

    @ParameterizedTest
    @MethodSource({"createURIStringsAndExpectedWithJexl", "createURIStringsAndExpectedWithMap"})
    void can_specify_target_with_date_vars_and_have_them_converted(
            SimpleExpressionConverterParams<SimpleMapContext> simpleExpressionConverterParams) {

        String converted = simpleExpressionConverterParams.getSimpleExpressionConverter().convert(
                simpleExpressionConverterParams.getHttpTargetURIAsString());

        assertThat(converted).isNotBlank().isEqualTo(simpleExpressionConverterParams.getExpectedURI());

    }

}