package com.zuhayrkhan.converter.strategy.map.context;

import com.zuhayrkhan.converter.context.ContextHolder;

public class MapContextHolder implements ContextHolder<SimpleMapContext> {

    private final SimpleMapContext content;

    public MapContextHolder(SimpleMapContext content) {
        this.content = content;
    }

    @Override
    public Object getFromContext(String key) {
        return content.get(key);
    }

    @Override
    public void addIntoContext(String key, Object value) {
        content.put(key, value);
    }

    @Override
    public SimpleMapContext unWrapContext() {
        return content;
    }
}
