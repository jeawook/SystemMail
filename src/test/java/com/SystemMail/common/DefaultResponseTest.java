package com.SystemMail.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class DefaultResponseTest {

    @Test
    @DisplayName("DefaultResponse 생성 테스트")
    public void createDefaultResponseTest() {
        Object test = new Object();
        DefaultResponse defaultResponse = DefaultResponse.builder().status(ResponseCode.OK).data(test).build();
        assertThat(defaultResponse.getStatus()).isEqualTo(ResponseCode.OK);
        assertThat(defaultResponse.getData()).isEqualTo(test);
    }
    @Test
    @DisplayName("error throw 테스트")
    public void createDefaultResponseErrorTest() {

        Throwable throwable = assertThrows(IllegalArgumentException.class,() ->{DefaultResponse.builder().build();});
        assertThat(throwable.getMessage()).isEqualTo("상태 코드가 입력 되어야 합니다.");

    }

}