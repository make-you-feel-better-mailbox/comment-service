package com.onetwo.commentservice.application.service.service;

import com.onetwo.commentservice.application.port.in.command.DeleteCommentCommand;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.command.UpdateCommentCommand;
import com.onetwo.commentservice.application.port.in.response.DeleteCommentResponseDto;
import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;
import com.onetwo.commentservice.application.port.in.response.UpdateCommentResponseDto;
import com.onetwo.commentservice.application.port.in.usecase.DeleteCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.RegisterCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.UpdateCommentUseCase;
import com.onetwo.commentservice.application.port.out.ReadCommentPort;
import com.onetwo.commentservice.application.port.out.RegisterCommentPort;
import com.onetwo.commentservice.application.port.out.UpdateCommentPort;
import com.onetwo.commentservice.application.service.converter.CommentUseCaseConverter;
import com.onetwo.commentservice.common.exceptions.BadRequestException;
import com.onetwo.commentservice.common.exceptions.NotFoundResourceException;
import com.onetwo.commentservice.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService implements RegisterCommentUseCase, DeleteCommentUseCase, UpdateCommentUseCase {

    private final RegisterCommentPort registerCommentPort;
    private final ReadCommentPort readCommentPort;
    private final UpdateCommentPort updateCommentPort;
    private final CommentUseCaseConverter commentUseCaseConverter;

    /**
     * Register comment use case,
     * register comment data to persistence
     *
     * @param registerCommentCommand data about register comment with user id and posting id
     * @return Boolean about register success
     */
    @Override
    @Transactional
    public RegisterCommentResponseDto registerComment(RegisterCommentCommand registerCommentCommand) {
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        Comment savedComment = registerCommentPort.registerComment(comment);

        return commentUseCaseConverter.commentToRegisterResponseDto(savedComment);
    }

    /**
     * Delete comment use case,
     * delete comment data to persistence
     *
     * @param deleteCommentCommand request delete comment id and request user id
     * @return Boolean about delete comment success
     */
    @Override
    @Transactional
    public DeleteCommentResponseDto deleteComment(DeleteCommentCommand deleteCommentCommand) {
        Comment comment = checkCommentExistAndGetComment(deleteCommentCommand.getCommentId());

        if (!comment.isSameUserId(deleteCommentCommand.getUserId()))
            throw new BadRequestException("Register does not match with request user");

        comment.deleteComment();

        updateCommentPort.updateComment(comment);

        return commentUseCaseConverter.commentToDeleteResponseDto(comment);
    }

    /**
     * Update comment use case,
     * update comment data on persistence
     *
     * @param updateCommentCommand request update comment id and request user id and update data
     * @return Boolean about update comment success
     */
    @Override
    @Transactional
    public UpdateCommentResponseDto updateComment(UpdateCommentCommand updateCommentCommand) {
        Comment comment = checkCommentExistAndGetComment(updateCommentCommand.getCommentId());

        if (!comment.isSameUserId(updateCommentCommand.getUserId()))
            throw new BadRequestException("Register does not match with request user");

        comment.updateComment(updateCommentCommand);

        updateCommentPort.updateComment(comment);

        return commentUseCaseConverter.commentToUpdateResponseDto(true);
    }

    private Comment checkCommentExistAndGetComment(Long commentId) {
        Optional<Comment> optionalComment = readCommentPort.findById(commentId);

        if (optionalComment.isEmpty()) throw new NotFoundResourceException("Comment dose not exist");

        Comment comment = optionalComment.get();

        if (comment.isDeleted()) throw new BadRequestException("Comment already deleted");

        return comment;
    }
}
