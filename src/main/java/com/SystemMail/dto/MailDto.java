package com.SystemMail.dto;

import com.SystemMail.domain.entity.Email;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;

@Data
public class MailDto {

    private HeaderDto headerDto;

    private Email email;

    private String content;

}
