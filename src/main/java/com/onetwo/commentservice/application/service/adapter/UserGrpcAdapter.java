package com.onetwo.commentservice.application.service.adapter;

import com.onetwo.commentservice.adapter.out.persistence.grpc.UserGrpcClient;
import com.onetwo.commentservice.application.port.out.ReadUserPort;
import com.onetwo.commentservice.application.port.out.dto.UserInfoResponse;
import com.onetwo.rpc.user.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGrpcAdapter implements ReadUserPort {

    private final UserGrpcClient userGrpcClient;

    @Override
    public UserInfoResponse getUserInfo(String userId) {
        UserInfo userInfo = userGrpcClient.getUserInfo(userId);

        return new UserInfoResponse(userInfo.getUserNickname(), userInfo.getProfileImageEndPoint());
    }
}
