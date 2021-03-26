package com.SystemMail.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDetails {

    @Id @GeneratedValue
    @Column(name = "result_details_id")
    private Long id;

    private String email;

    private int code;

    private String message;

    @ManyToOne
    @JoinColumn(name = "send_info_id")
    private SendInfo sendInfo;

}
