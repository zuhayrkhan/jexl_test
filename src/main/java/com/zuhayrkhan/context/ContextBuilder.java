package com.zuhayrkhan.context;

import java.util.function.Consumer;

public interface ContextBuilder<CONTEXT_HOLDER extends ContextHolder<?>> {

    ContextBuilder<CONTEXT_HOLDER> populateFrom(Consumer<CONTEXT_HOLDER> contextConsumer);

    CONTEXT_HOLDER build();

}
