package com.onetwo.commentservice.application.port.in.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

class DeleteCommentCommandValidationTest {

    private final Long commentId = 1L;
    private final String userId = "testUserId";

    @Test
    @DisplayName("[단위][Command Validation] Delete Comment Command Validation test - 성공 테스트")
    void deleteCommentCommandValidationTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new DeleteCommentCommand(commentId, userId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Delete Comment Command user Id Validation fail test - 실패 테스트")
    void deleteCommentCommandUserIdValidationFailTest(String testUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new DeleteCommentCommand(commentId, testUserId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Delete Comment Command comment Id Validation fail test - 실패 테스트")
    void deleteCommentCommandPostingIdValidationFailTest(Long testCommentId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new DeleteCommentCommand(testCommentId, userId));
    }
}