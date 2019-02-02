package com.gerald.noddus.personcollector.providers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimeRolloverLabelGeneratorTest {
    private final TimeRolloverLabelGenerator rolloverLabelGenerator = new TimeRolloverLabelGenerator();

    @Test
    public void generateRolloverLabel() {
        RolloverLabel label1 = rolloverLabelGenerator.generateRolloverLabel();
        assertThat(label1, notNullValue());
        assertTrue(label1 instanceof TimeRolloverLabel);
    }
}