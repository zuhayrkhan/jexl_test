package com.zuhayrkhan;

import com.zuhayrkhan.converter.SimpleExpressionConverter;
import com.zuhayrkhan.model.HttpTarget;

import java.net.URI;

public class HttpTargetFactory<CONTEXT> {

    private final SimpleExpressionConverter<CONTEXT> simpleExpressionConverter;

    public HttpTargetFactory(SimpleExpressionConverter<CONTEXT> simpleExpressionConverter) {
        this.simpleExpressionConverter = simpleExpressionConverter;
    }

    public HttpTarget createHttpTarget(String uriAsString,
                                       String body,
                                       String responseMatchForSuccess) {
        return new HttpTarget(URI.create(simpleExpressionConverter.convert(uriAsString)),
                simpleExpressionConverter.convert(body),
                simpleExpressionConverter.convert(responseMatchForSuccess)
        );
    }

    @Override
    public String toString() {
        return "HttpTargetFactory{" +
                "simpleExpressionConverter=" + simpleExpressionConverter +
                '}';
    }
}
