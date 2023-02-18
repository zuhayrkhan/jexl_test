package com.zuhayrkhan.converter.strategy.jexl.context;

import com.zuhayrkhan.converter.context.ContextBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.MapContext;

import java.util.function.Consumer;

public class JexlContextBuilder implements ContextBuilder<JexlContext, JexlContextHolder> {

    private final JexlContextHolder jexlContextHolder;

    public JexlContextBuilder() {
        this(new JexlContextHolder(new MapContext()));
    }

    public JexlContextBuilder(JexlContextHolder jexlContextHolder) {
        this.jexlContextHolder = jexlContextHolder;
    }

    @Override
    public ContextBuilder<JexlContext, JexlContextHolder> populateFrom(Consumer<JexlContextHolder> contextHolderConsumer) {
        contextHolderConsumer.accept(jexlContextHolder);
        return this;
    }

    @Override
    public JexlContextHolder build() {
        return jexlContextHolder;
    }

}
