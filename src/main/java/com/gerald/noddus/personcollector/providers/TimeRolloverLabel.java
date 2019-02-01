package com.gerald.noddus.personcollector.providers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeRolloverLabel implements RolloverLabel {
    private LocalDateTime localDateTime;

    public TimeRolloverLabel() {
        this(LocalDateTime.now());
    }

    public TimeRolloverLabel(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String prefix() {
        return "";
    }

    @Override
    public String suffix() {
        return "-" + localDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
                .replace(".", "_")
                .replace(":","_");
    }
}
