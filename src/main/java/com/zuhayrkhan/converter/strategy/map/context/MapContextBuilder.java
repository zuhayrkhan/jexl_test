package com.zuhayrkhan.converter.strategy.map.context;

import com.zuhayrkhan.context.ContextBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class MapContextBuilder implements ContextBuilder<MapContextHolder> {

    private final MapContextHolder contents = new MapContextHolder(new ConcurrentHashMap<>());

    @Override
    public ContextBuilder<MapContextHolder> populateFrom(Consumer<MapContextHolder> contextConsumer) {
        contextConsumer.accept(contents);
        return this;
    }

    @Override
    public MapContextHolder build() {
        return contents;
    }
}
