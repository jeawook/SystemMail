package com.SystemMail.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailGroup {

    @Id @GeneratedValue
    @Column(name = "mail_group_id")
    private Long id;

    private String name;

    private String email;

    private String qry;



}
