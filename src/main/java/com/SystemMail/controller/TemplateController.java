package com.SystemMail.controller;

import com.SystemMail.Service.TemplateService;
import com.SystemMail.common.DefaultResponse;
import com.SystemMail.common.ResponseCode;
import com.SystemMail.dto.TemplateDto;
import com.SystemMail.entity.MailTemplate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;
    private final ModelMapper modelMapper;

    @PostMapping("/api/template")
    public ResponseEntity createTemplate(@RequestBody @Valid TemplateDto templateDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(DefaultResponse.builder().status(ResponseCode.RESOURCE_NOT_FOUND).data(errors).build());
        }
        MailTemplate mailTemplate = modelMapper.map(templateDto, MailTemplate.class);
        MailTemplate createdMailTemplate = templateService.saveMailTemplate(mailTemplate);
        return ResponseEntity.created(linkTo(this).slash(createdMailTemplate.getId()).toUri()).body(DefaultResponse.builder().status(ResponseCode.OK).data(createdMailTemplate).build());
    }
    @GetMapping("/api/template/{id}")
    public ResponseEntity getTemplate(@PathVariable("id") Long id) {
        Optional<MailTemplate> optionalTemplate = templateService.findOne(id);
        if (optionalTemplate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(DefaultResponse.builder().status(ResponseCode.OK).data(optionalTemplate).build());
    }

    @GetMapping("/api/template")
    public ResponseEntity getTemplates(@PageableDefault(size = 5) Pageable pageable) {
        Page<MailTemplate> mailTemplate = templateService.findMailTemplate(pageable);
        return ResponseEntity.ok(DefaultResponse.builder().status(ResponseCode.OK).data(mailTemplate).build());
    }

    @PutMapping("/api/template/{id}")
    public ResponseEntity updateTemplate(@PathVariable("id") Long id, @RequestBody @Valid TemplateDto templateDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(DefaultResponse.builder().status(ResponseCode.RESOURCE_NOT_FOUND).data(errors).build());
        }
        Optional<MailTemplate> optional = templateService.findOne(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        MailTemplate mailTemplate = optional.get();
        this.modelMapper.map(templateDto,mailTemplate);
        MailTemplate saveMailTemplate = templateService.saveMailTemplate(mailTemplate);

        return ResponseEntity.ok().body(DefaultResponse.builder().status(ResponseCode.OK).data(saveMailTemplate).build());

    }

    
}
