package com.zuhayrkhan.converter.strategy.jexl.converter;

import com.zuhayrkhan.converter.context.ContextHolder;
import com.zuhayrkhan.converter.strategy.ConverterStrategy;
import com.zuhayrkhan.converter.strategy.jexl.context.JexlContextBuilder;
import org.apache.commons.jexl3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

public class JexlConverterStrategy implements ConverterStrategy<JexlContext, JexlContextBuilder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JexlConverterStrategy.class);

    private final JexlEngine jexlEngine = new JexlBuilder().cache(512).strict(true).silent(false).create();

    @Override
    public Supplier<JexlContext> getContextFactory() {
        return MapContext::new;
    }

    @Override
    public Function<JexlContext, JexlContextBuilder> getContextBuilderFactory() {
        return JexlContextBuilder::new;
    }

    @Override
    public String doConvert(ContextHolder jexlContextHolder, JexlContext context, String input) {

        String httpTargetURIAsJexlExpression = createJexlExpression(input);

        LOGGER.info("httpTargetURIAsJexlExpression={}", httpTargetURIAsJexlExpression);

        JexlExpression jexlExpression = jexlEngine.createExpression(httpTargetURIAsJexlExpression);

        return (String) jexlExpression.evaluate(context);
    }

    private static String createJexlExpression(String expressionToBeConverted) {

        if (expressionToBeConverted == null) {
            return null;
        }

        String[] splitEntries = expressionToBeConverted.split("\\$\\{|}");

        LOGGER.debug("splitEntries={}", Arrays.toString(splitEntries));

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0, splitEntriesLength = splitEntries.length; i < splitEntriesLength; i++) {

            boolean isEvenIndex = i % 2 == 0;

            String splitEntry = splitEntries[i];

            if (isEvenIndex) {
                if (i != 0) {
                    stringBuilder.append(" + ");
                }
                stringBuilder.append("'");
                stringBuilder.append(splitEntry);
                stringBuilder.append("'");
                if (i + 1 != splitEntries.length) {
                    stringBuilder.append(" + ");
                }
            } else {
                stringBuilder.append(splitEntry);
            }
        }

        return stringBuilder.toString();

    }


}
