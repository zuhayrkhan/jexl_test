package com.zuhayrkhan;

import com.zuhayrkhan.context.CommonDatesPopulator;
import com.zuhayrkhan.context.JexlContextBuilder;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;

public class HttpTargetChanger {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpTargetChanger.class);

    private final Clock clock;

    private final JexlEngine jexlEngine = new JexlBuilder().cache(512).strict(true).silent(false).create();

    public HttpTargetChanger(Clock clock) {
        this.clock = clock;
    }

    private static String createJexlExpression(String httpTargetURIAsString) {
        return "'" + httpTargetURIAsString
                .replace("${", "' + ")
                .replaceAll("}([^$]*)", " + '$1")
                .replaceAll("}$", "");
    }

    public String convert(String httpTargetURIAsString) {

        LOGGER.info("httpTargetURIAsString={}", httpTargetURIAsString);

        String httpTargetURIAsJexlExpression = createJexlExpression(httpTargetURIAsString);

        LOGGER.info("httpTargetURIAsJexlExpression={}", httpTargetURIAsJexlExpression);

        JexlExpression jexlExpression = jexlEngine.createExpression(httpTargetURIAsJexlExpression);

        JexlContext jexlContext = new JexlContextBuilder()
                .populateFrom(context -> context.set("clock", clock))
                .populateFrom(CommonDatesPopulator::addCommonDatesToContext)
                .build();

        String result = (String) jexlExpression.evaluate(jexlContext);

        LOGGER.info("result={}", result);

        return result;
    }

}
