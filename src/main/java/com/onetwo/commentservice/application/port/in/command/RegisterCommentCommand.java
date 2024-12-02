package com.onetwo.commentservice.application.port.in.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import onetwo.mailboxcommonconfig.common.SelfValidating;

@Getter
public final class RegisterCommentCommand extends SelfValidating<RegisterCommentCommand> {

    @NotEmpty
    private final String userId;

    @NotNull
    private Integer category;

    @NotNull
    private Long targetId;

    @NotEmpty
    private final String content;

    public RegisterCommentCommand(String userId, Integer category, Long targetId, String content) {
        this.userId = userId;
        this.category = category;
        this.targetId = targetId;
        this.content = content;
        this.validateSelf();
    }
}
