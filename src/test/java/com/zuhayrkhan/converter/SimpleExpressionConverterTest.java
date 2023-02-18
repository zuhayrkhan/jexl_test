package com.zuhayrkhan.converter;

import com.zuhayrkhan.converter.strategy.jexl.converter.JexlConverterStrategy;
import com.zuhayrkhan.converter.strategy.map.converter.MapConverterStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleExpressionConverterTest {

    private static final Clock CLOCK = Clock.fixed(Instant.ofEpochMilli(0), ZoneId.of("UTC"));

    static class SimpleExpressionConverterParams {

        static Builder simpleExpressionConverterParamsWithAllVars() {
            return new Builder()
                    .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                            "?fromDate=${yesterday}" +
                            "&untilDate=${today}")
                    .withExpectedURI("http://localhost:8080/reports/" +
                            "?fromDate=1969-12-31T00:00:00Z" +
                            "&untilDate=1970-01-01T00:00:00Z");
        }

        static Builder simpleExpressionConverterParamsWithSomeVars() {
            return new Builder()
                    .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                            "?fromDate=${yesterday}" +
                            "&untilDate=${today}" +
                            "&constant=aConstant")
                    .withExpectedURI("http://localhost:8080/reports/" +
                            "?fromDate=1969-12-31T00:00:00Z" +
                            "&untilDate=1970-01-01T00:00:00Z" +
                            "&constant=aConstant");
        }

        static Builder simpleExpressionConverterParamsWithNoVars() {
            return new Builder()
                    .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                            "?constant=aConstant")
                    .withExpectedURI("http://localhost:8080/reports/" +
                            "?constant=aConstant");
        }

        static class Builder {

            private SimpleExpressionConverter<?> simpleExpressionConverter;
            private String httpTargetURIAsString;
            private String expectedURI;

            public Builder withSimpleExpressionConverter(SimpleExpressionConverter<?> simpleExpressionConverter) {
                this.simpleExpressionConverter = simpleExpressionConverter;
                return this;
            }

            public Builder withHttpTargetURIAsString(String httpTargetURIAsString) {
                this.httpTargetURIAsString = httpTargetURIAsString;
                return this;
            }

            public Builder withExpectedURI(String expectedURI) {
                this.expectedURI = expectedURI;
                return this;
            }

            SimpleExpressionConverterParams build() {
                return new SimpleExpressionConverterParams(
                        simpleExpressionConverter,
                        httpTargetURIAsString,
                        expectedURI
                );
            }

        }

        private final SimpleExpressionConverter<?> simpleExpressionConverter;
        private final String httpTargetURIAsString;
        private final String expectedURI;

        SimpleExpressionConverterParams(SimpleExpressionConverter<?> simpleExpressionConverter,
                                        String httpTargetURIAsString,
                                        String expectedURI) {
            this.simpleExpressionConverter = simpleExpressionConverter;
            this.httpTargetURIAsString = httpTargetURIAsString;
            this.expectedURI = expectedURI;
        }

        @Override
        public String toString() {
            return "SECP{" +
                    "simpleExpressionConverter=" + simpleExpressionConverter +
                    ", httpTargetURIAsString='" + httpTargetURIAsString + '\'' +
                    ", expectedURI='" + expectedURI + '\'' +
                    '}';
        }

    }

    public static Stream<SimpleExpressionConverterParams> createURIStringsAndExpectedWithJexl() {
        return Stream.of(
                SimpleExpressionConverterParams.simpleExpressionConverterParamsWithAllVars()
                        .withSimpleExpressionConverter(new SimpleExpressionConverter<>(new JexlConverterStrategy(CLOCK)))
                        .build(),
                SimpleExpressionConverterParams.simpleExpressionConverterParamsWithSomeVars()
                        .withSimpleExpressionConverter(new SimpleExpressionConverter<>(new JexlConverterStrategy(CLOCK)))
                        .build(),
                SimpleExpressionConverterParams.simpleExpressionConverterParamsWithNoVars()
                        .withSimpleExpressionConverter(new SimpleExpressionConverter<>(new JexlConverterStrategy(CLOCK)))
                        .build()
        );
    }

    public static Stream<SimpleExpressionConverterParams> createURIStringsAndExpectedWithMap() {
        return Stream.of(
                SimpleExpressionConverterParams.simpleExpressionConverterParamsWithAllVars()
                        .withSimpleExpressionConverter(new SimpleExpressionConverter<>(new MapConverterStrategy(CLOCK)))
                        .build(),
                SimpleExpressionConverterParams.simpleExpressionConverterParamsWithSomeVars()
                        .withSimpleExpressionConverter(new SimpleExpressionConverter<>(new MapConverterStrategy(CLOCK)))
                        .build(),
                SimpleExpressionConverterParams.simpleExpressionConverterParamsWithNoVars()
                        .withSimpleExpressionConverter(new SimpleExpressionConverter<>(new MapConverterStrategy(CLOCK)))
                        .build()
        );
    }

    @BeforeEach
    void setUp() {
        //        simpleExpressionConverter = new SimpleExpressionConverter<>(new JexlConverterStrategy(clock));
    }

    @ParameterizedTest
    @MethodSource({"createURIStringsAndExpectedWithJexl", "createURIStringsAndExpectedWithMap"})
    void can_specify_target_with_date_vars_and_have_them_converted(
            SimpleExpressionConverterParams simpleExpressionConverterParams) {

        String converted = simpleExpressionConverterParams.simpleExpressionConverter.convert(
                simpleExpressionConverterParams.httpTargetURIAsString);

        assertThat(converted).isNotBlank().isEqualTo(simpleExpressionConverterParams.expectedURI);

    }

}