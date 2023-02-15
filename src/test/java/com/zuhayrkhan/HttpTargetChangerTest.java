package com.zuhayrkhan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpTargetChangerTest {

    private final Clock clock = Clock.fixed(Instant.ofEpochMilli(0), ZoneId.of("UTC"));
    private HttpTargetChanger httpTargetChanger;

    public static Stream<Arguments> createURIStringsAndExpected() {
        return Stream.of(
                Arguments.of("http://localhost:8080/reports/" +
                                "?fromDate=${yesterday}" +
                                "&untilDate=${today}"
                        , "http://localhost:8080/reports/" +
                                "?fromDate=1969-12-31T00:00:00Z" +
                                "&untilDate=1970-01-01T00:00:00Z"
                )
                , Arguments.of("http://localhost:8080/reports/" +
                                "?fromDate=${yesterday}" +
                                "&untilDate=${today}" +
                                "&constant=aConstant"
                        , "http://localhost:8080/reports/" +
                                "?fromDate=1969-12-31T00:00:00Z" +
                                "&untilDate=1970-01-01T00:00:00Z" +
                                "&constant=aConstant"
                )
                , Arguments.of("http://localhost:8080/reports/" +
                                "?constant=aConstant"
                        , "http://localhost:8080/reports/" +
                                "?constant=aConstant"
                )
        );
    }

    @BeforeEach
    void setUp() {
        httpTargetChanger = new HttpTargetChanger(clock);
    }


    @ParameterizedTest
    @MethodSource("createURIStringsAndExpected")
    void can_specify_target_with_date_vars_and_have_them_converted(String httpTargetURIAsString, String expected) {

        String converted = httpTargetChanger.convert(httpTargetURIAsString);

        assertThat(converted).isNotBlank().isEqualTo(expected);

    }

}