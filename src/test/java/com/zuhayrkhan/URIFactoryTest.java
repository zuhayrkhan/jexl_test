package com.zuhayrkhan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class URIFactoryTest {

    private final Clock clock = Clock.fixed(Instant.ofEpochMilli(0), ZoneId.of("UTC"));
    private URIFactory uriFactory;

    @BeforeEach
    void setUp() {
        uriFactory = new URIFactory(new HttpTargetChanger(clock));
    }

    @Test
    void can_create_URI_from_URI_with_date_vars_and_have_them_converted() {

        URI reportsURI = uriFactory.createURI("http://localhost:8080/reports/" +
                "?fromDate=${yesterday}" +
                "&untilDate=${today}" +
                "&constant=aConstant"
        );

        assertThat(reportsURI)
                .isNotNull()
                .isEqualTo(URI.create("http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z" +
                        "&constant=aConstant"
                ))
        ;

    }

}