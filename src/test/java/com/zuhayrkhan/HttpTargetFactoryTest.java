package com.zuhayrkhan;

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
        httpTargetFactory = new HttpTargetFactory(new SimpleExpressionConverter(clock));
    }

    static class HttpTargetTestParams {
        private final String httpTargetURIAsString;
        private final String body;
        private final String expectedURI;
        private final String expectedBody;

        HttpTargetTestParams(String httpTargetURIAsString,
                             String body,
                             String expectedURI,
                             String expectedBody) {
            this.httpTargetURIAsString = httpTargetURIAsString;
            this.body = body;
            this.expectedURI = expectedURI;
            this.expectedBody = expectedBody;
        }

    }

    public static Stream<HttpTargetTestParams> createURIStringsAndExpected() {
        return Stream.of(
                new HttpTargetTestParams("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&untilDate=${today}"
                        , "${tomorrow}"
                        , "http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z",
                        "1970-01-02T00:00:00Z"),
                new HttpTargetTestParams("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&untilDate=${today}" +
                        "&constant=aConstant"
                        , "${tomorrow}"
                        , "http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z" +
                        "&constant=aConstant",
                        "1970-01-02T00:00:00Z"),
                new HttpTargetTestParams("http://localhost:8080/reports/" +
                        "?constant=aConstant"
                        , "blah"
                        , "http://localhost:8080/reports/" +
                        "?constant=aConstant"
                        , "blah")
        );
    }

    @ParameterizedTest
    @MethodSource("createURIStringsAndExpected")
    void can_create_URI_from_URI_with_date_vars_and_have_them_converted(HttpTargetTestParams httpTargetTestParams) {

        HttpTarget httpTarget = httpTargetFactory.createHttpTarget(
                httpTargetTestParams.httpTargetURIAsString,
                httpTargetTestParams.body);

        assertThat(httpTarget)
                .isNotNull()
                .isEqualTo(new HttpTarget(
                        URI.create(httpTargetTestParams.expectedURI),
                        httpTargetTestParams.expectedBody));

    }

}