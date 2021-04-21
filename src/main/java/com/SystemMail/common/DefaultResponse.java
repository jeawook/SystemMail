package com.SystemMail.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
        checkArgument(status > 0, "상태 코드가 입력 되어야 합니다."  );
        this.status = status;
        this.data = data;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
