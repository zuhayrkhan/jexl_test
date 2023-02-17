package com.zuhayrkhan.model;

import java.net.URI;

public class HttpTargetBuilder {

    private URI uri = URI.create("http://localhost:8080/reports/" +
            "?fromDate=1969-12-31T00:00:00Z" +
            "&untilDate=1970-01-01T00:00:00Z");

    private String body = "1970-01-02T00:00:00Z";

    private String responseMatchForSuccess = ".*1970-01-01T00:00:00Z.*";

    public HttpTargetBuilder withURI(URI uri) {
        this.uri = uri;
        return this;
    }

    public HttpTargetBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public HttpTargetBuilder withResponseMatchForSuccess(String responseMatchForSuccess) {
        this.responseMatchForSuccess = responseMatchForSuccess;
        return this;
    }

    public HttpTarget build() {
        return new HttpTarget(uri, body, responseMatchForSuccess);
    }

    public static HttpTargetBuilder aHttpTarget() {
        return new HttpTargetBuilder();
    }

}
