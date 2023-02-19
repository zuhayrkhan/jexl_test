package com.zuhayrkhan.converter.strategy.map.context;

import com.zuhayrkhan.converter.context.ContextBuilder;
import com.zuhayrkhan.converter.context.ContextHolder;

import java.util.function.Consumer;

public class MapContextBuilder implements ContextBuilder {

    private final MapContextHolder contents;

    public MapContextBuilder(SimpleMapContext context) {
        contents = new MapContextHolder(context);
    }

    @Override
    public ContextBuilder populateFrom(
            Consumer<ContextHolder> contextConsumer) {
        contextConsumer.accept(contents);
        return this;
    }

    @Override
    public ContextHolder build() {
        return contents;
    }
}
