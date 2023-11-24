package com.onetwo.commentservice.adapter.in.web.comment.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegisterCommentRequest(@NotNull Long postingId,
                                     @NotEmpty String content) {
}
