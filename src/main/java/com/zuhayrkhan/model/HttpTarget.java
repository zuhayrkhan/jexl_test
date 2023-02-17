package com.zuhayrkhan.model;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class HttpTarget {

    private final URI uri;
    private final String body;
    private final String responseMatchForSuccess;
    private final AtomicReference<Pattern> responsePatternSingleton = new AtomicReference<>();

    public HttpTarget(URI uri, String body, String responseMatchForSuccess) {
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

    private Pattern getPatternResponseMatchForSuccess() {
        return Optional.ofNullable(responsePatternSingleton.get())
                .or(() -> {
                    Pattern myPattern = Pattern.compile(responseMatchForSuccess);
                    if (responsePatternSingleton.compareAndSet(null, myPattern)) {
                        return Optional.of(myPattern);
                    } else {
                        return Optional.of(responsePatternSingleton.get());
                    }
                })
                .orElseThrow();
    }

    public boolean checkResponseBodyForSuccess(String responseBody) {
        return getPatternResponseMatchForSuccess().matcher(responseBody).find();
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
