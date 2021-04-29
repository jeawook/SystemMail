package com.SystemMail.dto;

import com.SystemMail.domain.entity.Email;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
public class MailDto {

    private HeaderDto headerDto;

    private Email email;

    private String content;

    private HashMap<String, String> macro = new HashMap<>();


}
