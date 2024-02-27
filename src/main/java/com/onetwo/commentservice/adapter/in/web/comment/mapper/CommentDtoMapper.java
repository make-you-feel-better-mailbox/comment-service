package com.onetwo.commentservice.adapter.in.web.comment.mapper;

import com.onetwo.commentservice.adapter.in.web.comment.request.RegisterCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.request.UpdateCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.response.*;
import com.onetwo.commentservice.application.port.in.command.*;
import com.onetwo.commentservice.application.port.in.response.*;

public interface CommentDtoMapper {
    RegisterCommentCommand registerRequestToCommand(String userId, RegisterCommentRequest registerCommentRequest);

    RegisterCommentResponse dtoToRegisterResponse(RegisterCommentResponseDto registerCommentResponseDto);

    DeleteCommentCommand deleteRequestToCommand(Long commentId, String userId);

    DeleteCommentResponse dtoToDeleteResponse(DeleteCommentResponseDto deleteCommentResponseDto);

    UpdateCommentCommand updateRequestCommand(Long commentId, String userId, UpdateCommentRequest updateCommentRequest);

    UpdateCommentResponse dtoToUpdateResponse(UpdateCommentResponseDto updateCommentResponseDto);

    FindCommentDetailCommand findRequestToCommand(Long commentId);

    CommentDetailResponse dtoToDetailResponse(CommentDetailResponseDto commentDetailResponseDto);

    CountCommentCommand countRequestToCommand(Integer category, Long targetId);

    CommentCountResponse dtoToCountResponse(CountCommentResponseDto countCommentResponseDto);
}
