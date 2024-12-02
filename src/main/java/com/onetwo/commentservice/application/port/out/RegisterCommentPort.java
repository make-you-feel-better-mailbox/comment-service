package com.onetwo.commentservice.application.port.out;

import com.onetwo.commentservice.domain.Comment;

public interface RegisterCommentPort {
    Comment registerComment(Comment comment);
}
