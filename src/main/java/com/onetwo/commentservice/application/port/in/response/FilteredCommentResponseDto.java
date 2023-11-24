package com.onetwo.commentservice.application.port.in.response;

import java.time.Instant;

public record FilteredCommentResponseDto(long commentId,
                                         long postingId,
                                         String userId,
                                         String content,
                                         Instant createdDate) {
}
