package com.systemmail.domain.entity;

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

    @Enumerated(EnumType.STRING)
    private ResultStatus resultStatus;

    private String message;

    @ManyToOne
    @JoinColumn(name = "send_info_id")
    private SendInfo sendInfo;

}
