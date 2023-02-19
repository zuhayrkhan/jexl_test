package com.zuhayrkhan.converter.strategy.map.context;

import com.zuhayrkhan.converter.context.ContextBuilder;
import com.zuhayrkhan.converter.context.ContextHolder;

import java.util.function.Consumer;

public class MapContextBuilder implements ContextBuilder {

    private final ContextHolder contextHolder;

    public MapContextBuilder(ContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }

    @Override
    public ContextBuilder populateFrom(
            Consumer<ContextHolder> contextConsumer) {
        contextConsumer.accept(contextHolder);
        return this;
    }

    @Override
    public ContextHolder build() {
        return contextHolder;
    }
}
