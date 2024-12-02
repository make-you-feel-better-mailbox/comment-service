package com.onetwo.commentservice.application.port.out.dto;

public record UserInfoResponse(
        String userNickname,
        String userProfileImageEndPoint
) {
}
