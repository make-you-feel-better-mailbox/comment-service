package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.DeleteCommentCommand;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.DeleteCommentResponseDto;
import com.onetwo.commentservice.application.port.out.ReadCommentPort;
import com.onetwo.commentservice.application.port.out.UpdateCommentPort;
import com.onetwo.commentservice.application.service.converter.CommentUseCaseConverter;
import com.onetwo.commentservice.application.service.service.CommentService;
import com.onetwo.commentservice.common.exceptions.BadRequestException;
import com.onetwo.commentservice.common.exceptions.NotFoundResourceException;
import com.onetwo.commentservice.domain.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DeleteCommentUseCaseTest {

    @InjectMocks
    private CommentService deleteCommentUseCase;

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
    @DisplayName("[단위][Use Case] Comment 삭제 - 성공 테스트")
    void deleteCommentUseCaseSuccessTest() {
        //given
        DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(commentId, userId);
        DeleteCommentResponseDto deleteCommentResponseDto = new DeleteCommentResponseDto(true);

        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, postingId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        given(readCommentPort.findById(anyLong())).willReturn(Optional.of(comment));
        given(commentUseCaseConverter.commentToDeleteResponseDto(any(Comment.class))).willReturn(deleteCommentResponseDto);
        //when
        DeleteCommentResponseDto result = deleteCommentUseCase.deleteComment(deleteCommentCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isDeleteSuccess());
    }

    @Test
    @DisplayName("[단위][Use Case] Comment 삭제 comment does not exist - 실패 테스트")
    void deleteCommentUseCaseCommentDoesNotExistFailTest() {
        //given
        DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(commentId, userId);

        given(readCommentPort.findById(anyLong())).willReturn(Optional.empty());

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> deleteCommentUseCase.deleteComment(deleteCommentCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] Comment 삭제 comment already deleted - 실패 테스트")
    void deleteCommentUseCaseCommentAlreadyDeletedFailTest() {
        //given
        DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(commentId, userId);

        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, postingId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        comment.deleteComment();

        given(readCommentPort.findById(anyLong())).willReturn(Optional.of(comment));

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> deleteCommentUseCase.deleteComment(deleteCommentCommand));
    }
}