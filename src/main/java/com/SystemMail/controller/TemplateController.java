package com.SystemMail.controller;

import com.SystemMail.Service.TemplateService;
import com.SystemMail.common.ResponseMessage;
import com.SystemMail.dto.TemplateDto;
import com.SystemMail.entity.MailTemplate;
import com.SystemMail.reousrce.MailTemplateResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;
    private final ModelMapper modelMapper;

    @PostMapping("/api/template")
    public ResponseEntity createTemplate(@RequestBody @Valid TemplateDto templateDto) {

        MailTemplate mailTemplate = modelMapper.map(templateDto, MailTemplate.class);
        MailTemplate createdMailTemplate = templateService.createTemplate(mailTemplate);
        WebMvcLinkBuilder selfLinkBuilder = linkTo(TemplateController.class).slash(createdMailTemplate.getId());
        URI createUri = selfLinkBuilder.toUri();
        return ResponseEntity.created(createUri).body(createdMailTemplate);

    }
    
}
