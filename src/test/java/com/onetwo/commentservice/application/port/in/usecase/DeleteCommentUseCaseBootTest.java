package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.DeleteCommentCommand;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.DeleteCommentResponseDto;
import com.onetwo.commentservice.application.port.out.RegisterCommentPort;
import com.onetwo.commentservice.domain.Comment;
import onetwo.mailboxcommonconfig.common.exceptions.BadRequestException;
import onetwo.mailboxcommonconfig.common.exceptions.NotFoundResourceException;
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
    private DeleteCommentUseCase deleteCommentUseCase;

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
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, postingId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        Comment savedComment = registerCommentPort.registerComment(comment);

        DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(savedComment.getId(), userId);

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

    @Test
    @DisplayName("[단위][Use Case] Comment 삭제 user id does not match - 실패 테스트")
    void deleteCommentUseCaseUserIdDoesNotMatchFailTest() {
        //given
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, postingId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        Comment savedComment = registerCommentPort.registerComment(comment);

        String wrongUserId = "wrongUserId";

        DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(savedComment.getId(), wrongUserId);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> deleteCommentUseCase.deleteComment(deleteCommentCommand));
    }
}