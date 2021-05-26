package com.SystemMail.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailResultDetail {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String email;

    private int code;

    private String message;

    @ManyToOne
    @JoinColumn(name = "send_info_id")
    private SendInfo sendInfo;

}
