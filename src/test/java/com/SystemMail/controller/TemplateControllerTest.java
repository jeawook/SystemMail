package com.SystemMail.controller;

import com.SystemMail.common.BaseControllerTest;
import com.SystemMail.common.ResponseCode;
import com.SystemMail.dto.TemplateDto;
import com.SystemMail.entity.MailTemplate;
import com.SystemMail.repository.TemplateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Rollback(value = false)
class TemplateControllerTest extends BaseControllerTest {



    @Autowired
    TemplateRepository templateRepository;

    static final String URL = "/api/template";
    @Test
    @DisplayName("템플릿 생성 테스트")
    public void createTemplate() throws Exception{
        TemplateDto templateDto = TemplateDto.builder()
                .subject("메일 제목")
                .content("메일 본문")
                .user("user")
                .message("템플릿 내용")
                .build();
        mockMvc.perform(post("/api/template")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(templateDto)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("status").value(ResponseCode.OK))
        .andExpect(jsonPath("data.id").exists())
        .andExpect(jsonPath("data.subject").value("메일 제목"))
        .andExpect(jsonPath("data.content").value("메일 본문"))
        .andExpect(jsonPath("data.message").value("템플릿 내용"))
        .andExpect(jsonPath("data.createdDate").exists())
        .andExpect(jsonPath("data.lostModifiedDate").exists());
    }

    @Test
    @DisplayName("mailTemplate 조회 테스트")
    public void findMailTemplateTest() throws Exception {
        MailTemplate mailTemplate = MailTemplate.builder()
                .content("메일 본문")
                .message("템플릿 내용")
                .user("user")
                .subject("메일 제목")
                .build();
        MailTemplate createdMailTemplate = templateRepository.save(mailTemplate);
        mockMvc.perform(get("/api/template/{id}", createdMailTemplate.getId())
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value(ResponseCode.OK))
        .andExpect(jsonPath("data.id").value(createdMailTemplate.getId()))
        .andExpect(jsonPath("data.content").value(createdMailTemplate.getContent()))
        .andExpect(jsonPath("data.message").value(mailTemplate.getMessage()))
        .andExpect(jsonPath("data.subject").value(mailTemplate.getSubject()));

    }

    @Test
    @DisplayName("mailTemplate paging 테스트")
    public void findMailTemplatePagingTest() throws Exception {

        String user = "사용자1";
        String content = "메일 본문";
        String subject = "메일 제목";
        String message = "발송용";
        MailTemplate mailTemplate = MailTemplate.builder()
                .user(user)
                .content(content)
                .subject(subject)
                .message(message)
                .build();
        for (int i = 0; i < 10; i++) {
            templateRepository.save(mailTemplate);
        }
        mockMvc.perform(get("/api/template").param("page","0").param("size", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(200))
                .andExpect(jsonPath("data.content").exists())
                .andExpect(jsonPath("data.size").value(2));
    }

    @Test
    @DisplayName("mailTemplate update 테스트")
    public void updateMailTemplateTest() throws Exception {
        String user = "사용자1";

        MailTemplate mailTemplate = MailTemplate.builder()
                .user(user)
                .content("메일 본문")
                .subject("메일 제목")
                .message("발송용")
                .build();
        MailTemplate save = templateRepository.save(mailTemplate);

        TemplateDto templateDto = this.modelMapper.map(save,TemplateDto.class);
        templateDto.setSubject("update 제목");
        templateDto.setContent("update 본문");
        mockMvc.perform(put("/api/template/{id}",save.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(templateDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(ResponseCode.OK))
                .andExpect(jsonPath("data.id").value(save.getId()))
                .andExpect(jsonPath("data.subject").value(templateDto.getSubject()))
                .andExpect(jsonPath("data.message").value(templateDto.getMessage()))
                .andExpect(jsonPath("data.content").value(templateDto.getContent()));
    }

}