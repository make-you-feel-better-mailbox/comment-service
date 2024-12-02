package com.onetwo.commentservice.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import onetwo.mailboxcommonconfig.common.SelfValidating;

@Getter
public final class FindCommentDetailCommand extends SelfValidating<FindCommentDetailCommand> {

    @NotNull
    private final Long commentId;

    public FindCommentDetailCommand(Long commentId) {
        this.commentId = commentId;
        this.validateSelf();
    }
}
