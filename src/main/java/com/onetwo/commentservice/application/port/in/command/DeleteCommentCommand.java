package com.onetwo.commentservice.application.port.in.command;

import com.onetwo.commentservice.application.port.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public final class DeleteCommentCommand extends SelfValidating<DeleteCommentCommand> {

    @NotNull
    private final Long commentId;

    @NotEmpty
    private final String userId;

    public DeleteCommentCommand(Long commentId, String userId) {
        this.commentId = commentId;
        this.userId = userId;
        this.validateSelf();
    }
}
