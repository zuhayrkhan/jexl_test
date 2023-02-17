package com.zuhayrkhan.context;

public interface ContextHolder<CONTEXT> {

    void addIntoContext(String key, Object value);

    Object getFromContext(String key);

    CONTEXT unWrapContext();

}
