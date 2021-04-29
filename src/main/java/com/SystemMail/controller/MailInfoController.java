package com.SystemMail.controller;

import com.SystemMail.Service.MailInfoService;
import com.SystemMail.common.DefaultResponse;
import com.SystemMail.common.ResponseCode;
import com.SystemMail.dto.MailInfoDto;
import com.SystemMail.domain.entity.MailInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/mailInfo")
@RequiredArgsConstructor
public class MailInfoController {

    private final MailInfoService mailInfoService;
    private final ModelMapper modelMapper;
    @PostMapping
    public ResponseEntity createMailInfo(@RequestBody @Valid MailInfoDto mailInfoDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(DefaultResponse.builder().status(ResponseCode.RESOURCE_NOT_FOUND).data(errors.getFieldError()).build());
        }
        MailInfo mailInfo = modelMapper.map(mailInfoDto, MailInfo.class);
        MailInfo saveMailInfo = mailInfoService.saveMailInfo(mailInfo);

        return ResponseEntity.created(linkTo(MailInfoController.class).slash(saveMailInfo.getId()).toUri())
                .body(DefaultResponse.builder().status(ResponseCode.CREATED).data(saveMailInfo).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity findMailInfo(@PathVariable("id") Long id) {
        Optional<MailInfo> optional = mailInfoService.findMailInfoById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(DefaultResponse.builder().status(ResponseCode.OK).data(optional.get()).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMailInfo(@PathVariable("id") Long id, @RequestBody @Valid MailInfoDto mailInfoDto, Errors errors) {
        Optional<MailInfo> optional = mailInfoService.findMailInfoById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(DefaultResponse.builder().status(ResponseCode.OK).data(optional.get()));
    }

}
