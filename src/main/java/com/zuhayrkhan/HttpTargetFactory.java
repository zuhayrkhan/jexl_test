package com.zuhayrkhan;

import com.zuhayrkhan.converter.SimpleExpressionConverter;
import com.zuhayrkhan.converter.context.ContextBuilder;
import com.zuhayrkhan.converter.context.ContextHolder;
import com.zuhayrkhan.model.HttpTarget;

import java.net.URI;

public class HttpTargetFactory<CONTEXT, CONTEXT_HOLDER extends ContextHolder<CONTEXT>,
        CONTEXT_BUILDER extends ContextBuilder<CONTEXT, CONTEXT_HOLDER>> {

    private final SimpleExpressionConverter<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> simpleExpressionConverter;

    public HttpTargetFactory(SimpleExpressionConverter<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> simpleExpressionConverter) {
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
