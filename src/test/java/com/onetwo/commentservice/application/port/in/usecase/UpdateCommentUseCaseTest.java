package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.command.UpdateCommentCommand;
import com.onetwo.commentservice.application.port.in.response.UpdateCommentResponseDto;
import com.onetwo.commentservice.application.port.out.ReadCommentPort;
import com.onetwo.commentservice.application.port.out.UpdateCommentPort;
import com.onetwo.commentservice.application.service.converter.CommentUseCaseConverter;
import com.onetwo.commentservice.application.service.service.CommentService;
import com.onetwo.commentservice.domain.Comment;
import onetwo.mailboxcommonconfig.common.exceptions.BadRequestException;
import onetwo.mailboxcommonconfig.common.exceptions.NotFoundResourceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UpdateCommentUseCaseTest {

    @InjectMocks
    private CommentService updateCommentUseCase;

    @Mock
    private ReadCommentPort readCommentPort;

    @Mock
    private UpdateCommentPort updateCommentPort;

    @Mock
    private CommentUseCaseConverter commentUseCaseConverter;

    private final Long commentId = 1L;
    private final Long postingId = 1L;
    private final String userId = "testUserId";
    private final String content = "content";

    @Test
    @DisplayName("[단위][Use Case] Comment 수정 - 성공 테스트")
    void updateCommentUseCaseSuccessTest() {
        //given
        UpdateCommentCommand UpdateCommentCommand = new UpdateCommentCommand(commentId, userId, content);
        UpdateCommentResponseDto UpdateCommentResponseDto = new UpdateCommentResponseDto(true);

        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, postingId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        given(readCommentPort.findById(anyLong())).willReturn(Optional.of(comment));
        given(commentUseCaseConverter.commentToUpdateResponseDto(anyBoolean())).willReturn(UpdateCommentResponseDto);
        //when
        UpdateCommentResponseDto result = updateCommentUseCase.updateComment(UpdateCommentCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isUpdateSuccess());
    }

    @Test
    @DisplayName("[단위][Use Case] Comment 수정 comment does not exist - 실패 테스트")
    void updateCommentUseCaseCommentDoesNotExistFailTest() {
        //given
        UpdateCommentCommand UpdateCommentCommand = new UpdateCommentCommand(commentId, userId, content);

        given(readCommentPort.findById(anyLong())).willReturn(Optional.empty());

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> updateCommentUseCase.updateComment(UpdateCommentCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] Comment 수정 comment already Updated - 실패 테스트")
    void updateCommentUseCaseCommentAlreadyUpdatedFailTest() {
        //given
        UpdateCommentCommand UpdateCommentCommand = new UpdateCommentCommand(commentId, userId, content);

        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, postingId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        comment.deleteComment();

        given(readCommentPort.findById(anyLong())).willReturn(Optional.of(comment));

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateCommentUseCase.updateComment(UpdateCommentCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] Comment 수정 user id does not match - 실패 테스트")
    void updateCommentUseCaseUserIdDoesNotMatchFailTest() {
        //given
        String wrongUserId = "wrongUserId";

        UpdateCommentCommand UpdateCommentCommand = new UpdateCommentCommand(commentId, wrongUserId, content);

        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, postingId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        given(readCommentPort.findById(anyLong())).willReturn(Optional.of(comment));

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> updateCommentUseCase.updateComment(UpdateCommentCommand));
    }
}