package com.zuhayrkhan.converter.strategy.map.context;

import com.zuhayrkhan.converter.context.ContextHolder;

import java.util.Map;

public class MapContextHolder implements ContextHolder<Map<String, Object>> {

    private final Map<String, Object> content;

    public MapContextHolder(Map<String, Object> content) {
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
    public Map<String, Object> unWrapContext() {
        return content;
    }
}
