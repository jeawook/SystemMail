package com.systemmail.dto;

import lombok.*;

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
