package com.SystemMail.repository;

import com.SystemMail.domain.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class SendInfoRepositoryTest {
    @Autowired
    private SendInfoRepository sendInfoRepository;
    @Autowired
    private MailInfoRepository mailInfoRepository;
    @Autowired
    private MailTemplateRepository templateRepository;
    @Autowired
    private MailGroupRepository mailGroupRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("sendInfo save 테스트")
    public void sendInfoSaveTest() {
        Email mailTo = Email.of("mailTo@mailTo.com", "test");
        Email mailFrom = Email.of("mailFrom@mailFrom.com", "mailFrom");
        Email replyTo = Email.of("replyTo@replyTo.com", "replyTo");
        MailInfo mailInfo = MailInfo.builder().mailTo(mailTo).mailFrom(mailFrom).replyTo(replyTo).message("테스트용").build();
        MailTemplate mailTemplate = MailTemplate.builder().user("생성자").content("본문").subject("제목").message("테스트발송용").build();
        MailGroup mailGroup = MailGroup.builder().name("이름").email(mailTo).build();
        LocalDateTime localDateTime = LocalDateTime.now();
        String[] macroValue = {"박재욱"};
        String[] macroData = {"name"};
        MailResultInfo mailResultInfo = MailResultInfo.builder().build();
        MailResultDetail mailResultDetail = MailResultDetail.builder().build();
        SendInfo sendInfo = SendInfo.builder()
                .mailGroup(mailGroup)
                .sendDate(localDateTime.minusSeconds(10))
                .mailInfo(mailInfo)
                .mailTemplate(mailTemplate)
                .build();
        sendInfo.setMailResultInfo(mailResultInfo);
        sendInfo.addResultDetail(mailResultDetail);
        mailInfoRepository.save(mailInfo);
        templateRepository.save(mailTemplate);
        mailGroupRepository.save(mailGroup);
        SendInfo saveSendInfo = sendInfoRepository.save(sendInfo);
        entityManager.clear();
        assertThat(saveSendInfo.getMailResultInfo().getTotalCnt()).isEqualTo(mailResultInfo.getTotalCnt());


    }

    @Test
    @DisplayName("sendInfo 검색 테스트")
    public void sendInfoFindTest() {
        Email mailTo = Email.of("mailTo@mailTo.com", "test");
        Email mailFrom = Email.of("mailFrom@mailFrom.com", "mailFrom");
        Email replyTo = Email.of("replyTo@replyTo.com", "replyTo");
        MailInfo mailInfo = MailInfo.builder().mailTo(mailTo).mailFrom(mailFrom).replyTo(replyTo).message("테스트용").build();
        MailTemplate mailTemplate = MailTemplate.builder().user("생성자").content("본문").subject("제목").message("테스트발송용").build();
        MailGroup mailGroup = MailGroup.builder().name("이름").email(mailTo).build();
        LocalDateTime localDateTime = LocalDateTime.now();
        String[] macroValue = {"박재욱"};
        String[] macroData = {"name"};
        SendInfo build1 = SendInfo.builder().mailGroup(mailGroup).sendDate(localDateTime.minusSeconds(10)).mailInfo(mailInfo).mailTemplate(mailTemplate).build();
        SendInfo build2 = SendInfo.builder().mailGroup(mailGroup).sendDate(localDateTime.plusSeconds(30)).mailInfo(mailInfo).mailTemplate(mailTemplate).build();
        mailInfoRepository.save(mailInfo);
        templateRepository.save(mailTemplate);
        mailGroupRepository.save(mailGroup);
        SendInfo save1 = sendInfoRepository.save(build1);
        SendInfo save2 = sendInfoRepository.save(build2);
        List<SendInfo> list = sendInfoRepository.findBySendStatusAndSendDateLessThanOrderBySendDateDesc(SendStatus.READY, LocalDateTime.now());
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getId()).isEqualTo(save1.getId());
    }

}