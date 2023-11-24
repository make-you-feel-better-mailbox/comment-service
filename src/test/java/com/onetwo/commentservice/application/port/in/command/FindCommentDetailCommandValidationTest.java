package com.onetwo.commentservice.application.port.in.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class FindCommentDetailCommandValidationTest {

    private final Long commentId = 1L;

    @Test
    @DisplayName("[단위][Command Validation] Find Comment Detail Command Validation test - 성공 테스트")
    void findCommentDetailCommandValidationTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new FindCommentDetailCommand(commentId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Find Comment Detail Command comment Id Validation fail test - 실패 테스트")
    void findCommentDetailCommandPostingIdValidationFailTest(Long testCommentId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new FindCommentDetailCommand(testCommentId));
    }
}