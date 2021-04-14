package com.SystemMail.entity;

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
    @Column(name = "id")
    private Long id;

    private int totalCnt;

    private int success;

    private int fail;

    @OneToOne
    @JoinColumn(name = "send_info_id")
    private SendInfo sendInfo;
}
