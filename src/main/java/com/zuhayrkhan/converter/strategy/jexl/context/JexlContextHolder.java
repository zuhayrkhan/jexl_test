package com.zuhayrkhan.converter.strategy.jexl.context;

import com.zuhayrkhan.converter.context.ContextHolder;
import org.apache.commons.jexl3.JexlContext;

public class JexlContextHolder implements ContextHolder<JexlContext> {

    private final JexlContext jexlContext;

    public JexlContextHolder(JexlContext jexlContext) {
        this.jexlContext = jexlContext;
    }

    @Override
    public Object getFromContext(String key) {
        return jexlContext.get(key);
    }

    @Override
    public void addIntoContext(String key, Object value) {
        jexlContext.set(key, value);
    }

    @Override
    public JexlContext unWrapContext() {
        return jexlContext;
    }

}
