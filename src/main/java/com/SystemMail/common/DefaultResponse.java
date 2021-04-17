package com.SystemMail.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultResponse {
    private int status;

    private Object data;
    private String errorMessage;
    private String errorCode;

    protected DefaultResponse() {}

    @Builder
    protected DefaultResponse(int status, Object data, String errorCode, String errorMessage) {
        this.status = status;
        this.data = data;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
