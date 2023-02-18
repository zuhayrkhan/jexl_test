package com.zuhayrkhan;

import com.zuhayrkhan.converter.SimpleExpressionConverter;
import com.zuhayrkhan.converter.strategy.jexl.converter.JexlConverterStrategy;
import com.zuhayrkhan.converter.strategy.map.converter.MapConverterStrategy;
import com.zuhayrkhan.model.HttpTarget;
import org.apache.commons.jexl3.JexlContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;
import java.util.stream.Stream;

import static com.zuhayrkhan.HttpTargetTestParams.*;
import static org.assertj.core.api.Assertions.assertThat;

class HttpTargetFactoryTest {

    private static final Clock CLOCK = Clock.fixed(Instant.ofEpochMilli(0), ZoneId.of("UTC"));

    public static Stream<HttpTargetTestParams> createURIStringsAndExpectedMapStrategy() {

        HttpTargetFactory<Map<String, Object>> httpTargetFactory = new HttpTargetFactory<>(
                new SimpleExpressionConverter<>(new MapConverterStrategy(CLOCK)));

        return Stream.of(
                httpTargetTestParamsWithSomeConstAmongVars()
                        .withHttpTargetFactory(httpTargetFactory).build(),
                httpTargetTestParamsWithSomeVars()
                        .withHttpTargetFactory(httpTargetFactory).build(),
                httpTargetTestParamsWithNoVars()
                        .withHttpTargetFactory(httpTargetFactory).build()
        );
    }

    public static Stream<HttpTargetTestParams> createURIStringsAndExpectedJexlStrategy() {

        HttpTargetFactory<JexlContext> httpTargetFactory = new HttpTargetFactory<>(
                new SimpleExpressionConverter<>(new JexlConverterStrategy(CLOCK)));

        return Stream.of(
                httpTargetTestParamsWithSomeConstAmongVars()
                        .withHttpTargetFactory(httpTargetFactory).build(),
                httpTargetTestParamsWithSomeVars()
                        .withHttpTargetFactory(httpTargetFactory).build(),
                httpTargetTestParamsWithNoVars()
                        .withHttpTargetFactory(httpTargetFactory).build()
        );
    }

    @ParameterizedTest
    @MethodSource({"createURIStringsAndExpectedMapStrategy",
            "createURIStringsAndExpectedJexlStrategy"})
    void can_create_URI_from_URI_with_date_vars_and_have_them_converted(HttpTargetTestParams httpTargetTestParams) {

        HttpTarget httpTarget = httpTargetTestParams.getHttpTargetFactory()
                .createHttpTarget(
                        httpTargetTestParams.getHttpTargetURIAsString(),
                        httpTargetTestParams.getBody(),
                        httpTargetTestParams.getResponseMatchForSuccess());

        assertThat(httpTarget)
                .isNotNull()
                .isEqualTo(new HttpTarget(
                        URI.create(httpTargetTestParams.getExpectedURI()),
                        httpTargetTestParams.getExpectedBody(),
                        httpTargetTestParams.getExpectedResponseMatchForSuccess())
                );

    }

}