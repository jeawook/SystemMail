package com.SystemMail.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class MailDTO {

    private Long mailId;

    private String domain;

    private String mailFrom;

    private String mailFromName;

    private String mailTo;

    private String mailToName;

    private String returnPath;

    private String replyTo;

    private String encoding;

    private String subject;

    private String content;



}
