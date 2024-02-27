package com.onetwo.commentservice.application.service.adapter;

import com.onetwo.commentservice.adapter.out.persistence.entity.CommentEntity;
import com.onetwo.commentservice.adapter.out.persistence.repository.comment.CommentRepository;
import com.onetwo.commentservice.application.port.in.command.CommentFilterCommand;
import com.onetwo.commentservice.application.port.out.ReadCommentPort;
import com.onetwo.commentservice.application.port.out.RegisterCommentPort;
import com.onetwo.commentservice.application.port.out.UpdateCommentPort;
import com.onetwo.commentservice.common.GlobalStatus;
import com.onetwo.commentservice.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements RegisterCommentPort, ReadCommentPort, UpdateCommentPort {

    private final CommentRepository commentRepository;

    @Override
    public Comment registerComment(Comment comment) {
        CommentEntity commentEntity = CommentEntity.domainToEntity(comment);

        CommentEntity savedCommentEntity = commentRepository.save(commentEntity);

        return Comment.entityToDomain(savedCommentEntity);
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(commentId);

        if (optionalCommentEntity.isPresent()) {
            Comment comment = Comment.entityToDomain(optionalCommentEntity.get());

            return Optional.of(comment);
        }

        return Optional.empty();
    }

    @Override
    public List<Comment> filterComment(CommentFilterCommand commentFilterCommand) {
        List<CommentEntity> commentEntityList = commentRepository.sliceByCommand(commentFilterCommand);

        return commentEntityList.stream().map(Comment::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public int countCommentByCategoryAndTargetId(Integer category, Long targetId) {
        Integer countComment = commentRepository.countByCategoryAndTargetIdAndState(category, targetId, GlobalStatus.PERSISTENCE_NOT_DELETED);

        return countComment == null ? 0 : countComment;
    }

    @Override
    public void updateComment(Comment comment) {
        CommentEntity commentEntity = CommentEntity.domainToEntity(comment);

        commentRepository.save(commentEntity);
    }
}
