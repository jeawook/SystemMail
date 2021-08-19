package com.systemmail.controller;

import com.systemmail.Service.MailInfoService;
import com.systemmail.common.BaseControllerTest;
import com.systemmail.common.ResponseCode;
import com.systemmail.dto.MailInfoDto;
import com.systemmail.domain.entity.Email;
import com.systemmail.domain.entity.MailInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MailInfoControllerTest extends BaseControllerTest {


    @Autowired
    MailInfoService mailInfoService;
    @Test
    @DisplayName("mailInfo 생성 test")
    public void createMailInfoTest() throws Exception {

        Email mailTo = Email.of("mailto@mail.com","이름");
        Email mailFrom = Email.of("mailfrom@mail.com", "이름");
        Email replyTo = Email.of("replyto@mail.com" ,"이름");
        MailInfoDto mailInfoDto  = MailInfoDto.builder()
                .mailTo(mailTo)
                .mailFrom(mailFrom)
                .replyTo(replyTo)
                .message("기본 발송용").build();

        mockMvc.perform(post("/api/mailInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mailInfoDto)))
                .andDo(print())
                .andExpect(jsonPath("status").value(ResponseCode.CREATED))
                .andExpect(jsonPath("data.id").exists())
                .andExpect(jsonPath("data.message").value(mailInfoDto.getMessage()))
                .andExpect(jsonPath("data.mailTo.address").value(mailInfoDto.getMailTo().getAddress()))
                .andExpect(jsonPath("data.mailFrom.address").value(mailInfoDto.getMailFrom().getAddress()))
                .andExpect(jsonPath("data.replyTo.address").value(mailInfoDto.getReplyTo().getAddress()));
    }

    @Test
    @DisplayName("mailInfo 찾기 by id")
    public void findMailInfoTest() throws Exception {
        Email mailTo = Email.of("mailto@mail.com","이름");
        Email mailFrom = Email.of("mailfrom@mail.com", "이름");
        Email replyTo = Email.of("replyto@mail.com" , "이름");
        MailInfo mailInfo = MailInfo.builder()
                .mailTo(mailTo)
                .mailFrom(mailFrom)
                .replyTo(replyTo)
                .message("테스트").build();
        MailInfo saveMailInfo = mailInfoService.saveMailInfo(mailInfo);

        mockMvc.perform(get("/api/mailInfo/{id}", saveMailInfo.getId())
            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ResponseCode.OK))
                .andExpect(jsonPath("data.id").value(saveMailInfo.getId()))
                .andExpect(jsonPath("data.message").value(saveMailInfo.getMessage()))
                .andExpect(jsonPath("data.mailTo.address").value(saveMailInfo.getMailTo().getAddress()))
                .andExpect(jsonPath("data.mailFrom.address").value(saveMailInfo.getMailFrom().getAddress()))
                .andExpect(jsonPath("data.replyTo.address").value(saveMailInfo.getReplyTo().getAddress()));
    }

}