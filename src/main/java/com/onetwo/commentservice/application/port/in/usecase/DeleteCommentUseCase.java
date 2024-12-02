package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.DeleteCommentCommand;
import com.onetwo.commentservice.application.port.in.response.DeleteCommentResponseDto;

public interface DeleteCommentUseCase {

    /**
     * Delete comment use case,
     * delete comment data to persistence
     *
     * @param deleteCommentCommand request delete comment id and request user id
     * @return Boolean about delete comment success
     */
    DeleteCommentResponseDto deleteComment(DeleteCommentCommand deleteCommentCommand);
}
