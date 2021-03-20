package com.SystemMail.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
public class ResultDetails {

    @Id @GeneratedValue
    @Column(name = "result_details_id")
    private Long id;

    private String email;

    private int code;

    private String message;

    @OneToMany
    private SendInfo sendInfo;

}
