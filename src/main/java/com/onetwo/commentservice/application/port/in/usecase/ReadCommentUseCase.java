package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.FindCommentDetailCommand;
import com.onetwo.commentservice.application.port.in.response.CommentDetailResponseDto;

public interface ReadCommentUseCase {

    /**
     * Get Detail about comment use case,
     * Get detail data about comment if exist
     *
     * @param findCommentDetailCommand Request comment id
     * @return Detail data about comment
     */
    CommentDetailResponseDto findCommentsDetail(FindCommentDetailCommand findCommentDetailCommand);
}
