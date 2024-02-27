package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RegisterCommentUseCaseBootTest {

    @Autowired
    private RegisterCommentUseCase registerCommentUseCase;

    private final Integer category = 1;
    private final Long targetId = 1L;
    private final String userId = "testUserId";
    private final String content = "content";

    @Test
    @DisplayName("[통합][Use Case] Comment 등록 - 성공 테스트")
    void registerCommentUseCaseSuccessTest() {
        //given
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content);

        //when
        RegisterCommentResponseDto result = registerCommentUseCase.registerComment(registerCommentCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isRegisterSuccess());
        Assertions.assertNotNull(result.commentId());
    }
}