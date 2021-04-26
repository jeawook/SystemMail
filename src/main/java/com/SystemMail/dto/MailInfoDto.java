package com.SystemMail.dto;

import com.SystemMail.entity.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailInfoDto {

    @NotNull
    private String message;
    @NotNull
    private Email mailFrom;
    @NotNull
    private Email mailTo;
    @NotNull
    private Email replyTo;
}
