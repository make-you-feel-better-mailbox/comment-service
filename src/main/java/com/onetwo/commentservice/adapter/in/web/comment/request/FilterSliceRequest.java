package com.onetwo.commentservice.adapter.in.web.comment.request;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record FilterSliceRequest(@NotNull Integer category,
                                 @NotNull Long targetId,
                                 String userId,
                                 String content,
                                 Instant filterStartDate,
                                 Instant filterEndDate,
                                 Integer pageNumber,
                                 Integer pageSize,
                                 String sort) {
}
