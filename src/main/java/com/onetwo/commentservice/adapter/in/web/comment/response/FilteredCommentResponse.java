package com.onetwo.commentservice.adapter.in.web.comment.response;

import java.time.Instant;

public record FilteredCommentResponse(long commentId,
                                      long postingId,
                                      String userId,
                                      String content,
                                      Instant createdDate) {
}
