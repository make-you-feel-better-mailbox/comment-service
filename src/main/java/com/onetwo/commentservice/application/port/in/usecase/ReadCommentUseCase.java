package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.CommentFilterCommand;
import com.onetwo.commentservice.application.port.in.command.CountCommentCommand;
import com.onetwo.commentservice.application.port.in.command.FindCommentDetailCommand;
import com.onetwo.commentservice.application.port.in.response.CommentDetailResponseDto;
import com.onetwo.commentservice.application.port.in.response.CountCommentResponseDto;
import com.onetwo.commentservice.application.port.in.response.FilteredCommentResponseDto;
import org.springframework.data.domain.Slice;

public interface ReadCommentUseCase {

    /**
     * Get Detail about comment use case,
     * Get detail data about comment if exist
     *
     * @param findCommentDetailCommand Request comment id
     * @return Detail data about comment
     */
    CommentDetailResponseDto findCommentsDetail(FindCommentDetailCommand findCommentDetailCommand);

    /**
     * Get Filtered comment use case,
     * Get Filtered slice comment data
     *
     * @param commentFilterCommand filter condition and pageable
     * @return content and slice data
     */
    Slice<FilteredCommentResponseDto> filterComment(CommentFilterCommand commentFilterCommand);

    /**
     * Get Target's Comment count use case,
     * get target's comment count on persistence
     *
     * @param countCommentCommand request count target data
     * @return About target's comment count
     */
    CountCommentResponseDto getCommentCount(CountCommentCommand countCommentCommand);
}
