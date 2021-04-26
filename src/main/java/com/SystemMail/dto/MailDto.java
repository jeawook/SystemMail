package com.SystemMail.dto;

import com.SystemMail.entity.Email;
import com.SystemMail.entity.Macro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {

    @NotNull
    private Long mailInfoId;
    @NotNull
    private Long templateId;

    private String name;
    @NotNull
    private Email email;
    @NotNull
    private LocalDateTime sendDate;

    private Macro macro;

}
