package com.zuhayrkhan.converter.strategy.map.context;

import com.zuhayrkhan.converter.context.ContextBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class MapContextBuilder implements ContextBuilder<Map<String, Object>, MapContextHolder> {

    private final MapContextHolder contents = new MapContextHolder(new ConcurrentHashMap<>());

    @Override
    public ContextBuilder<Map<String, Object>, MapContextHolder> populateFrom(
            Consumer<MapContextHolder> contextConsumer) {
        contextConsumer.accept(contents);
        return this;
    }

    @Override
    public MapContextHolder build() {
        return contents;
    }
}
