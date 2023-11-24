package com.onetwo.commentservice.application.port.out;

import com.onetwo.commentservice.domain.Comment;

public interface UpdateCommentPort {
    void updateComment(Comment comment);
}
