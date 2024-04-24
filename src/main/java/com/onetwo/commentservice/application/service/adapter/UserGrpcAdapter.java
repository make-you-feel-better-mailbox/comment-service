package com.onetwo.commentservice.application.service.adapter;

import com.onetwo.commentservice.adapter.out.persistence.grpc.UserGrpcClient;
import com.onetwo.commentservice.application.port.out.ReadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGrpcAdapter implements ReadUserPort {

    private final UserGrpcClient userGrpcClient;

    @Override
    public String getUserNickname(String userId) {
        return userGrpcClient.getUserNickname(userId);
    }
}
