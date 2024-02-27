package com.onetwo.commentservice.application.port.in.response;

import java.time.Instant;

public record FilteredCommentResponseDto(long commentId,
                                         int category,
                                         long targetId,
                                         String userId,
                                         String content,
                                         Instant createdDate) {
}
