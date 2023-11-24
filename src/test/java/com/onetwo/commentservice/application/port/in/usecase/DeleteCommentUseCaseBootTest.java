package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.DeleteCommentCommand;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.DeleteCommentResponseDto;
import com.onetwo.commentservice.application.port.out.RegisterCommentPort;
import com.onetwo.commentservice.application.service.service.CommentService;
import com.onetwo.commentservice.common.exceptions.BadRequestException;
import com.onetwo.commentservice.common.exceptions.NotFoundResourceException;
import com.onetwo.commentservice.domain.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DeleteCommentUseCaseBootTest {

    @Autowired
    private CommentService deleteCommentUseCase;

    @Autowired
    private RegisterCommentPort registerCommentPort;

    private final Long commentId = 1L;
    private final Long postingId = 1L;
    private final String userId = "testUserId";
    private final String content = "content";

    @Test
    @DisplayName("[통합][Use Case] Comment 삭제 - 성공 테스트")
    void deleteCommentUseCaseSuccessTest() {
        //given
        DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(commentId, userId);

        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, postingId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        registerCommentPort.registerComment(comment);

        //when
        DeleteCommentResponseDto result = deleteCommentUseCase.deleteComment(deleteCommentCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isDeleteSuccess());
    }

    @Test
    @DisplayName("[통합][Use Case] Comment 삭제 comment does not exist - 실패 테스트")
    void deleteCommentUseCaseCommentDoesNotExistFailTest() {
        //given
        DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(commentId, userId);

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> deleteCommentUseCase.deleteComment(deleteCommentCommand));
    }

    @Test
    @DisplayName("[통합][Use Case] Comment 삭제 comment already deleted - 실패 테스트")
    void deleteCommentUseCaseCommentAlreadyDeletedFailTest() {
        //given
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, postingId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        comment.deleteComment();

        Comment savedComment = registerCommentPort.registerComment(comment);

        DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(savedComment.getId(), userId);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> deleteCommentUseCase.deleteComment(deleteCommentCommand));
    }
}