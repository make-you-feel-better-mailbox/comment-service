
package com.onetwo.commentservice.adapter.out.persistence.repository.comment;

import com.onetwo.commentservice.adapter.out.persistence.entity.CommentEntity;
import com.onetwo.commentservice.application.port.in.command.CommentFilterCommand;

import java.util.List;

public interface QCommentRepository {
    List<CommentEntity> sliceByCommand(CommentFilterCommand commentFilterCommand);
}
