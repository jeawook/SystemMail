package com.systemmail.common;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    @Scheduled(cron = "0 1 * * * ?")
    public void send() {

    }
}
