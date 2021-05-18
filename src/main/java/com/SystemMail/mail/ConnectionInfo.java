package com.SystemMail.mail;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ConnectionInfo {
    int limitCnt;
    int connectionCnt;

    @Builder
    public ConnectionInfo(int limitCnt) {
        this.limitCnt = limitCnt;
        this.connectionCnt = 0;
    }

    private boolean addConnection() {
        if (this.connectionCnt+1 > limitCnt) {
            return false;
        }
        connectionCnt++;
        return true;
    }
}
