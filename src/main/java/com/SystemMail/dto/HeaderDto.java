package com.SystemMail.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
public class HeaderDto {

    private String mailFrom;

    private String mailFromName;

    private String mailTo;

    private String mailToName;

    private String subject;

    private String DKIM;
}
