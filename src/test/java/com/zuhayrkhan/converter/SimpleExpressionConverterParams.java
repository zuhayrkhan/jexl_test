package com.zuhayrkhan.converter;

import com.zuhayrkhan.converter.context.ContextBuilder;
import com.zuhayrkhan.converter.context.ContextHolder;

class SimpleExpressionConverterParams<CONTEXT, CONTEXT_HOLDER extends ContextHolder,
        CONTEXT_BUILDER extends ContextBuilder> {

    static <CONTEXT, CONTEXT_HOLDER extends ContextHolder,
            CONTEXT_BUILDER extends ContextBuilder>
    Builder<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> simpleExpressionConverterParamsWithAllVars(
            Class<CONTEXT> contextClass, Class<CONTEXT_HOLDER> contextHolderClass, Class<CONTEXT_BUILDER> contextBuilderClass) {
        return new Builder<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER>()
                .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&untilDate=${today}")
                .withExpectedURI("http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z");
    }

    static <CONTEXT, CONTEXT_HOLDER extends ContextHolder,
            CONTEXT_BUILDER extends ContextBuilder>
    Builder<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> simpleExpressionConverterParamsWithSomeVars(
            Class<CONTEXT> contextClass, Class<CONTEXT_HOLDER> contextHolderClass, Class<CONTEXT_BUILDER> contextBuilderClass) {
        return new Builder<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER>()
                .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&untilDate=${today}" +
                        "&constant=aConstant")
                .withExpectedURI("http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z" +
                        "&constant=aConstant");
    }

    static <CONTEXT, CONTEXT_HOLDER extends ContextHolder,
            CONTEXT_BUILDER extends ContextBuilder>
    Builder<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> simpleExpressionConverterParamsWithNoVars(
            Class<CONTEXT> contextClass, Class<CONTEXT_HOLDER> contextHolderClass, Class<CONTEXT_BUILDER> contextBuilderClass) {
        return new Builder<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER>()
                .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                        "?constant=aConstant")
                .withExpectedURI("http://localhost:8080/reports/" +
                        "?constant=aConstant");
    }

    static class Builder<CONTEXT, CONTEXT_HOLDER extends ContextHolder,
            CONTEXT_BUILDER extends ContextBuilder> {

        private SimpleExpressionConverter<CONTEXT, CONTEXT_BUILDER> simpleExpressionConverter;
        private String httpTargetURIAsString;
        private String expectedURI;

        Builder<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> withSimpleExpressionConverter(
                SimpleExpressionConverter<CONTEXT, CONTEXT_BUILDER> simpleExpressionConverter) {
            this.simpleExpressionConverter = simpleExpressionConverter;
            return this;
        }

        Builder<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> withHttpTargetURIAsString(String httpTargetURIAsString) {
            this.httpTargetURIAsString = httpTargetURIAsString;
            return this;
        }

        Builder<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> withExpectedURI(String expectedURI) {
            this.expectedURI = expectedURI;
            return this;
        }

        SimpleExpressionConverterParams<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> build() {
            return new SimpleExpressionConverterParams<>(
                    simpleExpressionConverter,
                    httpTargetURIAsString,
                    expectedURI
            );
        }

    }

    private final SimpleExpressionConverter<CONTEXT, CONTEXT_BUILDER> simpleExpressionConverter;
    private final String httpTargetURIAsString;
    private final String expectedURI;

    SimpleExpressionConverterParams(SimpleExpressionConverter<CONTEXT, CONTEXT_BUILDER> simpleExpressionConverter,
                                    String httpTargetURIAsString,
                                    String expectedURI) {
        this.simpleExpressionConverter = simpleExpressionConverter;
        this.httpTargetURIAsString = httpTargetURIAsString;
        this.expectedURI = expectedURI;
    }

    public SimpleExpressionConverter<CONTEXT, CONTEXT_BUILDER> getSimpleExpressionConverter() {
        return simpleExpressionConverter;
    }

    public String getHttpTargetURIAsString() {
        return httpTargetURIAsString;
    }

    public String getExpectedURI() {
        return expectedURI;
    }

    @Override
    public String toString() {
        return "{" +
                simpleExpressionConverter.toString() +
                ", httpTargetURIAsString='" + httpTargetURIAsString + '\'' +
                ", expectedURI='" + expectedURI + '\'' +
                '}';
    }

}
