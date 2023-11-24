package com.onetwo.commentservice.application.port.in.command;

import com.onetwo.commentservice.application.port.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public final class FindCommentDetailCommand extends SelfValidating<FindCommentDetailCommand> {

    @NotNull
    private final Long commentId;

    public FindCommentDetailCommand(Long commentId) {
        this.commentId = commentId;
        this.validateSelf();
    }
}
