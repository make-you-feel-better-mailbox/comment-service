package com.onetwo.commentservice.adapter.in.web.comment.request;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record FilterSliceRequest(@NotNull Long postingId,
                                 String userId,
                                 String content,
                                 Instant filterStartDate,
                                 Instant filterEndDate,
                                 Integer pageNumber,
                                 Integer pageSize,
                                 String sort) {
}
