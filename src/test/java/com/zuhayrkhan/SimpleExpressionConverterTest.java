package com.zuhayrkhan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleExpressionConverterTest {

    private final Clock clock = Clock.fixed(Instant.ofEpochMilli(0), ZoneId.of("UTC"));
    private SimpleExpressionConverter simpleExpressionConverter;

    static class SimpleExpressionConverterParams {
        private final String httpTargetURIAsString;
        private final String expectedURI;

        SimpleExpressionConverterParams(String httpTargetURIAsString,
                                        String expectedURI) {
            this.httpTargetURIAsString = httpTargetURIAsString;
            this.expectedURI = expectedURI;
        }

    }

    public static Stream<SimpleExpressionConverterParams> createURIStringsAndExpected() {
        return Stream.of(
                new SimpleExpressionConverterParams("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&untilDate=${today}"
                        , "http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z"
                ),
                new SimpleExpressionConverterParams("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&untilDate=${today}" +
                        "&constant=aConstant"
                        , "http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z" +
                        "&constant=aConstant"
                ),
                new SimpleExpressionConverterParams("http://localhost:8080/reports/" +
                        "?constant=aConstant"
                        , "http://localhost:8080/reports/" +
                        "?constant=aConstant"
                )
        );
    }

    @BeforeEach
    void setUp() {
        simpleExpressionConverter = new SimpleExpressionConverter(clock);
    }


    @ParameterizedTest
    @MethodSource("createURIStringsAndExpected")
    void can_specify_target_with_date_vars_and_have_them_converted(
            SimpleExpressionConverterParams simpleExpressionConverterParams) {

        String converted = simpleExpressionConverter.convert(
                simpleExpressionConverterParams.httpTargetURIAsString);

        assertThat(converted).isNotBlank().isEqualTo(
                simpleExpressionConverterParams.expectedURI);

    }

}