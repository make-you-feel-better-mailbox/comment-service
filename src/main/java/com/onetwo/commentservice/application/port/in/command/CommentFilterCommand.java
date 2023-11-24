package com.onetwo.commentservice.application.port.in.command;

import com.onetwo.commentservice.application.port.SliceRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

@Getter
public final class CommentFilterCommand extends SliceRequest<CommentFilterCommand> {

    @NotNull
    private final Long postingId;

    private final String userId;

    private final String content;

    private final Instant filterStartDate;

    private final Instant filterEndDate;


    public CommentFilterCommand(Long postingId, String userId, String content, Instant filterStartDate, Instant filterEndDate, Pageable pageable) {
        super(pageable);
        this.postingId = postingId;
        this.userId = userId;
        this.content = content;
        this.filterStartDate = filterStartDate;
        this.filterEndDate = filterEndDate;
        this.validateSelf();
    }
}
