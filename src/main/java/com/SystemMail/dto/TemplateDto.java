package com.SystemMail.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class TemplateDto {
    private String content;
    private String subject;
    private String message;
}
