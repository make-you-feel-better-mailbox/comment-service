package com.onetwo.commentservice.adapter.in.web.comment.mapper;

import com.onetwo.commentservice.adapter.in.web.comment.request.RegisterCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.request.UpdateCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.response.*;
import com.onetwo.commentservice.application.port.in.command.*;
import com.onetwo.commentservice.application.port.in.response.*;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoMapperImpl implements CommentDtoMapper {
    @Override
    public RegisterCommentCommand registerRequestToCommand(String userId, RegisterCommentRequest registerCommentRequest) {
        return new RegisterCommentCommand(userId, registerCommentRequest.category(), registerCommentRequest.targetId(), registerCommentRequest.content());
    }

    @Override
    public RegisterCommentResponse dtoToRegisterResponse(RegisterCommentResponseDto registerCommentResponseDto) {
        return new RegisterCommentResponse(registerCommentResponseDto.commentId(), registerCommentResponseDto.isRegisterSuccess());
    }

    @Override
    public DeleteCommentCommand deleteRequestToCommand(Long commentId, String userId) {
        return new DeleteCommentCommand(commentId, userId);
    }

    @Override
    public DeleteCommentResponse dtoToDeleteResponse(DeleteCommentResponseDto deleteCommentResponseDto) {
        return new DeleteCommentResponse(deleteCommentResponseDto.isDeleteSuccess());
    }

    @Override
    public UpdateCommentCommand updateRequestCommand(Long commentId, String userId, UpdateCommentRequest updateCommentRequest) {
        return new UpdateCommentCommand(commentId, userId, updateCommentRequest.content());
    }

    @Override
    public UpdateCommentResponse dtoToUpdateResponse(UpdateCommentResponseDto updateCommentResponseDto) {
        return new UpdateCommentResponse(updateCommentResponseDto.isUpdateSuccess());
    }

    @Override
    public FindCommentDetailCommand findRequestToCommand(Long commentId) {
        return new FindCommentDetailCommand(commentId);
    }

    @Override
    public CommentDetailResponse dtoToDetailResponse(CommentDetailResponseDto commentDetailResponseDto) {
        return new CommentDetailResponse(
                commentDetailResponseDto.commentId(),
                commentDetailResponseDto.category(),
                commentDetailResponseDto.targetId(),
                commentDetailResponseDto.userId(),
                commentDetailResponseDto.content(),
                commentDetailResponseDto.createdDate()
        );
    }

    @Override
    public CountCommentCommand countRequestToCommand(Integer category, Long targetId) {
        return new CountCommentCommand(category, targetId);
    }

    @Override
    public CommentCountResponse dtoToCountResponse(CountCommentResponseDto countCommentResponseDto) {
        return new CommentCountResponse(countCommentResponseDto.commentCount());
    }
}
