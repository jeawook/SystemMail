package com.SystemMail.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class DefaultResponseTest {

    @Test
    public void createDefaultResponseTest() {
        Object test = new Object();
        DefaultResponse defaultResponse = DefaultResponse.builder().status(ResponseCode.OK).data(test).build();
        assertThat(defaultResponse.getStatus()).isEqualTo(ResponseCode.OK);
        assertThat(defaultResponse.getData()).isEqualTo(test);
    }
    @Test
    public void createDefaultResponseErrorTest() {

        Throwable throwable = assertThrows(NullPointerException.class,() ->{DefaultResponse.builder().build();});
        assertThat(throwable.getMessage()).isEqualTo("상태 코드가 입력 되어야 합니다.");

    }

}