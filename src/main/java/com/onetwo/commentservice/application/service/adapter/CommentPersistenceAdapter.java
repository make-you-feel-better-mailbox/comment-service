package com.onetwo.commentservice.application.service.adapter;

import com.onetwo.commentservice.adapter.out.persistence.entity.CommentEntity;
import com.onetwo.commentservice.adapter.out.persistence.repository.comment.CommentRepository;
import com.onetwo.commentservice.application.port.out.ReadCommentPort;
import com.onetwo.commentservice.application.port.out.RegisterCommentPort;
import com.onetwo.commentservice.application.port.out.UpdateCommentPort;
import com.onetwo.commentservice.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    public void updateComment(Comment comment) {
        CommentEntity commentEntity = CommentEntity.domainToEntity(comment);

        commentRepository.save(commentEntity);
    }
}
