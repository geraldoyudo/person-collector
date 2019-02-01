package com.gerald.noddus.personcollector.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RolloverManager {
    private RolloverCapable rolloverCapable;

    @Autowired
    public void setRolloverCapable(RolloverCapable rolloverCapable) {
        this.rolloverCapable = rolloverCapable;
    }

    @Scheduled(cron = "${person.service.rollover.cron}")
    public void rollOver(){
        rolloverCapable.rollOver(new TimeRolloverLabel());
    }
}
