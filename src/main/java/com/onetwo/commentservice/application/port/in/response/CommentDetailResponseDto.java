package com.onetwo.commentservice.application.port.in.response;

import java.time.Instant;

public record CommentDetailResponseDto(long commentId,
                                       int category,
                                       long targetId,
                                       String userId,
                                       String userNickname,
                                       String userProfileImageEndPoint,
                                       String content,
                                       Instant createdDate) {
}
