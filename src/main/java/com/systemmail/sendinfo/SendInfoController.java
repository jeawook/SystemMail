package com.systemmail.sendinfo;

import com.systemmail.Service.MailGroupService;
import com.systemmail.Service.MailInfoService;
import com.systemmail.Service.SendInfoService;
import com.systemmail.Service.TemplateService;
import com.systemmail.common.DefaultResponse;
import com.systemmail.common.ResponseCode;
import com.systemmail.domain.entity.*;
import com.systemmail.dto.SendInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity createSendInfo(@RequestBody @Valid SendInfoDto mailDto, Errors errors) {

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
        MailResultInfo mailResultInfo = MailResultInfo.builder().build();
        MailResultDetail mailResultDetail = MailResultDetail.builder().build();
        SendInfo sendInfo = SendInfo.builder()
                .mailInfo(mailInfoById.get())
                .mailTemplate(mailTemplateById.get())
                .sendDate(mailDto.getSendDate())
                .mailGroup(saveMailGroup)
                .build();
        if (mailDto.getMacroData() != null && mailDto.getMacroValues() != null) {
            sendInfo.setMacro(mailDto.getMacroValues().split(","), mailDto.getMacroData().split(","));
        }
        SendInfo createSendInfo = sendInfoService.saveSendInfo(sendInfo);

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
