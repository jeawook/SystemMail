package com.SystemMail.dto;

import com.SystemMail.domain.entity.Email;
import com.SystemMail.mail.MailHeaderEncoder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

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
        checkArgument(isNotEmpty(mailFrom), "mailFrom이 입력되어야 합니다.");
        checkArgument(isNotEmpty(mailTo), "mailTo가 입력되어야 합니다.");
        checkArgument(isNotEmpty(replyTo), "replyTo가 입력되어야 합니다.");
        checkArgument(isNotEmpty(subject), "subject가 입력되어야 합니다.");
        if (isEmpty(defaultCharset)) {
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
        sb.append(makeHeaderInfo(MailHeaderEncoder.HEADER_FROM,mailFrom.toString()));
        sb.append(makeHeaderInfo(MailHeaderEncoder.HEADER_REPLY_TO, replyTo.toString()));
        sb.append(makeHeaderInfo(MailHeaderEncoder.HEADER_DATE,LocalDateTime.now().toString()));
        if(isNotEmpty(mimeVersion)) {
            sb.append(makeHeaderInfo(MailHeaderEncoder.HEADER_MIME_VERSION, mimeVersion));
        }
        if (isNotEmpty(DKIM)) {
            sb.append(makeHeaderInfo(MailHeaderEncoder.HEADER_DKIM,DKIM));
        }
        return sb.toString();
    }


    private String makeHeaderInfo(String mailHeaderEncoder,String header) {
        return MailHeaderEncoder.create(mailHeaderEncoder, MailHeaderEncoder.encodeHeader(header, defaultCharset));
    }
}

