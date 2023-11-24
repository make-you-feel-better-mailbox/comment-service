package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;

public interface RegisterCommentUseCase {

    /**
     * Register comment use case,
     * register comment data to persistence
     *
     * @param registerCommentCommand data about register comment with user id and posting id
     * @return Boolean about register success
     */
    RegisterCommentResponseDto registerComment(RegisterCommentCommand registerCommentCommand);
}
