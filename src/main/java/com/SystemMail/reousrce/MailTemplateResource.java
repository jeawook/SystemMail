package com.SystemMail.reousrce;

import com.SystemMail.controller.TemplateController;
import com.SystemMail.entity.MailTemplate;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class MailTemplateResource extends EntityModel<MailTemplate> {
    public MailTemplateResource(MailTemplate content, Link... links) {
        super(content, links);
        add(linkTo(methodOn(TemplateController.class)).slash(content.getId()).withSelfRel());
    }
}
