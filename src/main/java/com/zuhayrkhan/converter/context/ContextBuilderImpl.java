package com.zuhayrkhan.converter.context;

import java.util.function.Consumer;

public class ContextBuilderImpl<CONTEXT, CONTEXT_HOLDER extends ContextHolder<CONTEXT>> implements ContextBuilder<CONTEXT, CONTEXT_HOLDER> {

    private final CONTEXT_HOLDER contextHolder;

    public ContextBuilderImpl(CONTEXT_HOLDER contextHolder) {
        this.contextHolder = contextHolder;
    }

    @Override
    public ContextBuilder<CONTEXT, CONTEXT_HOLDER> populateFrom(Consumer<CONTEXT_HOLDER> contextConsumer) {
        contextConsumer.accept(contextHolder);
        return this;
    }

    @Override
    public CONTEXT_HOLDER build() {
        return contextHolder;
    }

}
