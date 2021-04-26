package com.SystemMail.dto;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDto {
    @NotNull
    private String user;
    @NotNull
    private String content;
    @NotNull
    private String subject;
    @NotNull
    private String message;

}
