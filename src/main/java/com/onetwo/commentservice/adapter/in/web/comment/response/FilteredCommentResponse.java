package com.onetwo.commentservice.adapter.in.web.comment.response;

import java.time.Instant;

public record FilteredCommentResponse(long commentId,
                                      int category,
                                      long targetId,
                                      String userId,
                                      String content,
                                      Instant createdDate) {
}
