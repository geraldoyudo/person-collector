package com.gerald.noddus.personcollector.providers;

import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class TimeRolloverLabelTest {
    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Test
    public void givenTimeStampShouldGenerateLabelProperly(){
        LocalDateTime dateTime = LocalDateTime.parse("2019-01-31T14:23:46.085");
        TimeRolloverLabel rolloverLabel = new TimeRolloverLabel(dateTime);
        collector.checkThat(rolloverLabel.prefix(), equalTo(""));
        collector.checkThat(rolloverLabel.suffix(), equalTo("-2019-01-31T14_23_46_085"));
    }
}