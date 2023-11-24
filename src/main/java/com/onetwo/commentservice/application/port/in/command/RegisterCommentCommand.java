package com.onetwo.commentservice.application.port.in.command;

import com.onetwo.commentservice.application.port.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public final class RegisterCommentCommand extends SelfValidating<RegisterCommentCommand> {

    @NotEmpty
    private final String userId;

    @NotNull
    private final Long postingId;

    @NotEmpty
    private final String content;

    public RegisterCommentCommand(String userId, Long postingId, String content) {
        this.userId = userId;
        this.postingId = postingId;
        this.content = content;
        this.validateSelf();
    }
}
