package com.onetwo.commentservice.application.service.adapter;

import com.onetwo.commentservice.adapter.out.persistence.entity.CommentEntity;
import com.onetwo.commentservice.adapter.out.persistence.repository.comment.CommentRepository;
import com.onetwo.commentservice.application.port.out.RegisterCommentPort;
import com.onetwo.commentservice.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements RegisterCommentPort {

    private final CommentRepository commentRepository;

    @Override
    public Comment registerComment(Comment comment) {
        CommentEntity commentEntity = CommentEntity.domainToEntity(comment);

        CommentEntity savedCommentEntity = commentRepository.save(commentEntity);

        return Comment.entityToDomain(savedCommentEntity);
    }
}
