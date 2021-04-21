package com.SystemMail.dto;

import com.SystemMail.entity.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailInfoDto {

    @NotNull
    private String message;
    @NotNull
    private String mailFrom;
    @NotNull
    private String mailTo;
    @NotNull
    private String replyTo;
}
