package com.systemmail.dto;

import com.systemmail.domain.entity.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
