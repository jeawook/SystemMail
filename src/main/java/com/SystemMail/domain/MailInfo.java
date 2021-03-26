package com.SystemMail.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {

    @Id @GeneratedValue
    @Column(name = "mail_info_id")
    private Long id;

    private String mailFrom;
    private String mailTo;
    private String replyTo;

    private String header;

    private String content;


}
