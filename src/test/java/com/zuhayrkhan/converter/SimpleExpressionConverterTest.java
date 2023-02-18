package com.zuhayrkhan.converter;

import com.zuhayrkhan.converter.strategy.jexl.converter.JexlConverterStrategy;
import com.zuhayrkhan.converter.strategy.map.converter.MapConverterStrategy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleExpressionConverterTest {

    private static final Clock CLOCK = Clock.fixed(Instant.ofEpochMilli(0), ZoneId.of("UTC"));

    public static Stream<SimpleExpressionConverterParams> createURIStringsAndExpectedWithJexl() {
        return Stream.of(
                SimpleExpressionConverterParams.simpleExpressionConverterParamsWithAllVars()
                        .withSimpleExpressionConverter(new SimpleExpressionConverter<>(CLOCK, new JexlConverterStrategy(CLOCK)))
                        .build(),
                SimpleExpressionConverterParams.simpleExpressionConverterParamsWithSomeVars()
                        .withSimpleExpressionConverter(new SimpleExpressionConverter<>(CLOCK, new JexlConverterStrategy(CLOCK)))
                        .build(),
                SimpleExpressionConverterParams.simpleExpressionConverterParamsWithNoVars()
                        .withSimpleExpressionConverter(new SimpleExpressionConverter<>(CLOCK, new JexlConverterStrategy(CLOCK)))
                        .build()
        );
    }

    public static Stream<SimpleExpressionConverterParams> createURIStringsAndExpectedWithMap() {
        return Stream.of(
                SimpleExpressionConverterParams.simpleExpressionConverterParamsWithAllVars()
                        .withSimpleExpressionConverter(new SimpleExpressionConverter<>(CLOCK, new MapConverterStrategy(CLOCK)))
                        .build(),
                SimpleExpressionConverterParams.simpleExpressionConverterParamsWithSomeVars()
                        .withSimpleExpressionConverter(new SimpleExpressionConverter<>(CLOCK, new MapConverterStrategy(CLOCK)))
                        .build(),
                SimpleExpressionConverterParams.simpleExpressionConverterParamsWithNoVars()
                        .withSimpleExpressionConverter(new SimpleExpressionConverter<>(CLOCK, new MapConverterStrategy(CLOCK)))
                        .build()
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