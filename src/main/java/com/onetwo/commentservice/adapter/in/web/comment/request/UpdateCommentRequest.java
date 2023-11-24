package com.onetwo.commentservice.adapter.in.web.comment.request;

import jakarta.validation.constraints.NotEmpty;

public record UpdateCommentRequest(@NotEmpty String content) {
}
