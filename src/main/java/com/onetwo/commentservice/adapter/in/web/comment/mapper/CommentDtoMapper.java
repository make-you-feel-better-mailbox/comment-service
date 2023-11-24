package com.onetwo.commentservice.adapter.in.web.comment.mapper;

import com.onetwo.commentservice.adapter.in.web.comment.request.RegisterCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.request.UpdateCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.response.DeleteCommentResponse;
import com.onetwo.commentservice.adapter.in.web.comment.response.RegisterCommentResponse;
import com.onetwo.commentservice.adapter.in.web.comment.response.UpdateCommentResponse;
import com.onetwo.commentservice.application.port.in.command.DeleteCommentCommand;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.command.UpdateCommentCommand;
import com.onetwo.commentservice.application.port.in.response.DeleteCommentResponseDto;
import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;
import com.onetwo.commentservice.application.port.in.response.UpdateCommentResponseDto;

public interface CommentDtoMapper {
    RegisterCommentCommand registerRequestToCommand(String userId, RegisterCommentRequest registerCommentRequest);

    RegisterCommentResponse dtoToRegisterResponse(RegisterCommentResponseDto registerCommentResponseDto);

    DeleteCommentCommand deleteRequestToCommand(Long commentId, String userId);

    DeleteCommentResponse dtoToDeleteResponse(DeleteCommentResponseDto deleteCommentResponseDto);

    UpdateCommentCommand updateRequestCommand(Long commentId, String userId, UpdateCommentRequest updateCommentRequest);

    UpdateCommentResponse dtoToUpdateResponse(UpdateCommentResponseDto updateCommentResponseDto);
}
