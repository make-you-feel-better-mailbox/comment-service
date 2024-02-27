package com.onetwo.commentservice.adapter.out.persistence.entity;

import com.onetwo.commentservice.adapter.out.persistence.repository.converter.BooleanNumberConverter;
import com.onetwo.commentservice.domain.Comment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 1: posing 2:comment */
    @Column(nullable = false)
    private Integer category;

    @Column(nullable = false)
    private Long targetId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, length = 1)
    @Convert(converter = BooleanNumberConverter.class)
    private Boolean state;

    private CommentEntity(Long id, Integer category, Long targetId, String userId, String content, Boolean state) {
        this.id = id;
        this.category = category;
        this.targetId = targetId;
        this.userId = userId;
        this.content = content;
        this.state = state;
    }

    public static CommentEntity domainToEntity(Comment comment) {
        CommentEntity commentEntity = new CommentEntity(
                comment.getId(),
                comment.getCategory(),
                comment.getTargetId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getState()
        );

        commentEntity.setMetaDataByDomain(comment);
        return commentEntity;
    }
}
