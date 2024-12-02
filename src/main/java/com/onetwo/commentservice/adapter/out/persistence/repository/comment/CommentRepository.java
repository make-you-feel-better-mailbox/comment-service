package com.onetwo.commentservice.adapter.out.persistence.repository.comment;

import com.onetwo.commentservice.adapter.out.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>, QCommentRepository {
    Integer countByCategoryAndTargetIdAndState(Integer category, Long targetId, boolean persistenceNotDeleted);
}
