package com.onetwo.commentservice.application.port.out;

import com.onetwo.commentservice.domain.Comment;

import java.util.Optional;

public interface ReadCommentPort {
    Optional<Comment> findById(Long commentId);
}
