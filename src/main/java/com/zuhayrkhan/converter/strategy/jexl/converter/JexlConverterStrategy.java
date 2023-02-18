package com.zuhayrkhan.converter.strategy.jexl.converter;

import com.zuhayrkhan.context.CommonDatesPopulator;
import com.zuhayrkhan.context.ConverterStrategy;
import com.zuhayrkhan.converter.strategy.jexl.context.JexlContextBuilder;
import com.zuhayrkhan.converter.strategy.jexl.context.JexlContextHolder;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.util.Arrays;

public class JexlConverterStrategy implements ConverterStrategy<JexlContextHolder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JexlConverterStrategy.class);

    private final Clock clock;

    private final JexlEngine jexlEngine = new JexlBuilder().cache(512).strict(true).silent(false).create();

    public JexlConverterStrategy(Clock clock) {
        this.clock = clock;
    }

    @Override
    public JexlContextHolder createContextHolder() {
        return new JexlContextBuilder()
                .populateFrom(context -> context.addIntoContext(CLOCK_IN_CONTEXT, clock))
                .populateFrom(CommonDatesPopulator::addCommonDatesToContext)
                .build();
    }

    @Override
    public String doConvert(JexlContextHolder jexlContextHolder, String input) {

        String httpTargetURIAsJexlExpression = createJexlExpression(input);

        LOGGER.info("httpTargetURIAsJexlExpression={}", httpTargetURIAsJexlExpression);

        JexlExpression jexlExpression = jexlEngine.createExpression(httpTargetURIAsJexlExpression);

        return (String) jexlExpression.evaluate(jexlContextHolder.unWrapContext());
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
