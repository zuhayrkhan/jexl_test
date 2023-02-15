package com.zuhayrkhan;

import java.net.URI;

public class URIFactory {

    private final HttpTargetChanger httpTargetChanger;

    public URIFactory(HttpTargetChanger httpTargetChanger) {
        this.httpTargetChanger = httpTargetChanger;
    }

    public URI createURI(String uriAsString) {
        return URI.create(httpTargetChanger.convert(uriAsString));
    }

}
