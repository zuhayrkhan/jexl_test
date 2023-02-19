package com.zuhayrkhan.converter.context;

import java.util.function.Consumer;

public interface ContextBuilder {

    ContextBuilder populateFrom(Consumer<ContextHolder> contextConsumer);

    ContextHolder build();

}
