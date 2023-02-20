package com.zuhayrkhan.converter.context;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ContextHolderImpl implements ContextHolder {

    private final BiConsumer<String, Object> addIntoContext;
    private final Function<String, Object> getFromContext;

    public ContextHolderImpl(BiConsumer<String, Object> addIntoContext, Function<String, Object> getFromContext) {
        this.addIntoContext = addIntoContext;
        this.getFromContext = getFromContext;
    }

    @Override
    public void addIntoContext(String key, Object value) {
        addIntoContext.accept(key, value);
    }

    @Override
    public Object getFromContext(String key) {
        return getFromContext.apply(key);
    }

}
