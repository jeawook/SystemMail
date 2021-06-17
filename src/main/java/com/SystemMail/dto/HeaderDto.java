package com.SystemMail.dto;

import com.SystemMail.domain.entity.Email;
import com.SystemMail.mail.MailHeaderEncoder;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
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

    @Builder
    public HeaderDto(Email mailFrom, Email mailTo, Email replyTo, String subject, String mimeVersion, String contentType, String encoding, String defaultCharset, String DKIM) {
        checkArgment(isNotNull(mailFrom), "mailFrom이 입력되어야 합니다.");
        checkArgment(isNotNull(mailTo), "mailTo가 입력되어야 합니다.");
        checkArgment(isNotNull(replyTo), "replyTo가 입력되어야 합니다.");
        checkArgment(isNotNull(subject), "subject가 입력되어야 합니다.");
        if (defaultCharset == null) {
            defaultCharset = "UTF-8";
        }
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.replyTo = replyTo;
        this.subject = subject;
        this.mimeVersion = mimeVersion;
        this.contentType = contentType;
        this.encoding = encoding;
        this.defaultCharset = defaultCharset;
        this.DKIM = DKIM;
    }

    public String getHeader() {
        StringBuffer sb = new StringBuffer();
        sb.append(makeHeaderInfo(MailHeaderEncoder.HEADER_SUBJECT,subject));
        sb.append(makeHeaderInfo(MailHeaderEncoder.HEADER_FROM,mailFrom));
        sb.append(makeHeaderInfo(MailHeaderEncoder.HEADER_REPLY_TO, replyTo));
        sb.append(makeHeaderInfo(MailHeaderEncoder.HEADER_DATE,LocalDateTime.now()));
        if(isNotNull(mimeVersion)) {
            sb.append(makeHeaderInfo(MailHeaderEncoder.HEADER_MIME_VERSION, mimeVersion));
        }
        if (isNotNull(DKIM)) {
            sb.append(makeHeaderInfo(MailHeaderEncoder.Header_))
        }
    }


    private String makeHeaderInfo(MailHeaderEncoder mailHeaderEncoder,String header) {
        return MailHeaderEncoder.create(mailHeaderEncoder, MailHeaderEncoder.encodeHeader(header, defaultCharset));
    }
}

