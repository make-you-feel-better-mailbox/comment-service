package com.onetwo.commentservice.adapter.in.web.config;

import com.onetwo.commentservice.adapter.out.persistence.grpc.UserGrpcClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class GrpcTestConfig {

    @Bean
    @Primary
    public UserGrpcClient userGrpcClient() {
        return new UserGrpcClient() {

            @Override
            public String getUserNickname(String userId) {
                return "test";
            }
        };
    }
}
