package com.SystemMail.controller;

import com.SystemMail.common.BaseControllerTest;
import com.SystemMail.dto.TemplateDto;
import com.SystemMail.repository.TemplateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Rollback(value = false)
class TemplateControllerTest extends BaseControllerTest {



    @Autowired
    TemplateRepository templateRepository;

    @Test
    @DisplayName("템플릿 생성 테스트")
    public void createTemplate() throws Exception{
        TemplateDto templateDto = TemplateDto.builder()
                .subject("메일 제목")
                .content("메일 본문")
                .message("템플릿 내용")
                .build();
        mockMvc.perform(post("/api/template")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(templateDto.toString()))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").exists())
        .andExpect(jsonPath("subject").value("메일 제목"))
        .andExpect(jsonPath("content").value("메일 본문"))
        .andExpect(jsonPath("message").value("템플릿 내용"))
        .andExpect(jsonPath("createdDate").exists())
        .andExpect(jsonPath("lostModifiedDate").exists());
        ;
    }

}