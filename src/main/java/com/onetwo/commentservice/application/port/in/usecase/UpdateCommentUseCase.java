package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.UpdateCommentCommand;
import com.onetwo.commentservice.application.port.in.response.UpdateCommentResponseDto;

public interface UpdateCommentUseCase {

    /**
     * Update comment use case,
     * update comment data on persistence
     *
     * @param updateCommentCommand request update comment id and request user id and update data
     * @return Boolean about update comment success
     */
    UpdateCommentResponseDto updateComment(UpdateCommentCommand updateCommentCommand);
}
