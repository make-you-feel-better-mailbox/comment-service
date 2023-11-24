package com.onetwo.commentservice.application.port.in.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

class UpdateCommentCommandValidationTest {

    private final Long commentId = 1L;
    private final String userId = "testUserId";
    private final String content = "content";

    @Test
    @DisplayName("[단위][Command Validation] Update Comment Command Validation test - 성공 테스트")
    void updateCommentCommandValidationTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new UpdateCommentCommand(commentId, userId, content));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Update Comment Command user Id Validation fail test - 실패 테스트")
    void updateCommentCommandUserIdValidationFailTest(String testUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new UpdateCommentCommand(commentId, testUserId, content));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Update Comment Command comment Id Validation fail test - 실패 테스트")
    void updateCommentCommandCommentIdValidationFailTest(Long testCommentId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new UpdateCommentCommand(testCommentId, userId, content));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Update Comment Command content Validation fail test - 실패 테스트")
    void updateCommentCommandContentValidationFailTest(String testContent) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new UpdateCommentCommand(commentId, userId, testContent));
    }
}