package com.SystemMail.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HeaderDto {

    private String mailFrom;

    private String mailFromName;

    private String mailTo;

    private String mailToName;

    private String subject;

    private String DKIM;
}
