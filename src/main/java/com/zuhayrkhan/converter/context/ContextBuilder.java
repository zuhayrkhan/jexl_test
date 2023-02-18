package com.zuhayrkhan.converter.context;

import java.util.function.Consumer;

public interface ContextBuilder<CONTEXT, CONTEXT_HOLDER extends ContextHolder<?>> {

    ContextBuilder<CONTEXT, CONTEXT_HOLDER> populateFrom(Consumer<CONTEXT_HOLDER> contextConsumer);

    CONTEXT_HOLDER build();

}
