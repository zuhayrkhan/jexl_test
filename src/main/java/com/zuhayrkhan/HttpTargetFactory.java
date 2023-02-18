package com.zuhayrkhan;

import com.zuhayrkhan.converter.SimpleExpressionConverter;
import com.zuhayrkhan.model.HttpTarget;

import java.net.URI;
import java.util.Optional;
import java.util.regex.Pattern;

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

    private Pattern createPattern(String responseMatchForSuccess) {
        return Optional.ofNullable(responseMatchForSuccess)
                .map(s -> Pattern.compile(simpleExpressionConverter.convert(responseMatchForSuccess)))
                .orElse(null);
    }

}
