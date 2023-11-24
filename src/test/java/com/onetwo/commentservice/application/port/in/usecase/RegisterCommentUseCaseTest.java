package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;
import com.onetwo.commentservice.application.port.out.RegisterCommentPort;
import com.onetwo.commentservice.application.service.converter.CommentUseCaseConverter;
import com.onetwo.commentservice.application.service.service.CommentService;
import com.onetwo.commentservice.domain.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RegisterCommentUseCaseTest {

    @InjectMocks
    private CommentService registerCommentUseCase;

    @Mock
    private RegisterCommentPort registerCommentPort;

    @Mock
    private CommentUseCaseConverter commentUseCaseConverter;

    private final Long commentId = 1L;
    private final Long postingId = 1L;
    private final String userId = "testUserId";
    private final String content = "content";

    @Test
    @DisplayName("[단위][Use Case] Comment 등록 - 성공 테스트")
    void registerCommentUseCaseSuccessTest() {
        //given
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, postingId, content);

        RegisterCommentResponseDto registerCommentResponseDto = new RegisterCommentResponseDto(commentId, true);

        Comment savedComment = Comment.createNewCommentByCommand(registerCommentCommand);

        given(registerCommentPort.registerComment(any(Comment.class))).willReturn(savedComment);
        given(commentUseCaseConverter.commentToRegisterResponseDto(any(Comment.class))).willReturn(registerCommentResponseDto);
        //when
        RegisterCommentResponseDto result = registerCommentUseCase.registerComment(registerCommentCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isRegisterSuccess());
        Assertions.assertNotNull(result.commentId());
    }
}