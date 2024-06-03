package com.onetwo.commentservice.application.port.out;

import com.onetwo.commentservice.application.port.out.dto.UserInfoResponse;

public interface ReadUserPort {
    UserInfoResponse getUserInfo(String userId);
}
