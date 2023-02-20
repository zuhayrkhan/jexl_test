package com.zuhayrkhan.converter;

class SimpleExpressionConverterParams<CONTEXT> {

    static <CONTEXT>
    Builder<CONTEXT> simpleExpressionConverterParamsWithAllVars(Class<CONTEXT> contextClass) {
        return new Builder<CONTEXT>()
                .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&untilDate=${today}")
                .withExpectedURI("http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z");
    }

    static <CONTEXT>
    Builder<CONTEXT> simpleExpressionConverterParamsWithSomeVars(Class<CONTEXT> contextClass) {
        return new Builder<CONTEXT>()
                .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&untilDate=${today}" +
                        "&constant=aConstant")
                .withExpectedURI("http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z" +
                        "&constant=aConstant");
    }

    static <CONTEXT>
    Builder<CONTEXT> simpleExpressionConverterParamsWithNoVars(Class<CONTEXT> contextClass) {
        return new Builder<CONTEXT>()
                .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                        "?constant=aConstant")
                .withExpectedURI("http://localhost:8080/reports/" +
                        "?constant=aConstant");
    }

    static class Builder<CONTEXT> {

        private SimpleExpressionConverter<CONTEXT> simpleExpressionConverter;
        private String httpTargetURIAsString;
        private String expectedURI;

        Builder<CONTEXT> withSimpleExpressionConverter(
                SimpleExpressionConverter<CONTEXT> simpleExpressionConverter) {
            this.simpleExpressionConverter = simpleExpressionConverter;
            return this;
        }

        Builder<CONTEXT> withHttpTargetURIAsString(String httpTargetURIAsString) {
            this.httpTargetURIAsString = httpTargetURIAsString;
            return this;
        }

        Builder<CONTEXT> withExpectedURI(String expectedURI) {
            this.expectedURI = expectedURI;
            return this;
        }

        SimpleExpressionConverterParams<CONTEXT> build() {
            return new SimpleExpressionConverterParams<>(
                    simpleExpressionConverter,
                    httpTargetURIAsString,
                    expectedURI
            );
        }

    }

    private final SimpleExpressionConverter<CONTEXT> simpleExpressionConverter;
    private final String httpTargetURIAsString;
    private final String expectedURI;

    SimpleExpressionConverterParams(SimpleExpressionConverter<CONTEXT> simpleExpressionConverter,
                                    String httpTargetURIAsString,
                                    String expectedURI) {
        this.simpleExpressionConverter = simpleExpressionConverter;
        this.httpTargetURIAsString = httpTargetURIAsString;
        this.expectedURI = expectedURI;
    }

    public SimpleExpressionConverter<CONTEXT> getSimpleExpressionConverter() {
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
