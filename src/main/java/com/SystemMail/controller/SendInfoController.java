package com.SystemMail.controller;

import com.SystemMail.Service.MailGroupService;
import com.SystemMail.Service.MailInfoService;
import com.SystemMail.Service.SendInfoService;
import com.SystemMail.Service.TemplateService;
import com.SystemMail.common.DefaultResponse;
import com.SystemMail.common.ResponseCode;
import com.SystemMail.dto.MailDto;
import com.SystemMail.entity.MailGroup;
import com.SystemMail.entity.MailInfo;
import com.SystemMail.entity.MailTemplate;
import com.SystemMail.entity.SendInfo;
import com.SystemMail.repository.MailGroupRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/sendInfo")
@RequiredArgsConstructor
public class SendInfoController {

    private final MailInfoService mailInfoService;
    private final TemplateService templateService;
    private final SendInfoService sendInfoService;
    private final MailGroupService mailGroupService;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity createSendInfo(@RequestBody @Valid MailDto mailDto, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(DefaultResponse.builder().status(ResponseCode.AUTHENTICATION_ERROR).errorMessage(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()));
        }
        Optional<MailInfo> mailInfoById = mailInfoService.findMailInfoById(mailDto.getMailInfoId());
        Optional<MailTemplate> mailTemplateById = templateService.findMailTemplateById(mailDto.getTemplateId());
        if (mailInfoById.isEmpty() || mailTemplateById.isEmpty()) {
            return ResponseEntity.badRequest().body(DefaultResponse.builder().status(ResponseCode.AUTHENTICATION_ERROR));
        }
        MailGroup mailGroup = modelMapper.map(mailDto, MailGroup.class);
        MailGroup saveMailGroup = mailGroupService.createMailGroup(mailGroup);
        SendInfo sendInfo = SendInfo.builder()
                .mailInfo(mailInfoById.get())
                .mailTemplate(mailTemplateById.get())
                .sendDate(mailDto.getSendDate())
                .group(saveMailGroup)
                .macro(mailDto.getMacro())
                .build();
        SendInfo createSendInfo = sendInfoService.createSendInfo(sendInfo);

        return ResponseEntity.created(linkTo(SendInfoController.class).slash(createSendInfo.getId()).toUri())
                .body(DefaultResponse.builder().status(ResponseCode.CREATED).data(createSendInfo).build());

    }

    @GetMapping("/{id}")
    public ResponseEntity findSendInfo(@PathVariable("id") Long id) {
        Optional<SendInfo> sendInfo = sendInfoService.findSendInfo(id);
        if (sendInfo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(DefaultResponse.builder().status(ResponseCode.OK).data(sendInfo.get()).build());
    }



}