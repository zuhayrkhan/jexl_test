package com.zuhayrkhan.converter.strategy.jexl.context;

import com.zuhayrkhan.converter.context.ContextBuilder;
import com.zuhayrkhan.converter.context.ContextHolder;

import java.util.function.Consumer;

public class JexlContextBuilder implements ContextBuilder {

    private final ContextHolder contextHolder;

    public JexlContextBuilder(ContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }

    @Override
    public ContextBuilder populateFrom(Consumer<ContextHolder> contextHolderConsumer) {
        contextHolderConsumer.accept(contextHolder);
        return this;
    }

    @Override
    public ContextHolder build() {
        return contextHolder;
    }

}
