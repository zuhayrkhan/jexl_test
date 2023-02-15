package com.zuhayrkhan;

import com.zuhayrkhan.model.HttpTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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
        httpTargetFactory = new HttpTargetFactory(new HttpTargetChanger(clock));
    }

    public static Stream<Arguments> createURIStringsAndExpected() {
        return Stream.of(
                Arguments.of("http://localhost:8080/reports/" +
                                "?fromDate=${yesterday}" +
                                "&untilDate=${today}"
                        , "${tomorrow}"
                        , "http://localhost:8080/reports/" +
                                "?fromDate=1969-12-31T00:00:00Z" +
                                "&untilDate=1970-01-01T00:00:00Z",
                        "1970-01-02T00:00:00Z"
                )
                , Arguments.of("http://localhost:8080/reports/" +
                                "?fromDate=${yesterday}" +
                                "&untilDate=${today}" +
                                "&constant=aConstant"
                        , "${tomorrow}"
                        , "http://localhost:8080/reports/" +
                                "?fromDate=1969-12-31T00:00:00Z" +
                                "&untilDate=1970-01-01T00:00:00Z" +
                                "&constant=aConstant",
                        "1970-01-02T00:00:00Z"
                )
                , Arguments.of("http://localhost:8080/reports/" +
                                "?constant=aConstant"
                        , "blah"
                        , "http://localhost:8080/reports/" +
                                "?constant=aConstant"
                        , "blah"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("createURIStringsAndExpected")
    void can_create_URI_from_URI_with_date_vars_and_have_them_converted(
            String httpTargetURIAsString, String body,
            String expectedURI, String expectedBody) {

        HttpTarget httpTarget = httpTargetFactory.createHttpTarget(httpTargetURIAsString, body);

        assertThat(httpTarget)
                .isNotNull()
                .isEqualTo(new HttpTarget(URI.create(expectedURI), expectedBody));

    }

}