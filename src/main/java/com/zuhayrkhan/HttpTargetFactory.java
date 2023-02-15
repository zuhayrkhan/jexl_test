package com.zuhayrkhan;

import com.zuhayrkhan.model.HttpTarget;

import java.net.URI;

public class HttpTargetFactory {

    private final HttpTargetChanger httpTargetChanger;

    public HttpTargetFactory(HttpTargetChanger httpTargetChanger) {
        this.httpTargetChanger = httpTargetChanger;
    }

    public HttpTarget createHttpTarget(String uriAsString, String body) {
        return new HttpTarget(URI.create(httpTargetChanger.convert(uriAsString)),
                httpTargetChanger.convert(body)
        );
    }

}
