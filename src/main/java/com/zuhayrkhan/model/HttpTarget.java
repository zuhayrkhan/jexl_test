package com.zuhayrkhan.model;

import java.net.URI;
import java.util.Objects;

public class HttpTarget {

    private final URI uri;
    private final String body;

    public HttpTarget(URI uri, String body) {
        this.uri = uri;
        this.body = body;
    }

    public URI getUri() {
        return uri;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpTarget that = (HttpTarget) o;
        return Objects.equals(uri, that.uri) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, body);
    }

    @Override
    public String toString() {
        return "HttpTarget{" +
                "uri=" + uri +
                ", body='" + body + '\'' +
                '}';
    }
}
