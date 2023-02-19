package com.zuhayrkhan;

import com.zuhayrkhan.converter.SimpleExpressionConverter;
import com.zuhayrkhan.converter.context.ContextBuilder;
import com.zuhayrkhan.converter.context.ContextHolder;
import com.zuhayrkhan.converter.strategy.jexl.converter.JexlConverterStrategy;
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

    public static Stream<HttpTargetTestParams<SimpleMapContext, MapContextHolder, ContextBuilder>>
    createURIStringsAndExpectedMapStrategy() {

        HttpTargetFactory<SimpleMapContext, MapContextHolder, ContextBuilder> httpTargetFactory =
                new HttpTargetFactory<>(
                        new SimpleExpressionConverter<>(CLOCK, new MapConverterStrategy()));

        return Stream.of(
                httpTargetTestParamsWithSomeConstAmongVars(
                        SimpleMapContext.class, MapContextHolder.class, ContextBuilder.class)
                        .withHttpTargetFactory(httpTargetFactory).build(),
                httpTargetTestParamsWithSomeVars(
                        SimpleMapContext.class, MapContextHolder.class, ContextBuilder.class)
                        .withHttpTargetFactory(httpTargetFactory).build(),
                httpTargetTestParamsWithNoVars(
                        SimpleMapContext.class, MapContextHolder.class, ContextBuilder.class)
                        .withHttpTargetFactory(httpTargetFactory).build()
        );
    }

    public static Stream<HttpTargetTestParams<JexlContext, ContextHolder, ContextBuilder>>
    createURIStringsAndExpectedJexlStrategy() {

        HttpTargetFactory<JexlContext, ContextHolder, ContextBuilder> httpTargetFactory =
                new HttpTargetFactory<>(new SimpleExpressionConverter<>(CLOCK, new JexlConverterStrategy()));

        return Stream.of(
                httpTargetTestParamsWithSomeConstAmongVars(
                        JexlContext.class, ContextHolder.class, ContextBuilder.class)
                        .withHttpTargetFactory(httpTargetFactory).build(),
                httpTargetTestParamsWithSomeVars(
                        JexlContext.class, ContextHolder.class, ContextBuilder.class)
                        .withHttpTargetFactory(httpTargetFactory).build(),
                httpTargetTestParamsWithNoVars(
                        JexlContext.class, ContextHolder.class, ContextBuilder.class)
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