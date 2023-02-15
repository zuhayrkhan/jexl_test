package com.zuhayrkhan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class HttpTargetChangerTest {

    private final Clock clock = Clock.fixed(Instant.ofEpochMilli(0), ZoneId.of("UTC"));
    private HttpTargetChanger httpTargetChanger;

    @BeforeEach
    void setUp() {
        httpTargetChanger = new HttpTargetChanger(clock);
    }

    @Test
    void can_specify_target_with_date_vars_and_have_them_converted() {

        String httpTargetURIAsString = "http://localhost:8080/reports/?fromDate=${yesterday}&untilDate=${today}";

        String converted = httpTargetChanger.convert(httpTargetURIAsString);

        assertThat(converted)
                .isNotBlank()
                .isEqualTo("http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z")
        ;

    }

}