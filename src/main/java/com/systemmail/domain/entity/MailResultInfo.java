package com.systemmail.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
public class MailResultInfo {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    private int totalCnt;

    private int success;

    private int fail;

    @OneToOne
    @JoinColumn(name = "send_info_id")
    private SendInfo sendInfo;

    @Builder
    public MailResultInfo() {
        totalCnt = 1;
        success = 0;
        fail = 0;
    }

    public void setSendInfo(SendInfo sendInfo) {
        this.sendInfo = sendInfo;
    }

    public void addSuccess(){
        success++;
    }

    public void addFail() {
        fail++;
    }
}
