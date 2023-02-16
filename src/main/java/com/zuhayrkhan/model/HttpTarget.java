package com.zuhayrkhan.model;

import java.net.URI;
import java.util.Objects;
import java.util.regex.Pattern;

public class HttpTarget {

    private final URI uri;
    private final String body;
    private final Pattern responseMatchForSuccess;

    public HttpTarget(URI uri, String body, Pattern responseMatchForSuccess) {
        this.uri = uri;
        this.body = body;
        this.responseMatchForSuccess = responseMatchForSuccess;
    }

    public URI getUri() {
        return uri;
    }

    public String getBody() {
        return body;
    }

    public Pattern getResponseMatchForSuccess() {
        return responseMatchForSuccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpTarget that = (HttpTarget) o;
        return Objects.equals(uri, that.uri) && Objects.equals(body, that.body) && Objects.equals(responseMatchForSuccess, that.responseMatchForSuccess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, body, responseMatchForSuccess);
    }

    @Override
    public String toString() {
        return "HttpTarget{" +
                "uri=" + uri +
                ", body='" + body + '\'' +
                ", responseMatchForSuccess='" + responseMatchForSuccess + '\'' +
                '}';
    }
}
