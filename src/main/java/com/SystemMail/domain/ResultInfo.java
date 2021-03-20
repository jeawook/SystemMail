package com.SystemMail.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
public class ResultInfo {

    @Id @GeneratedValue
    @Column(name = "result_info_id")
    private Long id;

    private int total_cnt;

    private int success;

    private int fail;

    private SendInfo sendInfo;
}
