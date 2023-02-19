package com.zuhayrkhan.converter.context;

import java.util.function.Consumer;

public class ContextBuilderImpl<CONTEXT, CONTEXT_HOLDER extends ContextHolder> implements ContextBuilder {

    private final CONTEXT_HOLDER contextHolder;

    public ContextBuilderImpl(CONTEXT_HOLDER contextHolder) {
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
