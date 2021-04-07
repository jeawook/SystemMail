package com.SystemMail.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDetail {

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
