package com.onetwo.commentservice.adapter.in.web.comment.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegisterCommentRequest(@NotNull Integer category,
                                     @NotNull Long targetId,
                                     @NotEmpty String content) {
}
