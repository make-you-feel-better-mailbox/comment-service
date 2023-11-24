package com.onetwo.commentservice.adapter.out.persistence.repository.comment;

import com.onetwo.commentservice.adapter.out.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
