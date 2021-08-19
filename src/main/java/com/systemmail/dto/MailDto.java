package com.systemmail.dto;

import com.systemmail.domain.entity.Email;
import com.systemmail.domain.entity.SendInfo;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MailDto {

    private HeaderDto headerDto;

    private Email email;

    private String content;

    private SendInfo sendInfo;

    @Builder
    public MailDto(@NotNull HeaderDto headerDto, @NotNull Email email, @NotNull String content, @NotNull SendInfo sendInfo) {
        this.headerDto = headerDto;
        this.email = email;
        this.content = content;
        this.sendInfo = sendInfo;
    }

    public String getData() {
        return headerDto.getHeader() + content;
    }

}
