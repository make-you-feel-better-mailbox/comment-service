package com.onetwo.commentservice.application.service.converter;

import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;
import com.onetwo.commentservice.domain.Comment;

public interface CommentUseCaseConverter {
    RegisterCommentResponseDto commentToRegisterResponseDto(Comment savedComment);
}
