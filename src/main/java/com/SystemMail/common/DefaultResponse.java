package com.SystemMail.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Getter
@Setter
public class DefaultResponse {
    private Integer status;

    private Object data;
    private String errorMessage;
    private String errorCode;

    protected DefaultResponse() {}

    @Builder
    protected DefaultResponse(Integer status, Object data, String errorCode, String errorMessage) {
        checkNotNull(status, "상태 코드가 입력 되어야 합니다.");
        this.status = status;
        this.data = data;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
