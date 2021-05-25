package com.SystemMail.controller;

import com.SystemMail.common.BaseControllerTest;
import com.SystemMail.common.ResponseCode;
import com.SystemMail.dto.SendInfoDto;
import com.SystemMail.domain.entity.Email;
import com.SystemMail.domain.entity.MailInfo;
import com.SystemMail.domain.entity.MailTemplate;
import com.SystemMail.repository.MailInfoRepository;
import com.SystemMail.repository.MailTemplateRepository;
import com.SystemMail.repository.SendInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SendInfoControllerTest extends BaseControllerTest {

    @Autowired
    private SendInfoRepository sendInfoRepository;
    @Autowired
    private MailTemplateRepository mailTemplateRepository;
    @Autowired
    private MailInfoRepository mailInfoRepository;

    @BeforeEach

    @Test
    @DisplayName("sendInfo 생성 테스트")
    public void createSendInfo() throws Exception{
        MailTemplate mailTemplate = MailTemplate.builder()
                .message("테스트메일")
                .subject("메일 제목")
                .content("메일 본문")
                .user("admin")
                .build();

        Email mailTo = Email.of("mailto@test.com", "mailTo");
        Email mailFrom = Email.of("mailFrom@test.com", "mailFrom");
        Email replyTo = Email.of("replyTo@test.com", "replyTo");
        MailInfo mailInfo = MailInfo.builder()
                .message("테스트메일")
                .mailTo(mailTo)
                .mailFrom(mailFrom)
                .replyTo(replyTo)
                .build();

        MailTemplate saveMailTemplate = mailTemplateRepository.save(mailTemplate);
        MailInfo saveMailInfo = mailInfoRepository.save(mailInfo);
        Email email = Email.of("test@test.com", "test");
        SendInfoDto mailDto = SendInfoDto.builder()
                .mailInfoId(saveMailInfo.getId())
                .templateId(saveMailTemplate.getId())
                .email(email)
                .sendDate(LocalDateTime.now())
                .build();

        mockMvc.perform(post("/api/sendInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(mailDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("status").value(ResponseCode.CREATED))
                .andExpect(jsonPath("data.id").exists());
    }

}