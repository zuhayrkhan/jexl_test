package com.zuhayrkhan.converter.strategy.jexl.context;

import com.zuhayrkhan.converter.context.ContextBuilder;
import com.zuhayrkhan.converter.context.ContextHolder;
import org.apache.commons.jexl3.JexlContext;

import java.util.function.Consumer;

public class JexlContextBuilder implements ContextBuilder {

    private final JexlContextHolder jexlContextHolder;

    public JexlContextBuilder(JexlContext jexlContext) {
        this.jexlContextHolder = new JexlContextHolder(jexlContext);
    }

    @Override
    public ContextBuilder populateFrom(Consumer<ContextHolder> contextHolderConsumer) {
        contextHolderConsumer.accept(jexlContextHolder);
        return this;
    }

    @Override
    public ContextHolder build() {
        return jexlContextHolder;
    }

}
