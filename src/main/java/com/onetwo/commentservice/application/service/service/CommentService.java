package com.onetwo.commentservice.application.service.service;

import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;
import com.onetwo.commentservice.application.port.in.usecase.RegisterCommentUseCase;
import com.onetwo.commentservice.application.port.out.RegisterCommentPort;
import com.onetwo.commentservice.application.service.converter.CommentUseCaseConverter;
import com.onetwo.commentservice.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService implements RegisterCommentUseCase {

    private final RegisterCommentPort registerCommentPort;
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
}
