package com.onetwo.commentservice.application.port.out;

import com.onetwo.commentservice.application.port.in.command.CommentFilterCommand;
import com.onetwo.commentservice.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface ReadCommentPort {
    Optional<Comment> findById(Long commentId);

    List<Comment> filterComment(CommentFilterCommand commentFilterCommand);

    int countCommentByCategoryAndTargetId(Integer category, Long targetId);
}
