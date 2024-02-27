package com.onetwo.commentservice.application.port.in.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import onetwo.mailboxcommonconfig.common.SelfValidating;

@Getter
public final class UpdateCommentCommand extends SelfValidating<UpdateCommentCommand> {

    @NotNull
    private final Long commentId;

    @NotEmpty
    private final String userId;

    @NotEmpty
    private final String content;

    public UpdateCommentCommand(Long commentId, String userId, String content) {
        this.commentId = commentId;
        this.userId = userId;
        this.content = content;
        this.validateSelf();
    }
}
