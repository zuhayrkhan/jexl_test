package com.zuhayrkhan.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class MapContextBuilder implements ContextBuilder<ContextHolder<Map<String, Object>>> {

    private final ContextHolder<Map<String, Object>> contents = new MapContextHolder(new ConcurrentHashMap<>());

    @Override
    public ContextBuilder<ContextHolder<Map<String, Object>>> populateFrom(Consumer<ContextHolder<Map<String, Object>>> contextConsumer) {
        contextConsumer.accept(contents);
        return this;
    }

    @Override
    public ContextHolder<Map<String, Object>> build() {
        return contents;
    }
}
