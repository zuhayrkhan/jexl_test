package com.zuhayrkhan.context;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.MapContext;

import java.util.function.Consumer;

public class JexlContextBuilder {

    private final JexlContext jexlContext;

    public JexlContextBuilder() {
        this(new MapContext());
    }

    public JexlContextBuilder(JexlContext jexlContext) {
        this.jexlContext = jexlContext;
    }

    public JexlContextBuilder populateFrom(Consumer<JexlContext> contextConsumer) {
        contextConsumer.accept(jexlContext);
        return this;
    }

    public JexlContext build() {
        return jexlContext;
    }

}
