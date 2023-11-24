package com.onetwo.commentservice.application.service.converter;

import com.onetwo.commentservice.application.port.in.response.DeleteCommentResponseDto;
import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;
import com.onetwo.commentservice.application.port.in.response.UpdateCommentResponseDto;
import com.onetwo.commentservice.domain.Comment;

public interface CommentUseCaseConverter {
    RegisterCommentResponseDto commentToRegisterResponseDto(Comment savedComment);

    DeleteCommentResponseDto commentToDeleteResponseDto(Comment comment);

    UpdateCommentResponseDto commentToUpdateResponseDto(boolean isUpdateSuccess);
}
