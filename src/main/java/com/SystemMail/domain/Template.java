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
public class Template {

    @Id @GeneratedValue
    @Column(name = "template_id")
    private Long id;

    private String content;

    private String user;

    private String subject;
}
