package com.onetwo.commentservice.domain;

import com.onetwo.commentservice.adapter.out.persistence.entity.CommentEntity;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends BaseDomain {

    private Long id;
    private Long postingId;
    private String userId;
    private String content;
    private Boolean state;

    public static Comment createNewCommentByCommand(RegisterCommentCommand registerCommentCommand) {
        Comment comment = new Comment(
                null,
                registerCommentCommand.getPostingId(),
                registerCommentCommand.getUserId(),
                registerCommentCommand.getContent(),
                false);

        comment.setDefaultState();

        return comment;
    }

    public static Comment entityToDomain(CommentEntity commentEntity) {
        Comment comment = new Comment(
                commentEntity.getId(),
                commentEntity.getPostingId(),
                commentEntity.getUserId(),
                commentEntity.getContent(),
                commentEntity.getState()
        );

        comment.setMetaDataByEntity(commentEntity);
        return comment;
    }

    private void setDefaultState() {
        setCreatedAt(Instant.now());
        setCreateUser(this.userId);
    }

    public boolean isDeleted() {
        return this.state;
    }

    public boolean isSameUserId(String userId) {
        return this.userId.equals(userId);
    }

    public void deleteComment() {
        this.state = true;
        setUpdatedAt(Instant.now());
        setUpdateUser(this.userId);
    }
}
