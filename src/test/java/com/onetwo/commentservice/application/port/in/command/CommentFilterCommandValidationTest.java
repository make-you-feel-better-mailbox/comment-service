package com.onetwo.commentservice.application.port.in.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;

class CommentFilterCommandValidationTest {

    private final long postingId = 1L;
    private final String content = "content";
    private final String userId = "testUserId";
    private final Instant filterStartDate = Instant.parse("2000-01-01T00:00:00Z");
    private final Instant filterEndDate = Instant.parse("4000-01-01T00:00:00Z");
    private final PageRequest pageRequest = PageRequest.of(0, 20);

    @Test
    @DisplayName("[단위][Command Validation] Comment Filter Command Validation test - 성공 테스트")
    void commentFilterCommandValidationSuccessTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new CommentFilterCommand(postingId, userId, content, filterStartDate, filterEndDate, pageRequest));
    }

    @Test
    @DisplayName("[단위][Command Validation] Comment Filter Command Validation null test - 성공 테스트")
    void commentFilterCommandValidationNullSuccessTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new CommentFilterCommand(postingId, null, null, null, null, pageRequest));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Comment Filter Command posting id Validation fail test - 실패 테스트")
    void commentFilterCommandUserIdValidationFailTest(Long testPostingId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new CommentFilterCommand(testPostingId, userId, content, filterStartDate, filterEndDate, pageRequest));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Comment Filter Command page request Validation fail test - 실패 테스트")
    void commentFilterCommandPageRequestValidationFailTest(PageRequest testPageRequest) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new CommentFilterCommand(postingId, userId, content, filterStartDate, filterEndDate, testPageRequest));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Comment Filter Command Validation user id - 성공 테스트")
    void commentFilterCommandValidationUserIdSuccessTest(String testUserId) {
        //given when then
        Assertions.assertDoesNotThrow(() -> new CommentFilterCommand(postingId, testUserId, content, filterStartDate, filterEndDate, pageRequest));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Comment Filter Command Validation content - 성공 테스트")
    void commentFilterCommandValidationContentSuccessTest(String testContent) {
        //given when then
        Assertions.assertDoesNotThrow(() -> new CommentFilterCommand(postingId, userId, testContent, filterStartDate, filterEndDate, pageRequest));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Comment Filter Command Validation start date - 성공 테스트")
    void commentFilterCommandValidationStartDateSuccessTest(Instant testStartDate) {
        //given when then
        Assertions.assertDoesNotThrow(() -> new CommentFilterCommand(postingId, userId, content, testStartDate, filterEndDate, pageRequest));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Comment Filter Command Validation end date - 성공 테스트")
    void commentFilterCommandValidationEndDateSuccessTest(Instant testEndDate) {
        //given when then
        Assertions.assertDoesNotThrow(() -> new CommentFilterCommand(postingId, userId, content, filterStartDate, testEndDate, pageRequest));
    }
}