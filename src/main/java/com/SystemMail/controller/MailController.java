package com.SystemMail.controller;

import com.SystemMail.Service.MailInfoService;
import com.SystemMail.Service.TemplateService;
import com.SystemMail.common.DefaultResponse;
import com.SystemMail.common.ResponseCode;
import com.SystemMail.dto.MailDto;
import com.SystemMail.entity.MailGroup;
import com.SystemMail.entity.MailInfo;
import com.SystemMail.entity.MailTemplate;
import com.SystemMail.entity.SendInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailInfoService mailInfoService;
    private final TemplateService templateService;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity sendMail(@RequestBody @Valid MailDto mailDto, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(DefaultResponse.builder().status(ResponseCode.AUTHENTICATION_ERROR).errorMessage(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }
        Optional<MailInfo> mailInfoById = mailInfoService.findMailInfoById(mailDto.getMailInfoId());
        Optional<MailTemplate> mailTemplateById = templateService.findMailTemplateById(mailDto.getTemplateId());
        if (mailInfoById.isEmpty() || mailTemplateById.isEmpty()) {
            return ResponseEntity.badRequest().body(DefaultResponse.builder().status(ResponseCode.AUTHENTICATION_ERROR));
        }
        MailGroup mailGroup = modelMapper.map(mailDto, MailGroup.class);
        SendInfo.builder()
                .mailInfo(mailInfoById.get())
                .mailTemplate(mailTemplateById.get())
                .sendDate(mailDto.getSendDate())
                .group(mailGroup)
                .macro(mailDto.getMacro());


    }



}
