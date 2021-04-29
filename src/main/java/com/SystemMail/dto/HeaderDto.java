package com.SystemMail.dto;

import com.SystemMail.domain.entity.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HeaderDto {
    private Email mailFrom;

    private Email mailTo;

    private Email replyTo;

    private String subject;

    private String mimeVersion;

    private String contentType;

    private String encoding;

    private String defaultCharset;

    private String DKIM;
}
