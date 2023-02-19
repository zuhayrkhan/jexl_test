package com.zuhayrkhan.converter.strategy.map.context;

import com.zuhayrkhan.converter.context.ContextBuilder;

import java.util.function.Consumer;

public class MapContextBuilder implements ContextBuilder<SimpleMapContext, MapContextHolder> {

    private final MapContextHolder contents;

    public MapContextBuilder(SimpleMapContext context) {
        contents = new MapContextHolder(context);
    }

    @Override
    public ContextBuilder<SimpleMapContext, MapContextHolder> populateFrom(
            Consumer<MapContextHolder> contextConsumer) {
        contextConsumer.accept(contents);
        return this;
    }

    @Override
    public MapContextHolder build() {
        return contents;
    }
}
