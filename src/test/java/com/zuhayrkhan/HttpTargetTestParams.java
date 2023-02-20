package com.zuhayrkhan;

class HttpTargetTestParams<CONTEXT> {

    static <CONTEXT> Builder<CONTEXT> httpTargetTestParamsWithSomeConstAmongVars(Class<CONTEXT> contextClass) {
        return new Builder<CONTEXT>()
                .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&anotherParam=anotherValue" +
                        "&untilDate=${today}"
                )
                .withBody("${tomorrow}")
                .withResponseMatchForSuccess("${today}")
                .withExpectedURI("http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&anotherParam=anotherValue" +
                        "&untilDate=1970-01-01T00:00:00Z"
                )
                .withExpectedBody("1970-01-02T00:00:00Z")
                .withExpectedResponseMatchForSuccess("1970-01-01T00:00:00Z");
    }

    static <CONTEXT> Builder<CONTEXT> httpTargetTestParamsWithSomeVars(Class<CONTEXT> contextClass) {
        return new Builder<CONTEXT>()
                .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                        "?fromDate=${yesterday}" +
                        "&untilDate=${today}" +
                        "&constant=aConstant"
                )
                .withBody("${tomorrow}")
                .withResponseMatchForSuccess("${today}")
                .withExpectedURI("http://localhost:8080/reports/" +
                        "?fromDate=1969-12-31T00:00:00Z" +
                        "&untilDate=1970-01-01T00:00:00Z" +
                        "&constant=aConstant"
                )
                .withExpectedBody("1970-01-02T00:00:00Z")
                .withExpectedResponseMatchForSuccess("1970-01-01T00:00:00Z");
    }

    static <CONTEXT> Builder<CONTEXT> httpTargetTestParamsWithNoVars(Class<CONTEXT> contextClass) {
        return new Builder<CONTEXT>()
                .withHttpTargetURIAsString("http://localhost:8080/reports/" +
                        "?constant=aConstant"
                )
                .withBody("blah")
                .withResponseMatchForSuccess(null)
                .withExpectedURI("http://localhost:8080/reports/" +
                        "?constant=aConstant"
                )
                .withExpectedBody("blah")
                .withExpectedResponseMatchForSuccess(null);
    }

    static class Builder<CONTEXT> {

        private HttpTargetFactory<CONTEXT> httpTargetFactory;
        private String httpTargetURIAsString;
        private String body;
        private String responseMatchForSuccess;
        private String expectedURI;
        private String expectedBody;
        private String expectedResponseMatchForSuccess;

        Builder<CONTEXT> withHttpTargetFactory(HttpTargetFactory<CONTEXT> httpTargetFactory) {
            this.httpTargetFactory = httpTargetFactory;
            return this;
        }

        Builder<CONTEXT> withHttpTargetURIAsString(String httpTargetURIAsString) {
            this.httpTargetURIAsString = httpTargetURIAsString;
            return this;
        }

        Builder<CONTEXT> withBody(String body) {
            this.body = body;
            return this;
        }

        Builder<CONTEXT> withResponseMatchForSuccess(String responseMatchForSuccess) {
            this.responseMatchForSuccess = responseMatchForSuccess;
            return this;
        }

        Builder<CONTEXT> withExpectedURI(String expectedURI) {
            this.expectedURI = expectedURI;
            return this;
        }

        Builder<CONTEXT> withExpectedBody(String expectedBody) {
            this.expectedBody = expectedBody;
            return this;
        }

        Builder<CONTEXT> withExpectedResponseMatchForSuccess(String expectedResponseMatchForSuccess) {
            this.expectedResponseMatchForSuccess = expectedResponseMatchForSuccess;
            return this;
        }

        HttpTargetTestParams<CONTEXT> build() {
            return new HttpTargetTestParams<>(
                    httpTargetFactory, httpTargetURIAsString,
                    body,
                    responseMatchForSuccess,
                    expectedURI,
                    expectedBody,
                    expectedResponseMatchForSuccess
            );
        }

    }

    private final HttpTargetFactory<CONTEXT> httpTargetFactory;
    private final String httpTargetURIAsString;
    private final String body;
    private final String responseMatchForSuccess;
    private final String expectedURI;
    private final String expectedBody;
    private final String expectedResponseMatchForSuccess;

    HttpTargetTestParams(HttpTargetFactory<CONTEXT> httpTargetFactory,
                         String httpTargetURIAsString,
                         String body,
                         String responseMatchForSuccess,
                         String expectedURI,
                         String expectedBody,
                         String expectedResponseMatchForSuccess) {
        this.httpTargetFactory = httpTargetFactory;
        this.httpTargetURIAsString = httpTargetURIAsString;
        this.body = body;
        this.responseMatchForSuccess = responseMatchForSuccess;
        this.expectedURI = expectedURI;
        this.expectedBody = expectedBody;
        this.expectedResponseMatchForSuccess = expectedResponseMatchForSuccess;
    }

    public HttpTargetFactory<CONTEXT> getHttpTargetFactory() {
        return httpTargetFactory;
    }

    public String getHttpTargetURIAsString() {
        return httpTargetURIAsString;
    }

    public String getBody() {
        return body;
    }

    public String getResponseMatchForSuccess() {
        return responseMatchForSuccess;
    }

    public String getExpectedURI() {
        return expectedURI;
    }

    public String getExpectedBody() {
        return expectedBody;
    }

    public String getExpectedResponseMatchForSuccess() {
        return expectedResponseMatchForSuccess;
    }

    @Override
    public String toString() {
        return "{" +
                httpTargetFactory.toString() + '\'' +
                "httpTargetURIAsString='" + httpTargetURIAsString + '\'' +
                ", body='" + body + '\'' +
                ", responseMatchForSuccess='" + responseMatchForSuccess + '\'' +
                ", expectedURI='" + expectedURI + '\'' +
                ", expectedBody='" + expectedBody + '\'' +
                ", expectedResponseMatchForSuccess='" + expectedResponseMatchForSuccess + '\'' +
                '}';
    }

}
