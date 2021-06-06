package com.SystemMail.config;

import com.SystemMail.Service.SendInfoService;
import com.SystemMail.domain.entity.SendInfo;
import com.SystemMail.domain.entity.SendStatus;
import com.SystemMail.mail.MailProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class Scheduler {

    private final SendStatus sendStatus = SendStatus.READY;
    private final MailProcessor mailScheduler;
    private final SendInfoService sendInfoService;
    @Scheduled(cron = "*/10 * * * * *")
    public void mailScheduler() {
        List<SendInfo> sendList = sendInfoService.findSendList(sendStatus, LocalDateTime.now());
        if (sendList.size() > 0) {
            mailScheduler.send(sendList);
        }
    }
}
