package com.onetwo.commentservice.application.service.converter;

import com.onetwo.commentservice.application.port.in.response.*;
import com.onetwo.commentservice.domain.Comment;

public interface CommentUseCaseConverter {
    RegisterCommentResponseDto commentToRegisterResponseDto(Comment savedComment);

    DeleteCommentResponseDto commentToDeleteResponseDto(Comment comment);

    UpdateCommentResponseDto commentToUpdateResponseDto(boolean isUpdateSuccess);

    CommentDetailResponseDto commentToDetailResponseDto(Comment comment);

    FilteredCommentResponseDto commentToFilteredResponse(Comment comment);

    CountCommentResponseDto resultToCountResponseDto(int countComment);
}
