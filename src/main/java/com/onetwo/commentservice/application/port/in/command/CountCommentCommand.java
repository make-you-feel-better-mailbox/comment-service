package com.onetwo.commentservice.application.port.in.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import onetwo.mailboxcommonconfig.common.SelfValidating;

@Getter
public final class CountCommentCommand extends SelfValidating<CountCommentCommand> {

    @NotNull
    private final Integer category;

    @NotNull
    private final Long targetId;

    public CountCommentCommand(Integer category, Long targetId) {
        this.category = category;
        this.targetId = targetId;
        this.validateSelf();
    }
}
