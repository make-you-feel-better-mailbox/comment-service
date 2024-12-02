package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.command.UpdateCommentCommand;
import com.onetwo.commentservice.application.port.in.response.UpdateCommentResponseDto;
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
class UpdateCommentUseCaseBootTest {

    @Autowired
    private UpdateCommentUseCase updateCommentUseCase;

    @Autowired
    private RegisterCommentPort registerCommentPort;

    private final Long commentId = 0L;
    private final Integer category = 1;
    private final Long targetId = 1L;
    private final String userId = "testUserId";
    private final String content = "content";
    private final String updateContent = "updateContent";

    @Test
    @DisplayName("[통합][Use Case] Comment 수정 - 성공 테스트")
    void updateCommentUseCaseSuccessTest() {
        //given
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        Comment savedComment = registerCommentPort.registerComment(comment);

        UpdateCommentCommand UpdateCommentCommand = new UpdateCommentCommand(savedComment.getId(), userId, updateContent);

        //when
        UpdateCommentResponseDto result = updateCommentUseCase.updateComment(UpdateCommentCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isUpdateSuccess());
    }

    @Test
    @DisplayName("[통합][Use Case] Comment 수정 comment does not exist - 실패 테스트")
    void updateCommentUseCaseCommentDoesNotExistFailTest() {
        //given
        UpdateCommentCommand UpdateCommentCommand = new UpdateCommentCommand(commentId, userId, updateContent);

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> updateCommentUseCase.updateComment(UpdateCommentCommand));
    }

    @Test
    @DisplayName("[통합][Use Case] Comment 수정 comment already Updated - 실패 테스트")
    void updateCommentUseCaseCommentAlreadyUpdatedFailTest() {
        //given
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        comment.deleteComment();

        Comment savedComment = registerCommentPort.registerComment(comment);

        UpdateCommentCommand UpdateCommentCommand = new UpdateCommentCommand(savedComment.getId(), userId, updateContent);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateCommentUseCase.updateComment(UpdateCommentCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] Comment 수정 user id does not match - 실패 테스트")
    void updateCommentUseCaseUserIdDoesNotMatchFailTest() {
        //given
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        Comment savedComment = registerCommentPort.registerComment(comment);

        String wrongUserId = "wrongUserId";

        UpdateCommentCommand UpdateCommentCommand = new UpdateCommentCommand(savedComment.getId(), wrongUserId, updateContent);

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateCommentUseCase.updateComment(UpdateCommentCommand));
    }
}