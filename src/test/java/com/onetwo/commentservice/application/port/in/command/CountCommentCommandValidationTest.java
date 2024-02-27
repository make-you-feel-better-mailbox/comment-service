package com.onetwo.commentservice.application.port.in.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class CountCommentCommandValidationTest {

    private final int category = 1;
    private final long targetId = 11;

    @Test
    @DisplayName("[단위][Command Validation] Count Comment Command Validation test - 성공 테스트")
    void countCommentCommandValidationTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new CountCommentCommand(category, targetId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Count Comment Command category Validation fail test - 실패 테스트")
    void countCommentCommandCategoryValidationFailTest(Integer testCategory) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new CountCommentCommand(testCategory, targetId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Count Comment Command target Id Validation fail test - 실패 테스트")
    void countCommentCommandTargetIdValidationFailTest(Long testTargetId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new CountCommentCommand(category, testTargetId));
    }
}