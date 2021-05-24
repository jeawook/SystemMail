package com.SystemMail.dto;

import com.SystemMail.domain.entity.Email;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;

@Data
@Builder
public class MailDto {

    @NotNull
    private HeaderDto headerDto;

    @NotNull
    private Email email;

    @NotNull
    private String content;

}
