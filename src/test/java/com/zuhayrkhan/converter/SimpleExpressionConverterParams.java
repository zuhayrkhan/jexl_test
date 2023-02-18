package com.zuhayrkhan.converter;

import com.zuhayrkhan.converter.context.ContextBuilder;
import com.zuhayrkhan.converter.context.ContextHolder;

class SimpleExpressionConverterParams<CONTEXT, CONTEXT_HOLDER extends ContextHolder<CONTEXT>,
        CONTEXT_BUILDER extends ContextBuilder<CONTEXT, CONTEXT_HOLDER>> {

    static Builder simpleExpressionConverterParamsWithAllVars() {
        return new Builder()
                .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&untilDate=${today}")
                .withExpectedURI("http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z");
    }

    static Builder simpleExpressionConverterParamsWithSomeVars() {
        return new Builder()
                .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&untilDate=${today}" +
                        "&constant=aConstant")
                .withExpectedURI("http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z" +
                        "&constant=aConstant");
    }

    static Builder simpleExpressionConverterParamsWithNoVars() {
        return new Builder()
                .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                        "?constant=aConstant")
                .withExpectedURI("http://localhost:8080/reports/" +
                        "?constant=aConstant");
    }

    static class Builder<CONTEXT, CONTEXT_HOLDER extends ContextHolder<CONTEXT>,
            CONTEXT_BUILDER extends ContextBuilder<CONTEXT, CONTEXT_HOLDER>> {

        private SimpleExpressionConverter<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> simpleExpressionConverter;
        private String httpTargetURIAsString;
        private String expectedURI;

        public Builder withSimpleExpressionConverter(
                SimpleExpressionConverter<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> simpleExpressionConverter) {
            this.simpleExpressionConverter = simpleExpressionConverter;
            return this;
        }

        public Builder withHttpTargetURIAsString(String httpTargetURIAsString) {
            this.httpTargetURIAsString = httpTargetURIAsString;
            return this;
        }

        public Builder withExpectedURI(String expectedURI) {
            this.expectedURI = expectedURI;
            return this;
        }

        SimpleExpressionConverterParams build() {
            return new SimpleExpressionConverterParams(
                    simpleExpressionConverter,
                    httpTargetURIAsString,
                    expectedURI
            );
        }

    }

    private final SimpleExpressionConverter<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> simpleExpressionConverter;
    private final String httpTargetURIAsString;
    private final String expectedURI;

    SimpleExpressionConverterParams(SimpleExpressionConverter<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> simpleExpressionConverter,
                                    String httpTargetURIAsString,
                                    String expectedURI) {
        this.simpleExpressionConverter = simpleExpressionConverter;
        this.httpTargetURIAsString = httpTargetURIAsString;
        this.expectedURI = expectedURI;
    }

    public SimpleExpressionConverter<CONTEXT, CONTEXT_HOLDER, CONTEXT_BUILDER> getSimpleExpressionConverter() {
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
