package com.systemmail.mailtemplate;

import com.systemmail.Service.TemplateService;
import com.systemmail.common.DefaultResponse;
import com.systemmail.common.ResponseCode;
import com.systemmail.dto.TemplateDto;
import com.systemmail.domain.entity.MailTemplate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/template")
@RequiredArgsConstructor
public class MailTemplateController {

    private final TemplateService templateService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity createTemplate(@RequestBody @Valid TemplateDto templateDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(DefaultResponse.builder().status(ResponseCode.RESOURCE_NOT_FOUND).errorMessage(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage()).build());
        }
        MailTemplate mailTemplate = modelMapper.map(templateDto, MailTemplate.class);
        MailTemplate createdMailTemplate = templateService.saveMailTemplate(mailTemplate);
        return ResponseEntity.created(linkTo(MailTemplateController.class).slash(createdMailTemplate.getId()).toUri()).body(DefaultResponse.builder().status(ResponseCode.OK).data(createdMailTemplate).build());
    }
    @GetMapping("/{id}")
    public ResponseEntity getTemplate(@PathVariable("id") Long id) {
        Optional<MailTemplate> optionalTemplate = templateService.findMailTemplateById(id);
        if (optionalTemplate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(DefaultResponse.builder().status(ResponseCode.OK).data(optionalTemplate).build());
    }

    @GetMapping
    public ResponseEntity getTemplates(@PageableDefault(size = 5) Pageable pageable) {
        Page<MailTemplate> mailTemplate = templateService.findMailTemplate(pageable);
        return ResponseEntity.ok(DefaultResponse.builder().status(ResponseCode.OK).data(mailTemplate).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTemplate(@PathVariable("id") Long id, @RequestBody @Valid TemplateDto templateDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(DefaultResponse.builder().status(ResponseCode.RESOURCE_NOT_FOUND).data(errors).build());
        }
        Optional<MailTemplate> optional = templateService.findMailTemplateById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        MailTemplate mailTemplate = optional.get();
        this.modelMapper.map(templateDto,mailTemplate);
        MailTemplate saveMailTemplate = templateService.saveMailTemplate(mailTemplate);

        return ResponseEntity.ok().body(DefaultResponse.builder().status(ResponseCode.OK).data(saveMailTemplate).build());

    }

    
}
