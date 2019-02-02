package com.gerald.noddus.personcollector.providers;

import org.springframework.stereotype.Component;

@Component
public class TimeRolloverLabelGenerator implements RolloverLabelGenerator {

    @Override
    public RolloverLabel generateRolloverLabel() {
        return new TimeRolloverLabel();
    }
}
