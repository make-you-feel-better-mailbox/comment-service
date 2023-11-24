package com.onetwo.commentservice.adapter.in.web.comment.mapper;

import com.onetwo.commentservice.adapter.in.web.comment.request.RegisterCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.response.RegisterCommentResponse;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoMapperImpl implements CommentDtoMapper {
    @Override
    public RegisterCommentCommand registerRequestToCommand(String userId, RegisterCommentRequest registerCommentRequest) {
        return new RegisterCommentCommand(userId, registerCommentRequest.postingId(), registerCommentRequest.content());
    }

    @Override
    public RegisterCommentResponse dtoToRegisterResponse(RegisterCommentResponseDto registerCommentResponseDto) {
        return new RegisterCommentResponse(registerCommentResponseDto.commentId(), registerCommentResponseDto.isRegisterSuccess());
    }
}
