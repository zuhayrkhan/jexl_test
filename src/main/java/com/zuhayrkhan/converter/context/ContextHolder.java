package com.zuhayrkhan.converter.context;

public interface ContextHolder {

    void addIntoContext(String key, Object value);

    Object getFromContext(String key);

}
