package com.zuhayrkhan.converter;

class SimpleExpressionConverterParams {

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

    static class Builder {

        private SimpleExpressionConverter<?> simpleExpressionConverter;
        private String httpTargetURIAsString;
        private String expectedURI;

        public Builder withSimpleExpressionConverter(SimpleExpressionConverter<?> simpleExpressionConverter) {
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

    private final SimpleExpressionConverter<?> simpleExpressionConverter;
    private final String httpTargetURIAsString;
    private final String expectedURI;

    SimpleExpressionConverterParams(SimpleExpressionConverter<?> simpleExpressionConverter,
                                    String httpTargetURIAsString,
                                    String expectedURI) {
        this.simpleExpressionConverter = simpleExpressionConverter;
        this.httpTargetURIAsString = httpTargetURIAsString;
        this.expectedURI = expectedURI;
    }

    public SimpleExpressionConverter<?> getSimpleExpressionConverter() {
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
        return "SECP{" +
                "simpleExpressionConverter=" + simpleExpressionConverter +
                ", httpTargetURIAsString='" + httpTargetURIAsString + '\'' +
                ", expectedURI='" + expectedURI + '\'' +
                '}';
    }

}
