package com.onetwo.commentservice.application.service.converter;

import com.onetwo.commentservice.application.port.in.response.*;
import com.onetwo.commentservice.application.port.out.dto.UserInfoResponse;
import com.onetwo.commentservice.domain.Comment;

public interface CommentUseCaseConverter {
    RegisterCommentResponseDto commentToRegisterResponseDto(Comment savedComment);

    DeleteCommentResponseDto commentToDeleteResponseDto(Comment comment);

    UpdateCommentResponseDto commentToUpdateResponseDto(boolean isUpdateSuccess);

    CommentDetailResponseDto commentToDetailResponseDto(Comment comment, UserInfoResponse userInfo);

    FilteredCommentResponseDto commentToFilteredResponse(Comment comment, UserInfoResponse userInfo);

    CountCommentResponseDto resultToCountResponseDto(int countComment);
}
