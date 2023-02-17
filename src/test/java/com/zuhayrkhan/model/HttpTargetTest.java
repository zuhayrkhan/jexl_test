package com.zuhayrkhan.model;

import org.junit.jupiter.api.Test;

import static com.zuhayrkhan.model.HttpTargetBuilder.aHttpTarget;
import static org.assertj.core.api.Assertions.assertThat;

class HttpTargetTest {

    @Test
    void can_match_response_body_for_success() {

        HttpTarget httpTarget = aHttpTarget().build();

        assertThat(httpTarget.checkResponseBodyForSuccess("""
                Some text which includes 1970-01-01T00:00:00Z and then some other text
                """)).isTrue();

    }

    @Test
    void does_not_match_response_body_for_success() {

        HttpTarget httpTarget = aHttpTarget().build();

        assertThat(httpTarget.checkResponseBodyForSuccess("""
                Some text which includes no matching date-time and then some other text
                """)).isFalse();

    }

}