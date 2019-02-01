package com.gerald.noddus.personcollector.providers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RolloverManager.class)
@TestPropertySource(properties = "person.service.rollover.cron=*/1 * * * * *")
@EnableScheduling
public class RolloverManagerTest {
    @MockBean
    private RolloverCapable rolloverCapable;

    @Test
    public void rollOver() throws Exception {
        Thread.sleep(5000);
        verify(rolloverCapable, times(5)).rollOver(any(TimeRolloverLabel.class));
    }
}