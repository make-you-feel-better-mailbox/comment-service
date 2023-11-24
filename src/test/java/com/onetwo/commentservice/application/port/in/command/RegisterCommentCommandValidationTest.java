package com.onetwo.commentservice.application.port.in.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

class RegisterCommentCommandValidationTest {

    private final Long postingId = 1L;
    private final String userId = "testUserId";
    private final String content = "content";

    @Test
    @DisplayName("[단위][Command Validation] Register Comment Command Validation test - 성공 테스트")
    void registerCommentCommandValidationTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new RegisterCommentCommand(userId, postingId, content));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register Comment Command user Id Validation fail test - 실패 테스트")
    void registerCommentCommandUserIdValidationFailTest(String testUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterCommentCommand(testUserId, postingId, content));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Register Comment Command posting Id Validation fail test - 실패 테스트")
    void registerCommentCommandPostingIdValidationFailTest(Long testPostingId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterCommentCommand(userId, testPostingId, content));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register Comment Command content Validation fail test - 실패 테스트")
    void registerCommentCommandContentValidationFailTest(String testContent) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterCommentCommand(userId, postingId, testContent));
    }
}