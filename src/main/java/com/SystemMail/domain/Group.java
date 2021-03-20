package com.SystemMail.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
public class Group {

    @Id @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    private String name;

    private String email;

    private String qry;



}
