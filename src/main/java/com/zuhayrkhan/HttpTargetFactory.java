package com.zuhayrkhan;

import com.zuhayrkhan.model.HttpTarget;

import java.net.URI;

public class HttpTargetFactory {

    private final SimpleExpressionConverter simpleExpressionConverter;

    public HttpTargetFactory(SimpleExpressionConverter simpleExpressionConverter) {
        this.simpleExpressionConverter = simpleExpressionConverter;
    }

    public HttpTarget createHttpTarget(String uriAsString, String body) {
        return new HttpTarget(URI.create(simpleExpressionConverter.convert(uriAsString)),
                simpleExpressionConverter.convert(body)
        );
    }

}
