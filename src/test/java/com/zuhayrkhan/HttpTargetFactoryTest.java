package com.zuhayrkhan;

import com.zuhayrkhan.context.ContextHolder;
import com.zuhayrkhan.context.ConverterStrategy;
import com.zuhayrkhan.converter.SimpleExpressionConverter;
import com.zuhayrkhan.converter.strategy.map.converter.MapConverterStrategy;
import com.zuhayrkhan.model.HttpTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpTargetFactoryTest {

    private final Clock clock = Clock.fixed(Instant.ofEpochMilli(0), ZoneId.of("UTC"));
    private HttpTargetFactory httpTargetFactory;

    @BeforeEach
    void setUp() {
//        ConverterStrategy<? extends ContextHolder<?>> converterStrategy = new JexlConverterStrategy(clock);
        ConverterStrategy<? extends ContextHolder<?>> converterStrategy = new MapConverterStrategy(clock);
        httpTargetFactory = new HttpTargetFactory(
                new SimpleExpressionConverter<>(converterStrategy));
    }

    static class HttpTargetTestParams {
        private final String httpTargetURIAsString;
        private final String body;
        private final String responseMatchForSuccess;
        private final String expectedURI;
        private final String expectedBody;
        private final String expectedResponseMatchForSuccess;

        HttpTargetTestParams(String httpTargetURIAsString,
                             String body,
                             String responseMatchForSuccess,
                             String expectedURI,
                             String expectedBody,
                             String expectedResponseMatchForSuccess) {
            this.httpTargetURIAsString = httpTargetURIAsString;
            this.body = body;
            this.responseMatchForSuccess = responseMatchForSuccess;
            this.expectedURI = expectedURI;
            this.expectedBody = expectedBody;
            this.expectedResponseMatchForSuccess = expectedResponseMatchForSuccess;
        }

        @Override
        public String toString() {
            return "HttpTargetTestParams{" +
                    "httpTargetURIAsString='" + httpTargetURIAsString + '\'' +
                    ", body='" + body + '\'' +
                    ", responseMatchForSuccess='" + responseMatchForSuccess + '\'' +
                    ", expectedURI='" + expectedURI + '\'' +
                    ", expectedBody='" + expectedBody + '\'' +
                    ", expectedResponseMatchForSuccess='" + expectedResponseMatchForSuccess + '\'' +
                    '}';
        }

    }

    public static Stream<HttpTargetTestParams> createURIStringsAndExpected() {
        return Stream.of(
                new HttpTargetTestParams("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&anotherParam=anotherValue" +
                        "&untilDate=${today}"
                        , "${tomorrow}"
                        , "${today}"
                        , "http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&anotherParam=anotherValue" +
                        "&untilDate=1970-01-01T00:00:00Z"
                        , "1970-01-02T00:00:00Z"
                        , "1970-01-01T00:00:00Z"),
                new HttpTargetTestParams("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&untilDate=${today}" +
                        "&constant=aConstant"
                        , "${tomorrow}"
                        , "${today}"
                        , "http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z" +
                        "&constant=aConstant"
                        , "1970-01-02T00:00:00Z"
                        , "1970-01-01T00:00:00Z"),
                new HttpTargetTestParams("http://localhost:8080/reports/" +
                        "?constant=aConstant"
                        , "blah"
                        , null
                        , "http://localhost:8080/reports/" +
                        "?constant=aConstant"
                        , "blah"
                        , null)
        );
    }

    @ParameterizedTest
    @MethodSource("createURIStringsAndExpected")
    void can_create_URI_from_URI_with_date_vars_and_have_them_converted(HttpTargetTestParams httpTargetTestParams) {

        HttpTarget httpTarget = httpTargetFactory.createHttpTarget(
                httpTargetTestParams.httpTargetURIAsString,
                httpTargetTestParams.body,
                httpTargetTestParams.responseMatchForSuccess);

        assertThat(httpTarget)
                .isNotNull()
                .isEqualTo(new HttpTarget(
                        URI.create(httpTargetTestParams.expectedURI),
                        httpTargetTestParams.expectedBody,
                        httpTargetTestParams.expectedResponseMatchForSuccess)
                );

    }

}