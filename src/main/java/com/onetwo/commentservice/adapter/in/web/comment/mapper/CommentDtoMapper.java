package com.onetwo.commentservice.adapter.in.web.comment.mapper;

import com.onetwo.commentservice.adapter.in.web.comment.request.RegisterCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.response.DeleteCommentResponse;
import com.onetwo.commentservice.adapter.in.web.comment.response.RegisterCommentResponse;
import com.onetwo.commentservice.application.port.in.command.DeleteCommentCommand;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.DeleteCommentResponseDto;
import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;

public interface CommentDtoMapper {
    RegisterCommentCommand registerRequestToCommand(String userId, RegisterCommentRequest registerCommentRequest);

    RegisterCommentResponse dtoToRegisterResponse(RegisterCommentResponseDto registerCommentResponseDto);

    DeleteCommentCommand deleteRequestToCommand(String userId, Long commentId);

    DeleteCommentResponse dtoToDeleteResponse(DeleteCommentResponseDto deleteCommentResponseDto);
}
