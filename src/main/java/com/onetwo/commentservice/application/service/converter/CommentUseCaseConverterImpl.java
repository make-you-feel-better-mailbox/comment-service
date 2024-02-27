package com.onetwo.commentservice.application.service.converter;

import com.onetwo.commentservice.application.port.in.response.*;
import com.onetwo.commentservice.domain.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentUseCaseConverterImpl implements CommentUseCaseConverter {
    @Override
    public RegisterCommentResponseDto commentToRegisterResponseDto(Comment savedComment) {
        boolean isSuccessSave = savedComment != null && savedComment.getId() != null;

        return new RegisterCommentResponseDto(isSuccessSave ? savedComment.getId() : null, isSuccessSave);
    }

    @Override
    public DeleteCommentResponseDto commentToDeleteResponseDto(Comment comment) {
        return new DeleteCommentResponseDto(comment.isDeleted());
    }

    @Override
    public UpdateCommentResponseDto commentToUpdateResponseDto(boolean isUpdateSuccess) {
        return new UpdateCommentResponseDto(isUpdateSuccess);
    }

    @Override
    public CommentDetailResponseDto commentToDetailResponseDto(Comment comment) {
        return new CommentDetailResponseDto(
                comment.getId(),
                comment.getCategory(),
                comment.getTargetId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }

    @Override
    public FilteredCommentResponseDto commentToFilteredResponse(Comment comment) {
        return new FilteredCommentResponseDto(
                comment.getId(),
                comment.getCategory(),
                comment.getTargetId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
