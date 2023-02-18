package com.zuhayrkhan;

import com.zuhayrkhan.converter.SimpleExpressionConverter;
import com.zuhayrkhan.converter.strategy.jexl.context.JexlContextBuilder;
import com.zuhayrkhan.converter.strategy.jexl.context.JexlContextHolder;
import com.zuhayrkhan.converter.strategy.jexl.converter.JexlConverterStrategy;
import com.zuhayrkhan.converter.strategy.map.context.MapContextBuilder;
import com.zuhayrkhan.converter.strategy.map.context.MapContextHolder;
import com.zuhayrkhan.converter.strategy.map.context.SimpleMapContext;
import com.zuhayrkhan.converter.strategy.map.converter.MapConverterStrategy;
import com.zuhayrkhan.model.HttpTarget;
import org.apache.commons.jexl3.JexlContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Stream;

import static com.zuhayrkhan.HttpTargetTestParams.*;
import static org.assertj.core.api.Assertions.assertThat;

class HttpTargetFactoryTest {

    private static final Clock CLOCK = Clock.fixed(Instant.ofEpochMilli(0), ZoneId.of("UTC"));

    public static Stream<HttpTargetTestParams<SimpleMapContext, MapContextHolder, MapContextBuilder>>
    createURIStringsAndExpectedMapStrategy() {

        HttpTargetFactory<SimpleMapContext, MapContextHolder, MapContextBuilder> httpTargetFactory =
                new HttpTargetFactory<>(
                        new SimpleExpressionConverter<>(CLOCK, new MapConverterStrategy()));

        return Stream.of(
                httpTargetTestParamsWithSomeConstAmongVars(
                        SimpleMapContext.class, MapContextHolder.class, MapContextBuilder.class)
                        .withHttpTargetFactory(httpTargetFactory).build(),
                httpTargetTestParamsWithSomeVars(
                        SimpleMapContext.class, MapContextHolder.class, MapContextBuilder.class)
                        .withHttpTargetFactory(httpTargetFactory).build(),
                httpTargetTestParamsWithNoVars(
                        SimpleMapContext.class, MapContextHolder.class, MapContextBuilder.class)
                        .withHttpTargetFactory(httpTargetFactory).build()
        );
    }

    public static Stream<HttpTargetTestParams<JexlContext, JexlContextHolder, JexlContextBuilder>>
    createURIStringsAndExpectedJexlStrategy() {

        HttpTargetFactory<JexlContext, JexlContextHolder, JexlContextBuilder> httpTargetFactory =
                new HttpTargetFactory<>(new SimpleExpressionConverter<>(CLOCK, new JexlConverterStrategy()));

        return Stream.of(
                httpTargetTestParamsWithSomeConstAmongVars(
                        JexlContext.class, JexlContextHolder.class, JexlContextBuilder.class)
                        .withHttpTargetFactory(httpTargetFactory).build(),
                httpTargetTestParamsWithSomeVars(
                        JexlContext.class, JexlContextHolder.class, JexlContextBuilder.class)
                        .withHttpTargetFactory(httpTargetFactory).build(),
                httpTargetTestParamsWithNoVars(
                        JexlContext.class, JexlContextHolder.class, JexlContextBuilder.class)
                        .withHttpTargetFactory(httpTargetFactory).build()
        );
    }

    @ParameterizedTest
    @MethodSource({"createURIStringsAndExpectedMapStrategy",
            "createURIStringsAndExpectedJexlStrategy"})
    void can_create_URI_from_URI_with_date_vars_and_have_them_converted(HttpTargetTestParams<?, ?, ?> httpTargetTestParams) {

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