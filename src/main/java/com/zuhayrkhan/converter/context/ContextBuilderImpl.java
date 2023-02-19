package com.zuhayrkhan.converter.context;

import java.util.function.Consumer;

public class ContextBuilderImpl implements ContextBuilder {

    private final ContextHolder contextHolder;

    public ContextBuilderImpl(ContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }

    @Override
    public ContextBuilder populateFrom(Consumer<ContextHolder> contextConsumer) {
        contextConsumer.accept(contextHolder);
        return this;
    }

    @Override
    public ContextHolder build() {
        return contextHolder;
    }

}
