package com.SystemMail.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultInfo {

    @Id @GeneratedValue
    @Column(name = "result_info_id")
    private Long id;

    private int total_cnt;

    private int success;

    private int fail;

    @OneToOne
    @JoinColumn(name = "send_info_id")
    private SendInfo sendInfo;
}
