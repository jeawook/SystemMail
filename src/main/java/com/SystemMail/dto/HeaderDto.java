package com.SystemMail.dto;

import com.SystemMail.domain.entity.Email;
import com.SystemMail.mail.MailHeaderEncoder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

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

    public String getHeaderInfo() {
        return MailHeaderEncoder.create(MailHeaderEncoder.HEADER_SUBJECT, MailHeaderEncoder.encodeHeader(subject, defaultCharset)) +
                MailHeaderEncoder.create(MailHeaderEncoder.HEADER_FROM, MailHeaderEncoder.encodeHeader(mailFrom.toString(), defaultCharset)) +
                MailHeaderEncoder.create(MailHeaderEncoder.HEADER_TO, MailHeaderEncoder.encodeHeader(mailTo.toString(), defaultCharset)) +
                MailHeaderEncoder.create(MailHeaderEncoder.HEADER_REPLY_TO, MailHeaderEncoder.encodeHeader(replyTo.toString(), defaultCharset)) +
                MailHeaderEncoder.create(MailHeaderEncoder.HEADER_DATE, MailHeaderEncoder.encodeHeader(LocalDateTime.now().toString(), defaultCharset));
    }
}
