package com.onetwo.commentservice.application.service.service;

import com.onetwo.commentservice.application.port.in.command.*;
import com.onetwo.commentservice.application.port.in.response.*;
import com.onetwo.commentservice.application.port.in.usecase.DeleteCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.ReadCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.RegisterCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.UpdateCommentUseCase;
import com.onetwo.commentservice.application.port.out.ReadCommentPort;
import com.onetwo.commentservice.application.port.out.RegisterCommentPort;
import com.onetwo.commentservice.application.port.out.UpdateCommentPort;
import com.onetwo.commentservice.application.service.converter.CommentUseCaseConverter;
import com.onetwo.commentservice.domain.Comment;
import lombok.RequiredArgsConstructor;
import onetwo.mailboxcommonconfig.common.exceptions.BadRequestException;
import onetwo.mailboxcommonconfig.common.exceptions.NotFoundResourceException;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService implements RegisterCommentUseCase, DeleteCommentUseCase, UpdateCommentUseCase, ReadCommentUseCase {

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

    /**
     * Get Detail about comment use case,
     * Get detail data about comment if exist
     *
     * @param findCommentDetailCommand Request comment id
     * @return Detail data about comment
     */
    @Override
    @Transactional(readOnly = true)
    public CommentDetailResponseDto findCommentsDetail(FindCommentDetailCommand findCommentDetailCommand) {
        Comment comment = checkCommentExistAndGetComment(findCommentDetailCommand.getCommentId());

        return commentUseCaseConverter.commentToDetailResponseDto(comment);
    }

    private Comment checkCommentExistAndGetComment(Long commentId) {
        Optional<Comment> optionalComment = readCommentPort.findById(commentId);

        if (optionalComment.isEmpty()) throw new NotFoundResourceException("Comment dose not exist");

        Comment comment = optionalComment.get();

        if (comment.isDeleted()) throw new BadRequestException("Comment already deleted");

        return comment;
    }

    /**
     * Get Filtered comment use case,
     * Get Filtered slice comment data
     *
     * @param commentFilterCommand filter condition and pageable
     * @return content and slice data
     */
    @Override
    @Transactional(readOnly = true)
    public Slice<FilteredCommentResponseDto> filterComment(CommentFilterCommand commentFilterCommand) {
        List<Comment> commentList = readCommentPort.filterComment(commentFilterCommand);

        boolean hasNext = commentList.size() > commentFilterCommand.getPageable().getPageSize();

        if (hasNext) commentList.removeLast();

        List<FilteredCommentResponseDto> filteredCommentResponseDtoList = commentList.stream()
                .map(commentUseCaseConverter::commentToFilteredResponse).toList();

        return new SliceImpl<>(filteredCommentResponseDtoList, commentFilterCommand.getPageable(), hasNext);
    }

    @Override
    @Transactional(readOnly = true)
    public CountCommentResponseDto getCommentCount(CountCommentCommand countCommentCommand) {
        int countComment = readCommentPort.countCommentByCategoryAndTargetId(countCommentCommand.getCategory(), countCommentCommand.getTargetId());

        return commentUseCaseConverter.resultToCountResponseDto(countComment);
    }
}
