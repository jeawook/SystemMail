package com.SystemMail.controller;

import com.SystemMail.common.BaseControllerTest;
import com.SystemMail.common.ResponseCode;
import com.SystemMail.dto.MailInfoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


class MailInfoControllerTest extends BaseControllerTest {


    @Test
    @DisplayName("mailInfo 생성 test")
    public void createMailInfoTest() throws Exception {

        MailInfoDto mailInfoDto  = MailInfoDto.builder()
                .mailTo("mailto@mail.com")
                .mailFrom("mailfrom@mail.com")
                .replyTo("replyto@mail.com")
                .message("기본 발송용").build();

        mockMvc.perform(post("/api/mailInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mailInfoDto)))
                .andDo(print())
                .andExpect(jsonPath("status").value(ResponseCode.CREATED))
                .andExpect(jsonPath("data.id").exists())
                .andExpect(jsonPath("data.mailTo").value(mailInfoDto.getMailTo()))
                .andExpect(jsonPath("data.mailFrom").value(mailInfoDto.getMailFrom()))
                .andExpect(jsonPath("data.replyTo").value(mailInfoDto.getReplyTo()));

    }

}